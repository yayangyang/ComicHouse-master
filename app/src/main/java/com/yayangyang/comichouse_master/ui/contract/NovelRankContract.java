package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public interface NovelRankContract {

    interface View extends BaseContract.BaseView {
        void showNovelRankList(List<NovelRank> list, int page);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getNovelRankList(String tagId, String type, int page);
    }

}
