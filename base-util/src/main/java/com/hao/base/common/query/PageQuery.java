package com.hao.base.common.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hao
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
@ToString
public class PageQuery {

    @Builder.Default
    @Positive(message = "页码必须为正数")
    private int page = 1; // 默认查询第一页

    @Builder.Default
    @Positive(message = "每页记录数必须为正数")
    @Max(value = 100, message = "每页大小不得超过 100")
    private int size = 10; // 默认每页 10 条

    private List<Order> orders;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    @Builder
    @ToString
    public static class Order {
        private String property;

        @Builder.Default
        private boolean desc = false; // 默认升序
    }

}
