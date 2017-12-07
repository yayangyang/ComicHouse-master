package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategoryDetail;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NovelCategoryDetailContract {

    interface View extends BaseContract.BaseView {
        void showNovelCategoryDetailList(List<NovelCategoryDetail> list, int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNovelCategoryDetailList(String tagId,String schduleType,String type,int page);
    }

}
