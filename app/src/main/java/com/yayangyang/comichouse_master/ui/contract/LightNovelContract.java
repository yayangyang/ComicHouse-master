package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface LightNovelContract {

    interface View extends BaseContract.BaseView {
        void showNovelList(List<LightNovel> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNovelList();
    }

}
