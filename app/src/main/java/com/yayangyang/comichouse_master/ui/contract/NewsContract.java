package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.Fabulous;
import com.yayangyang.comichouse_master.Bean.NewsBean;
import com.yayangyang.comichouse_master.base.BaseContract;

public interface NewsContract {

    interface View extends BaseContract.BaseView {
        void showNews(NewsBean newsBean);

        void showFabulousResult(Fabulous fabulous);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNews(String objectId);

        void fabulous(String objectId);
    }

}
