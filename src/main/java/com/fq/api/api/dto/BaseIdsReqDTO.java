package com.fq.api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 超chao
 * @Description 通用ID列表类操作，用于批量删除、修改状态等
 * @Date 2024/10/14/周一 10:54
 * @Version 1.0
 */
@Data
@Schema(title = "ID列表参数", description = "ID列表参数")
public class BaseIdsReqDTO implements Serializable {

    @Schema(description = "ID列表", required = true)
    private List<Long> ids;
}
