package com.fq.modules.sys.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.modules.sys.role.dto.QueryRoleDTO;
import com.fq.modules.sys.role.dto.SysRoleDTO;
import com.fq.modules.sys.role.dto.SysRoleMenuReqDTO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.service.SysRoleMenuService;
import com.fq.modules.sys.role.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 超chao
 * @Description 系统角色控制器
 * @Date 2024/10/23/周三 10:18
 * @Version 1.0
 */
@Tag(name = "系统角色")
@RestController
@RequestMapping("/api/admin/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService baseService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    /**
     * 分页查询--角色列表
     */
    @PreAuthorize("@ss.hasPermi('sys:role:list')")
    @Operation(summary = "分页查询--角色列表")
    @GetMapping(value = "/paging")
    public ApiRest<IPage<SysRoleDTO>> paging(@Validated @ModelAttribute QueryRoleDTO reqDTO) {

        //分页查询并转换
        IPage<SysRoleDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

    /**
     * 增改--角色
     */
    @PreAuthorize("@ss.hasAnyPermi('sys:role:save', 'sys:role:update')")
    @Operation(summary = "增改--角色")
    @PostMapping(value = "/save")
    public ApiRest<String> save(@Validated @RequestBody SysRoleDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success(reqDTO.getId());
    }

    /**
     * 删除--角色
     */
    @PreAuthorize("@ss.hasPermi('sys:role:delete')")
    @Operation(summary = "删除--角色")
    @DeleteMapping(value = "/delete")
    public ApiRest<Void> delete(@RequestParam(value = "id") String id) {
        //根据ID删除
        baseService.deleteById(id);
        return super.success();
    }

    /**
     * 查询--角色详情
     */
    @Operation(summary = "查询--角色详情")
    @GetMapping(value = "/detail")
    public ApiRest<SysRoleDTO> find(@RequestParam(value = "id") Long id) {
        SysRole entity = baseService.getById(id);
        SysRoleDTO dto = new SysRoleDTO();
        BeanUtils.copyProperties(entity, dto);
        return super.success(dto);
    }

    /**
     * 查询--角色菜单授权列表
     */
    @PreAuthorize("@ss.hasPermi('sys:role:grant')")
    @Operation(summary = "查询--角色菜单授权列表")
    @GetMapping(value = "/list-menus")
    public ApiRest<List<Long>> listMenus(@RequestParam(value = "roleId") String roleId) {

        List<Long> ids = sysRoleMenuService.findRoleMenus(roleId);
        return super.success(ids);
    }

    /**
     * 增改--角色菜单授权
     */
    @PreAuthorize("@ss.hasPermi('sys:role:grant')")
    @Operation(summary = "增改--角色菜单授权")
    @PostMapping(value = "/save-menus")
    public ApiRest<Void> saveMenus(@Validated @RequestBody SysRoleMenuReqDTO reqDTO) {

        // 保存授权
        sysRoleMenuService.saveRoleIds(reqDTO.getRoleId(), reqDTO.getMenuIds());
        return super.success();
    }
    /**
     * 启用或禁用角色
     */
    @PreAuthorize("@ss.hasPermi('sys:role:state')")
    @Operation(summary = "更新--启用或禁用角色")
    @PutMapping(value = "/change-state")
    public ApiRest<Void> changeState(@Validated @RequestBody BaseChangeStateDTO<String> reqDTO) {

        // 更新状态
        baseService.updateRoleState(reqDTO);
        return super.success();
    }
}
