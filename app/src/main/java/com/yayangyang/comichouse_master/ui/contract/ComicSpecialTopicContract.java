package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicSpecialTopic;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface ComicSpecialTopicContract {

    interface View extends BaseContract.BaseView {
        void showComicSpecialTopicList(List<ComicSpecialTopic> list, int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicSpecialTopicList(int page);
    }

}
