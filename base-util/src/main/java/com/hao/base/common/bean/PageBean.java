package com.hao.base.common.bean;

import cn.hutool.core.bean.BeanUtil;
import com.hao.base.common.query.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageBean<T> implements Serializable {

    private long total; // 总记录数
    private int pages; // 总页数
    private int page; // 请求页码
    private int size; // 请求的每页数量
    private List<T> list; // 当前页最终的数量（一般最后一页数组不足时才有用，否则一般和 pageSize 保持一致）

    public <E> PageBean<E> convert(Class<E> clazz) {
        List<E> data = BeanUtil.copyToList(list, clazz);
        return PageBean.<E>builder()
            .total(this.total)
            .pages(this.pages)
            .page(this.page)
            .size(this.size)
            .list(data)
            .build();
    }

    public static <E> PageBean<E> empty(PageQuery pageQuery) {
        return PageBean.<E>builder()
            .total(0)
            .pages(0)
            .page(pageQuery.getPage())
            .size(pageQuery.getSize())
            .list(new ArrayList<>())
            .build();
    }
}
