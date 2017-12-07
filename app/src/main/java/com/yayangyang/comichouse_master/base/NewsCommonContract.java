package com.yayangyang.comichouse_master.base;

import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NewsCommonContract {

    interface View extends BaseContract.BaseView {
        void showNewsCommonBody(List<NewsCommonBody> list, int page);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getNewsCommonBody(int newsType,int page);
    }

}
