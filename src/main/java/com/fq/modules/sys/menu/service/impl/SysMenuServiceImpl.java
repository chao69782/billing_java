package com.fq.modules.sys.menu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.api.api.ApiError;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.menu.dto.QuerySysMenuDTO;
import com.fq.modules.sys.menu.dto.SysMenuDTO;
import com.fq.modules.sys.menu.entity.SysMenu;
import com.fq.modules.sys.menu.enums.MenuState;
import com.fq.modules.sys.menu.mapper.SysMenuMapper;
import com.fq.modules.sys.menu.service.SysMenuService;
import com.fq.modules.sys.menu.vo.MenuRouteVO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.enums.RoleType;
import com.fq.modules.sys.role.service.SysRoleMenuService;
import com.fq.modules.sys.role.service.SysUserRoleService;
import com.fq.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 超chao
 * @Description 系统菜单业务实现类
 * @Date 2024/11/21/周四 10:04
 * @Version 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper baseMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public IPage<SysMenuDTO> paging(QuerySysMenuDTO reqDTO) {
        return baseMapper.paging(reqDTO.toPage(), reqDTO);

    }

    @Override
    public List<MenuRouteVO> listRoutes(Long userId) {
        // 全部菜单列表
        LinkedHashMap<Long,SysMenu> map = this.allMap();
        SysRole sysRole = sysUserRoleService.userRole(userId);
        if (!sysRole.getId().equals(RoleType.ROLE_SUPER_ADMIN.getType())){
            // 构建路由菜单
            List<Long> list = baseMapper.findUserMenu(userId);

            // 补全上级
            List<Long> ids = new ArrayList<>();
            for(Long id: list){
                this.addParent(map.get(id), map, ids);
            }
            // 移除为未授权菜单
            List<Long> keys = new ArrayList<>();
            BeanMapper.copy(map.keySet(), keys);

            for(Long key: keys){
                if(!ids.contains(key)){
                    map.remove(key);
                }
            }
        }

        List<SysMenu> routes = new ArrayList<>(map.values());

        return this.buildRoutes(routes);
    }

    @Override
    public List<MenuRouteVO> listTree() {
        // 列出用户菜单
        List<SysMenu> list = baseMapper.findAllMenu();
        return this.buildTree(list);
    }

    @Override
    public void sort(Long id, String sort) {
        SysMenu depart = this.getById(id);
        SysMenu exchange = null;

        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        // 同级排序
        wrapper.lambda().eq(SysMenu::getParentId, depart.getParentId());
        wrapper.last("LIMIT 1");
        switch (sort){
            // 下降
            case "desc":
                // 同级排序
                wrapper.lambda()
                        .gt(SysMenu::getSort, depart.getSort())
                        .orderByAsc(SysMenu::getSort);
                exchange = this.getOne(wrapper, false);
                break;
            case "asc":
                // 同级排序
                wrapper.lambda()
                        .lt(SysMenu::getSort, depart.getSort())
                        .orderByDesc(SysMenu::getSort);
                exchange = this.getOne(wrapper, false);

                break;
            default:
                throw new ServiceException(ApiError.ERROR_10010007);

        }

        if (exchange != null) {
            SysMenu a = new SysMenu();
            a.setId(id);
            a.setSort(exchange.getSort());
            SysMenu b = new SysMenu();
            b.setId(exchange.getId());
            b.setSort(depart.getSort());
            this.updateById(a);
            this.updateById(b);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuDTO reqDTO) {
        if (reqDTO.getId() == null) {
            QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id").eq("meta_title", reqDTO.getMetaTitle());
            if (this.count(queryWrapper) > 0) {
                throw new ServiceException(ApiError.ERROR_10060001);
            }
            this.fillSort(reqDTO);
        } else {
            reqDTO.setSort(null);
        }

        SysMenu entity = new SysMenu();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);

        // Spring Security 模式下，清除缓存的操作由 Redis 过期时间自动处理
        // 不再需要手动清除 Shiro 缓存
    }

    @Override
    public void delete(List<Long> ids) {
        //查询条件
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysMenu::getParentId, ids);
        long count = this.count(wrapper);
        if(count > 0){
            throw new ServiceException(ApiError.ERROR_10050001);
        }

        this.removeByIds(ids);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeState(BaseChangeStateDTO<Long> reqDTO) {
        // Spring Security 模式下，清除缓存的操作由 Redis 过期时间自动处理
        // 不再需要手动清除 Shiro 缓存

        UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
        wrapper.set("state",reqDTO.getState()).in("id",reqDTO.getId());
        if (reqDTO.getState().equals(MenuState.DISABLE.getValue())){
            //删除角色菜单权限
            sysRoleMenuService.deleteByMenuId(reqDTO.getId());

            this.update(wrapper);
            return;
        }
        this.update(wrapper);

    }

    /**
     * 全部菜单的Map
     * @return
     */
    public LinkedHashMap<Long,SysMenu> allMap(){
        List<SysMenu> menus = baseMapper.findAllMenu();
        LinkedHashMap<Long,SysMenu> map = new LinkedHashMap<>();
        for(SysMenu item: menus){
            map.put(item.getId(), item);
        }
        return map;
    }

    /**
     * 递归添加上级
     * @param menu
     * @param map
     * @param ids
     */
    private void addParent(SysMenu menu, Map<Long, SysMenu> map, List<Long> ids){

        // 添加菜单
        if(menu == null){
            return;
        }

        ids.add(menu.getId());

        // 还存在上级
        Long parentId = menu.getParentId();
        if(parentId != null
                && parentId != 0
                && !ids.contains(parentId)){
            this.addParent(map.get(parentId), map, ids);
        }
    }

    /**
     * 构建路由表
     * @param list
     * @return
     */
    private List<MenuRouteVO> buildRoutes(List<SysMenu> list) {

        // 获取Map
        Map<Long, List<MenuRouteVO>> map = this.listToMap(list);

        //注意，第0级为顶级的
        List<MenuRouteVO> topList = map.get(0L);
        if (!CollectionUtils.isEmpty(topList)) {
            for (MenuRouteVO item : topList) {
                List<MenuRouteVO> children = this.expandRoutes(map, item.getId());
                item.setChildren(children);
            }
        }

        return topList;
    }

    /**
     * 将菜单列表展开成包含父子关系的Map
     * @param list
     * @return
     */
    private Map<Long, List<MenuRouteVO>> listToMap(List<SysMenu> list){

        // 列出用户菜单
        List<MenuRouteVO> dtoList = BeanMapper.mapList(list, MenuRouteVO.class);

        //子结构的列表
        Map<Long, List<MenuRouteVO>> map = new HashMap<>();

        for (MenuRouteVO item : dtoList) {

            //如果存在
            if (map.containsKey(item.getParentId())) {
                map.get(item.getParentId()).add(item);
                continue;
            }

            //增加新的结构
            List<MenuRouteVO> a = new ArrayList<>();
            a.add(item);
            map.put(item.getParentId(), a);
        }

        return map;
    }

    /**
     * 递归构建树形结构
     * @param map 菜单映射关系
     * @param parentId 父级ID
     * @return 子菜单列表
     */
    private List<MenuRouteVO> expandRoutes(Map<Long, List<MenuRouteVO>> map, Long parentId) {
        List<MenuRouteVO> children = map.get(parentId);
        if (CollectionUtils.isEmpty(children)) {
            return new ArrayList<>();
        }

        List<MenuRouteVO> result = new ArrayList<>();
        for (MenuRouteVO child : children) {
            // 只处理有path的菜单项（路由菜单），跳过功能按钮
            if (StrUtil.isNotBlank(child.getPath())) {
                // 递归构建当前节点的子节点
                List<MenuRouteVO> subChildren = expandRoutes(map, child.getId());
                child.setChildren(subChildren);
                result.add(child);
            } else {
                // 如果当前节点没有path（功能按钮），继续检查它的子节点
                List<MenuRouteVO> subChildren = expandRoutes(map, child.getId());
                if (!CollectionUtils.isEmpty(subChildren)) {
                    // 如果子节点中有有效的路由菜单，将它们添加到结果中
                    result.addAll(subChildren);
                }
            }
        }

        return result;
    }

    /**
     * 构建菜单树
     *
     * @param list
     * @return
     */
    private List<MenuRouteVO> buildTree(List<SysMenu> list) {

        // 获取Map
        Map<Long, List<MenuRouteVO>> map = this.listToMap(list);

        //注意，第0级为顶级的
        List<MenuRouteVO> topList = map.get(0L);
        if (!CollectionUtils.isEmpty(topList)) {
            for (MenuRouteVO item : topList) {
                this.fillChildren(map, item);
            }
        }

        return topList;
    }

    /**
     * 递归去做填充数据
     *
     * @param map
     * @param item
     */
    private void fillChildren(Map<Long, List<MenuRouteVO>> map, MenuRouteVO item) {

        //设置子类
        if (map.containsKey(item.getId())) {

            List<MenuRouteVO> children = map.get(item.getId());

            if (!CollectionUtils.isEmpty(children)) {
                for (MenuRouteVO sub : children) {
                    sub.setParentMenu(item.getMetaTitle());
                    this.fillChildren(map, sub);
                }
            }
            item.setChildren(children);
        }
    }
    /**
     * 填充排序
     *
     * @param reqDTO
     */
    private void fillSort(SysMenuDTO reqDTO) {

        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();

        // 同级排序
        wrapper.lambda()
                .eq(SysMenu::getParentId, reqDTO.getParentId())
                .orderByDesc(SysMenu::getSort);
        wrapper.last("LIMIT 1");
        SysMenu depart = this.getOne(wrapper, false);

        if (depart != null) {
            reqDTO.setSort(depart.getSort() + 1);
        } else {
            reqDTO.setSort(1);
        }
    }
}
