package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.ComicCategoryPopupWindowBean;
import com.yayangyang.comichouse_master.Bean.base.ComicRankPopupWindowBean;
import com.yayangyang.comichouse_master.base.Constant;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicCategoryThemeTypeBean extends ComicCategoryPopupWindowBean {

    public ComicCategoryThemeTypeBean(String title, @Constant.ThemeType String categoryType, String detailType){
        this.title=title;
        this.categoryType=categoryType;
        this.detailType=detailType;
    }

}
