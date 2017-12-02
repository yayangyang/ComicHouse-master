package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.base.NewsCommonContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NewsRecommendContract {

    interface View extends NewsCommonContract.View {
        void showNewsRecommendHeader(List<NewsRecommendHeader.DataBean> data);
    }

    interface Presenter extends NewsCommonContract.Presenter<View> {
        void getNewsRecommendHeader();
    }

}
