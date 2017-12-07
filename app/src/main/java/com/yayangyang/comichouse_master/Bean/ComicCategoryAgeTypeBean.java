package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.ComicCategoryPopupWindowBean;
import com.yayangyang.comichouse_master.base.Constant;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicCategoryAgeTypeBean extends ComicCategoryPopupWindowBean {

    public ComicCategoryAgeTypeBean(String title, @Constant.AgeType String categoryType, String detailType){
        this.title=title;
        this.categoryType=categoryType;
        this.detailType=detailType;
    }

}
