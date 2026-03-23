package com.fq.modules.sys.menu.vo;

import cn.hutool.core.util.StrUtil;
import com.fq.modules.sys.menu.dto.SysMenuDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 超chao
 * @Description 菜单路由响应类
 * @Date 2024/11/21/周四 10:08
 * @Version 1.0
 */
@Data
public class MenuRouteVO extends SysMenuDTO {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 父级菜单
     */
    @Schema(description = "父级菜单")
    private String parentMenu;
    /**
     * 子路由表
     */
    @Schema(description = "子菜单列表")
    private List<MenuRouteVO> children;
    /**
     * 路由属性
     */
    private Map<String,Object> meta;

    /**
     * 获取属性
     * @return
     */
    public Map<String,Object> getMeta(){
        Map<String,Object> meta = new HashMap<>();
        if(!StrUtil.isBlank(this.getMetaTitle())){
            meta.put("title", this.getMetaTitle());
        }

        if(!StrUtil.isBlank(this.getMetaIcon())){
            meta.put("icon", this.getMetaIcon());
        }

        if(this.getMetaNoCache()!=null){
            meta.put("noCache", this.getMetaNoCache());
        }

        if(!StrUtil.isBlank(this.getMetaActiveMenu())){
            meta.put("activeMenu", this.getMetaActiveMenu());
        }
        return meta;
    }
}
