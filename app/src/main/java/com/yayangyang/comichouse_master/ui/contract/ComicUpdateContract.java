package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.Bean.user.ComicUpdate;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface ComicUpdateContract {

    interface View extends BaseContract.BaseView {
        void showComicUpdateList(List<ComicUpdate> list,int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicUpdateList(int type,int page);
    }

}
