package com.fq.api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author 超chao
 * @Description 排序请求类
 * @Date 2024/10/14/周一 11:00
 * @Version 1.0
 */
@Data
@Schema(title = "排序请求类", description="排序请求类")
public class ListSortReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "排序，desc下降，asc上升")
    private String sort;
}
