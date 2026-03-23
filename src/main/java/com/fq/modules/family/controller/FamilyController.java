package com.fq.modules.family.controller;


import com.fq.ability.anno.RequestLimit;
import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.family.service.IFamilyService;
import com.fq.modules.family.vo.MemberListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 超chao
 * @since 2026-02-25
 */
@Tag(name = "家庭管理")
@RestController
@RequestMapping("/api/front/family")
public class FamilyController extends BaseController {
    @Autowired
    private IFamilyService baseService;

    @GetMapping("/members")
    @Operation(summary = "获取家庭成员列表")
    public ApiRest<List<MemberListVO>> members(){
        return super.success(baseService.members());
    }

    @PostMapping("/join")
    @Operation(summary = "加入家庭")
    @RequestLimit
    public ApiRest<Void> join(@RequestParam(value = "code") String code){
        baseService.join(code);
        return super.success();
    }

    @PostMapping("/editRemark")
    @Operation(summary = "修改成员备注")
    public ApiRest<Void> editRemark(@RequestParam(value = "id") Long id, @RequestParam(value = "remark") String remark){
        baseService.editRemark(id,remark);
        return super.success();
    }

    @DeleteMapping("/remove")
    @Operation(summary = "移除家庭成员")
    public ApiRest<Void> remove(@RequestParam(value = "id") Long id){
        baseService.remove(id);
        return super.success();
    }

    @DeleteMapping("/quit")
    @Operation(summary = "退出家庭")
    public ApiRest<Void> remove(){
        baseService.quit();
        return super.success();
    }

}
