package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface ComicCategoryDetailContract {

    interface View extends BaseContract.BaseView {
        void showComicCategoryDetailList(List<ComicCategoryDetail> list,int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicCategoryDetailList(String tagId,String type, int page);
    }

}
