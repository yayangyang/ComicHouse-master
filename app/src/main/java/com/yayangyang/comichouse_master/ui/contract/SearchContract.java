package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.HotSearch;
import com.yayangyang.comichouse_master.Bean.SearchInfo;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface SearchContract {

    interface View extends BaseContract.BaseView {
        void showHotSearch(List<HotSearch> list);

        void showSearchInfo(List<SearchInfo> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getHotSearch(int type);

        void getSearchInfo(int type,String keyWord,int page);
    }

}
