package com.fq.modules.sys.menu.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fq.modules.sys.menu.dto.QuerySysMenuDTO;
import com.fq.modules.sys.menu.dto.SysMenuDTO;
import com.fq.modules.sys.menu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 超chao
 * @Description 系统菜单Mapper
 * @Date 2024/11/21/周四 10:02
 * @Version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 分页查询数据
     * @param reqDTO
     * @return
     */
    IPage<SysMenuDTO> paging(Page<SysMenuDTO> page, @Param("query") QuerySysMenuDTO reqDTO);
    /**
     * 查找用户菜单表
     * @param userId
     * @return
     */
    List<Long> findUserMenu(@Param("userId") Long userId);

    /**
     * 查找全部菜单
     * @return
     */
    List<SysMenu> findAllMenu();
}
