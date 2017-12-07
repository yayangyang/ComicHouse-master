package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface NewComicWeeklyActivityContract {

    interface View extends BaseContract.BaseView {
        void showNewComicWeekly(NewComicWeekly newComicWeeklylist);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNewComicWeekly(String subject);
    }

}
