package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface ComicRecommendContract {

    interface View extends BaseContract.BaseView {
        void showComicRecommendList(List<ComicRecommend> list);

        void showSubscriptionComic(SubscriptionComic.DataBean data);

        void showElatedComic(ElatedComic.DataBean data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicRecommendList();

        void getSubscriptionComic(Map<String, String> params);

        void getElatedComic(Map<String, String> params);
    }

}
