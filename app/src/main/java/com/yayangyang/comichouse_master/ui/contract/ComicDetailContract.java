package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface ComicDetailContract {

    interface View extends BaseContract.BaseView {
        void showComicDetailHeader(ComicDetailHeader comicDetail);

        void showComicDetailBody(List<ComicDetailBody> list, int page);

        void showAnnouncement(Announcement announcement);

        void showIsHelpful(IsHelpful helpful, String obj_id, String comment_id);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicDetailHeader(String comicId);

        void getComicDetailBody(String comicId,int page);

        void getAnnouncement(String comicId);

        void getIsHelpful(String obj_id,String comment_id,String type);
    }

}
