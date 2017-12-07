package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface NewComicWeeklyContract {

    interface View extends BaseContract.BaseView {
        void showNewComicWeekly(NewComicWeekly newComicWeekly);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNewComicWeekly(String object_id);
    }

}
