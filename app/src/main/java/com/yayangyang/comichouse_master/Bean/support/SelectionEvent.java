package com.yayangyang.comichouse_master.Bean.support;


import com.yayangyang.comichouse_master.base.Constant;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class SelectionEvent {

    public String distillate;

    public String type;

    public String sort;

    public SelectionEvent(@Constant.Distillate String distillate,
                          @Constant.BookType String type,
                          @Constant.SortType String sort) {
        this.distillate = distillate;
        this.type = type;
        this.sort = sort;
    }

    public SelectionEvent(@Constant.SortType String sort) {
        this.sort = sort;
    }
}
