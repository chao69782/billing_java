package com.fq.ability.mybatis;

import com.fq.utils.user.UserUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 超chao
 * @Description 时间字段自动填充
 * @Date 2024/10/30/周三 13:50
 * @Version 1.0
 */
@Intercepts(value = {@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AutoFillInterceptor implements Interceptor {

    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createTime";
    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 创建人
     * @param invocation
     * @return
     * @throws Throwable
     */
    private static final String CREATE_BY = "createBy";

    /**
     * 更新人
     * @param invocation
     * @return
     * @throws Throwable
     */
    private static final String UPDATE_BY = "updateBy";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // SQL操作命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取新增或修改的对象参数
        Object parameter = invocation.getArgs()[1];
        // 处理数据
        if (parameter != null) {

            // 新增数据
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                this.processInsert(parameter);
            }

            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                this.processUpdate(parameter);
            }
        }
        return invocation.proceed();
    }

    /**
     * 处理插入数据
     *
     * @param parameter
     * @throws IllegalAccessException
     */
    private void processInsert(Object parameter) throws IllegalAccessException {

        Field[] fields = getAllFields(parameter);

        for (Field field : fields) {
            // 创建时间
            if (Objects.equals(CREATE_TIME, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }

            // 更新时间
            if (Objects.equals(UPDATE_TIME, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }

            // 注入创建人
            if (Objects.equals(CREATE_BY, field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(parameter, UserUtils.getUserId());
                } catch (Exception e) {
                    // 未登录时，设置为 null 或默认值
                    field.set(parameter, null);
                }
            }

            // 注入更新人
            if (Objects.equals(UPDATE_BY, field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(parameter, UserUtils.getUserId());
                } catch (Exception e) {
                    // 未登录时，设置为 null 或默认值
                    field.set(parameter, null);
                }
            }
        }
    }

    /**
     * 处理更新
     *
     * @param parameter
     * @throws IllegalAccessException
     */
    private void processUpdate(Object parameter) throws IllegalAccessException {
        if (parameter instanceof Map) {
            Map<?, ?> p = (Map<?, ?>) parameter;
            if (p.containsKey("et")) {
                parameter = p.get("et");
            } else {
                parameter = p.get("param1");
            }
            if (parameter == null) {
                return;
            }
        }
        Field[] fields = getAllFields(parameter);
        for (Field field : fields) {
            // 更新时间
            if (Objects.equals(UPDATE_TIME, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }
            // 注入更新人
            if (Objects.equals(UPDATE_BY, field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(parameter, UserUtils.getUserId());
                } catch (Exception e) {
                    // 未登录时，设置为 null 或默认值
                    field.set(parameter, null);
                }
            }
        }
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
