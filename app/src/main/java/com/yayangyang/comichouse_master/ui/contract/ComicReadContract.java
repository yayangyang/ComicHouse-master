package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.base.BaseContract;
import com.yayangyang.comichouse_master.base.Constant;

import java.util.ArrayList;
import java.util.List;

public interface ComicReadContract {

    interface View extends BaseContract.BaseView {
        void showComicChapter(ArrayList<ComicDetailHeader.ChaptersBean.DataBean> list);

        void showComicChapterDetail(ComicRead comicRead,boolean isLoadTop);

        void showComicReadHotView(List<ComicReadHotView> list,String chapterId,boolean isLoadTop);

        void showComicReadViewPoint(List<ComicReadViewPoint> list,String chapterId,boolean isLoadTop);

        void showError(boolean isLoadTop);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getComicChapter(String comicId);

        void getComicChapterDetail(String comicId,String chapterId,boolean isLoadTop);

        void getComicReadHotView(String comicId,String chapterId,boolean isLoadTop);

        void getComicReadViewPoint(String comicId,String chapterId,boolean isLoadTop);
    }

}
