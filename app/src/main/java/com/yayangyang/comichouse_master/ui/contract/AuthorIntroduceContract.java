package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface AuthorIntroduceContract {

    interface View extends BaseContract.BaseView {
        void showAuthorIntroduce(AuthorIntroduce authorIntroduce);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getAuthorIntroduce(String object_id);
    }

}
