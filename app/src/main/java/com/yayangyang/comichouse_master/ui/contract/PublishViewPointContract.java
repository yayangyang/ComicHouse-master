package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.util.List;

public interface PublishViewPointContract {

    interface View extends BaseContract.BaseView {

        void showComicReadViewPoint(List<ComicReadViewPoint> list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getComicReadViewPoint(String comicId, String chapterId);
    }

}
