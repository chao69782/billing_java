package com.fq.modules.sys.menu.controller;

import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.api.api.dto.ListSortReqDTO;
import com.fq.modules.sys.menu.dto.SysMenuDTO;
import com.fq.modules.sys.menu.entity.SysMenu;
import com.fq.modules.sys.menu.service.SysMenuService;
import com.fq.modules.sys.menu.vo.MenuRouteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 超chao
 * @Description 系统菜单控制器
 * @Date 2024/11/21/周四 10:20
 * @Version 1.0
 */
@Tag(name = "系统菜单")
@RestController
@RequestMapping("/api/admin/sys/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService baseService;

    @Operation(summary = "查询--用户个人菜单列表")
    @GetMapping(value = "/routes")
    public ApiRest<List<MenuRouteVO>> routes(@RequestParam(value = "userId") Long userId) {
        List<MenuRouteVO> list = baseService.listRoutes(userId);
        return super.success(list);
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:add', 'sys:menu:update')")
    @Operation(summary = "增改--菜单信息")
    @PostMapping(value = "/save")
    public ApiRest<Void> save(@Validated @RequestBody SysMenuDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @Operation(summary = "批量删除--菜单信息")
    @DeleteMapping(value = "/delete")
    public ApiRest<Void> edit(@RequestParam("ids") List<Long> ids) {
        //根据ID删除
        baseService.delete(ids);
        return super.success();
    }

    @Operation(summary = "查询--菜单详情")
    @GetMapping(value = "/detail")
    public ApiRest<SysMenuDTO> find(@RequestParam(value = "id") Long id) {
        SysMenu entity = baseService.getById(id);
        SysMenuDTO dto = new SysMenuDTO();
        BeanUtils.copyProperties(entity, dto);
        return super.success(dto);
    }


    @Operation(summary = "查询--菜单树结构")
    @GetMapping(value = "/tree")
    public ApiRest<List<MenuRouteVO>> tree() {

        //分页查询并转换
        List<MenuRouteVO> list = baseService.listTree();

        return super.success(list);
    }

    @PreAuthorize("hasAuthority('sys:menu:sort')")
    @Operation(summary = "操作--菜单排序")
    @PutMapping(value = "/sort")
    public ApiRest<Void> sort(@RequestBody ListSortReqDTO reqDTO) {
        baseService.sort(reqDTO.getId(), reqDTO.getSort());
        return super.success();
    }
    @PreAuthorize("hasAuthority('sys:menu:state')")
    @Operation(summary = "操作--禁用启用菜单")
    @PutMapping(value = "/change-state")
    public ApiRest<Void> changeState(@Validated @RequestBody BaseChangeStateDTO<Long> reqDTO) {
        baseService.changeState(reqDTO);
        return super.success();
    }

}
