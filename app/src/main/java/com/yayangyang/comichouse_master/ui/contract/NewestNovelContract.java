package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.Bean.NewestNovel;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NewestNovelContract {

    interface View extends BaseContract.BaseView {
        void showNewestNovelList(List<NewestNovel> list,int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNewestNovelList(int page);
    }

}
