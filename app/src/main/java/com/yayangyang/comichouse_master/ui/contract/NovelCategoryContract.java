package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NovelCategoryContract {

    interface View extends BaseContract.BaseView {
        void showNovelCategoryList(List<NovelCategory> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNovelCategoryList();
    }

}
