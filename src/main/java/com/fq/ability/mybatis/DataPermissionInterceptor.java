package com.fq.ability.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.utils.user.UserUtils;
import lombok.extern.log4j.Log4j2;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.reflect.Field;
import java.sql.Connection;

/**
 * @Author 超chao
 * @Description 数据权限拦截器，使用 MyBatis-Plus 3.5.1+ 内置插件机制
 * @Date 2026/1/5 周一 10:17
 * @Version 1.0
 */
@Log4j2
public class DataPermissionInterceptor implements InnerInterceptor {

    /**
     * 本部门数据
     */
    private static final String FILTER_DEPT = "{alias}.department_id = '{departmentId}'";

    /**
     * 需要进行数据权限过滤的查询
     */
    private static final String[] FILTER_MAPPERS = new String[]{

    };

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();

        // 检查是否忽略
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
            return;
        }

        BoundSql boundSql = mpSh.boundSql();
        String sql = boundSql.getSql();

        // 只处理查询语句
        if (ms.getSqlCommandType() == SqlCommandType.SELECT) {
            // 数据权限过滤
            if (this.needFilter(ms.getId())) {
                String newSql = this.parseSql(sql);
                // 通过反射修改 SQL，这是 MyBatis-Plus 内部推荐的方式
                setSql(boundSql, newSql);
                return;
            }

        }
        log.info("++++++++++执行SQL：{}", sql);
    }

    /**
     * 通过反射修改 BoundSql 中的 SQL
     */
    private void setSql(BoundSql boundSql, String sql) {
        try {
            Field sqlField = boundSql.getClass().getDeclaredField("sql");
            sqlField.setAccessible(true);
            sqlField.set(boundSql, sql);
        } catch (Exception e) {
            log.error("修改 SQL 失败", e);
        }
    }

    /**
     * 是否需要处理数据权限
     */
    private boolean needFilter(String id) {
        for (String item : FILTER_MAPPERS) {
            if (id.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理 SQL 语句
     */
    private String parseSql(String src) {
        log.info("++++++++++原始SQL：{}", src);

        try {
            Select select = (Select) CCJSqlParserUtil.parse(src);
            PlainSelect selectBody = (PlainSelect) select.getSelectBody();
            String alias = getTableAlias(selectBody.getFromItem());
            if (alias == null) {
                log.warn("无法获取表别名，跳过数据权限过滤");
                return src;
            }

            String appendSql = this.generateAppend(alias);
            if (!StrUtil.isBlank(appendSql)) {
                if (selectBody.getWhere() == null) {
                    selectBody.setWhere(CCJSqlParserUtil.parseCondExpression(appendSql));
                } else {
                    Expression whereExpression = CCJSqlParserUtil.parseCondExpression(appendSql);
                    selectBody.setWhere(new AndExpression(selectBody.getWhere(), whereExpression));
                }
            }

            String out = select.toString();
            log.info("++++++++++过滤SQL：{}", out);
            return out;
        } catch (Exception e) {
            log.error("SQL解析失败", e);
        }

        return src;
    }

    /**
     * 安全获取表别名，处理 Table 和 SubSelect 等不同类型的 FromItem
     */
    private String getTableAlias(FromItem fromItem) {
        if (fromItem instanceof Table table) {
            if (table.getAlias() != null) {
                return table.getAlias().getName();
            }
            return table.getName();
        }

        if (fromItem.getAlias() != null) {
            return fromItem.getAlias().getName();
        }

        log.warn("FromItem 没有别名，无法应用数据权限过滤");
        return null;
    }

    /**
     * 处理数据权限作用域
     */
    private String generateAppend(String alias) {
        SysUserLoginResDTO user;
        try {
            user = UserUtils.getUser();
        } catch (Exception e) {
            // 未登录时，不进行权限过滤
            return "";
        }

        // 无需登录的内容，不做权限过滤
        if (user == null) {
            return "";
        }

        // 自己部门的数据
//        if (user.getDataScope().equals(DataScope.SELF.getScope())) {
//            log.info("++++++++++数据权限：自己部门数据");
//            return FILTER_DEPT.replace("{alias}", alias).replace("{departmentId}", user.getDepartmentId().toString());
//        }
        log.info("++++++++++数据权限：所有数据");

        return "";
    }
}