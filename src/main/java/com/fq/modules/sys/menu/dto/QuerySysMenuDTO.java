package com.fq.modules.sys.menu.dto;

import com.fq.api.api.dto.PagingReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;


/**
 * @Author 超chao
 * @Description 菜单列表分页查询数据传输类
 * @Date 2024/11/21/周四 10:51
 * @Version 1.0
 */
@Data
@Schema(title = "菜单列表分页查询数据传输类")
public class QuerySysMenuDTO extends PagingReqDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单名称")
    private String metaTitle;
    @Schema(description = "菜单ID")
    private Long id;
    @Schema(description = "排序号")
    private Integer sort;
    @Schema(description = "菜单路径")
    private String path;
    @Schema(description = "菜单类型")
    private String menuType;
    @Schema(description = "菜单状态")
    private Integer state;
    @Schema(description = "开始时间")
    private String startTime;
    @Schema(description = "结束时间")
    private String endTime;
}
