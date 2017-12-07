package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface ComicRankDetailContract {

    interface View extends BaseContract.BaseView {
        void showComicRankList(List<ComicInfo> list, int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicRankList(int comicType,int date,int rankType, int page);
    }

}
