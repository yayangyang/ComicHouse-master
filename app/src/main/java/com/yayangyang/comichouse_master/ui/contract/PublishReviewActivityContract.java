package com.yayangyang.comichouse_master.ui.contract;

import com.yayangyang.comichouse_master.Bean.ComicReview;
import com.yayangyang.comichouse_master.Bean.UploadImageResult;
import com.yayangyang.comichouse_master.base.BaseContract;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface PublishReviewActivityContract {

    interface View extends BaseContract.BaseView {
        void publishReviewResult(ComicReview review);

        void uploadImageResult(UploadImageResult uploadImageResult);

        void compressImageResult(List<File> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void publishReview(Map<String,String> map);

        void uploadImage(Map<String, RequestBody> map);

        void compressImage(List<File> list,int largestSize, String targetPath, int reqWidth, int reqHeight);
    }

}
