package com.fq.modules.sys.menu.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.modules.sys.menu.dto.QuerySysMenuDTO;
import com.fq.modules.sys.menu.dto.SysMenuDTO;
import com.fq.modules.sys.menu.entity.SysMenu;
import com.fq.modules.sys.menu.vo.MenuRouteVO;

import java.util.List;


/**
 * @Author 超chao
 * @Description 系统菜单业务类
 * @Date 2024/11/21/周四 10:04
 * @Version 1.0
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 分页查询数据
     * @param reqDTO 查询参数
     * @return 菜单分页列表
     */
    IPage<SysMenuDTO> paging(QuerySysMenuDTO reqDTO);

    /**
     * 列出全部菜单
     * @param userId 用户id
     * @return 用户菜单列表
     */
    List<MenuRouteVO> listRoutes(Long userId);

    /**
     * 列出全部菜单
     * @return 菜单列表
     */
    List<MenuRouteVO> listTree();

    /**
     * 排序
     * @param id 菜单ID
     * @param sort 排序
     */
    void sort(Long id, String sort);

    /**
     * 保存菜单
     * @param reqDTO 菜单信息
     */
    void save(SysMenuDTO reqDTO);

    /**
     * 删除菜单
     * @param ids 菜单ID集合
     */
    void delete(List<Long> ids);

    /**
     * 禁用菜单
     *
     * @param reqDTO 禁用参数
     */
    void changeState(BaseChangeStateDTO<Long> reqDTO);
}
