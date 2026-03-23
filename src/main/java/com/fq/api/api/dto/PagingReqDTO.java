package com.fq.api.api.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author 超chao
 * @Description 分页查询类
 * @Date 2024/10/12/周六 14:10
 * @Version 1.0
 */
@Schema(title = "分页参数", description = "分页参数")
@Data
public class PagingReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页码",example = "1")
    @NotNull(message = "当前页码不能为空")
    private Integer current;

    @Schema(description = "每页数量",example = "10")
    @NotNull(message = "每页数量不能为空")
    private Integer size;

    @Schema(description = "排序字段 column下划线命名 create_time",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String sortName;

    @Schema(description = "排序方式 asc/desc",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String sortOrder;

    /**
     * 转换成MyBatis的简单分页对象
     *
     * @return
     */
    public <T> Page<T> toPage() {
        Page<T> page = new Page<>();
        page.setCurrent(this.current);
        page.setSize(this.size);
        if (this.sortName != null && this.sortOrder != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(this.sortName);
            orderItem.setAsc("asc".equalsIgnoreCase(this.sortOrder));
            page.addOrder(orderItem);
        }
        return page;
    }
}
