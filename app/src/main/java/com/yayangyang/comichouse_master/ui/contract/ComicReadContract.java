package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.base.BaseContract;
import com.yayangyang.comichouse_master.base.Constant;

import java.util.List;

public interface ComicReadContract {

    interface View extends BaseContract.BaseView {
        void showComicChapter(List<ComicRead> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicChapter(String comicId,String chapterId);
    }

}
