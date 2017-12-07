package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.ComicRankPopupWindowBean;
import com.yayangyang.comichouse_master.base.Constant;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicRankComicTypeBean extends ComicRankPopupWindowBean {

    public ComicRankComicTypeBean(String title, @Constant.ComicType int type, boolean isDate){
        this.title=title;
        this.type=type;
        this.isDate=isDate;
    }

}
