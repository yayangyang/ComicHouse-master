package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.ComicRankPopupWindowBean;
import com.yayangyang.comichouse_master.base.Constant;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicRankDateTypeBean extends ComicRankPopupWindowBean {

    public ComicRankDateTypeBean(String title, @Constant.DateType int type, boolean isDate){
        this.title=title;
        this.type=type;
        this.isDate=isDate;
    }

}
