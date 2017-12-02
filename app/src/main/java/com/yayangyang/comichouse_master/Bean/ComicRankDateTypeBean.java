package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;
import com.yayangyang.comichouse_master.base.Constant;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicRankDateTypeBean extends ComicRankItemBean {

    public ComicRankDateTypeBean(String title, @Constant.DateType int type, boolean isDate){
        this.title=title;
        this.type=type;
        this.isDate=isDate;
    }

}
