package com.hao.base.common.mp;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.base.common.pojo.query.PageQuery;
import com.hao.base.common.pojo.wrapper.PageBean;

public class MpConverter {

    public static <T> PageBean<T> pageConvert(Page<T> page) {
        return PageBean.<T>builder()
                .page((int) page.getCurrent())
                .pages((int) page.getPages())
                .size((int) page.getSize())
                .total(page.getTotal())
                .list(page.getRecords())
                .build();

    }

    public static <T> Page<T> pageQueryToPage(PageQuery pageQuery) {
        return Page.<T>of(pageQuery.getPage(), pageQuery.getSize());
    }


}
