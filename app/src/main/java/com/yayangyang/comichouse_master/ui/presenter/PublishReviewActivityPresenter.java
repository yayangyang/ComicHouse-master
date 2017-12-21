package com.yayangyang.comichouse_master.ui.presenter;

import android.os.Looper;

import com.yayangyang.comichouse_master.Bean.ComicReview;
import com.yayangyang.comichouse_master.Bean.UploadImageResult;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.PublishReviewContract;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class PublishReviewActivityPresenter extends RxPresenter<PublishReviewContract.View>
        implements PublishReviewContract.Presenter {

    private ComicApi comicApi;
    private List<File> mFiles;

    @Inject
    public PublishReviewActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void publishReview(Map<String,String> map) {
        Disposable rxDisposable = comicApi.publishReview(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<ComicReview>() {
                            @Override
                            public void accept(ComicReview review) throws Exception {
                                LogUtils.e("publishReview-accept");
                                if (mView != null) {
                                    mView.publishReviewResult(review);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("publishReview"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("complete");
                                if(mView!=null){
                                    mView.complete();
                                }
                            }
                        }
                );
        addDisposable(rxDisposable);
    }

    @Override
    public void uploadImage(Map<String, RequestBody> map) {
        Disposable rxDisposable = comicApi.uploadImage(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<UploadImageResult>() {
                            @Override
                            public void accept(UploadImageResult result) throws Exception {
                                LogUtils.e("uploadImage-accept");
                                if (mView != null) {
                                    mView.uploadImageResult(result);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("uploadImage"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("complete");
                                if(mView!=null){
                                    mView.complete();
                                }
                            }
                        }
                );
        addDisposable(rxDisposable);
    }

    @Override
    public void compressImage(List<File> list, int largestSize, String targetPath, int reqWidth, int reqHeight) {
        Compressor compressedImage=new Compressor(AppUtils.getAppContext());
        mFiles=new ArrayList<>();

        Disposable rxDisposable = Observable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .map(new Function<File, File>() {
                    @Override
                    public File apply(File file) throws Exception {
                        boolean b = Looper.getMainLooper() == Looper.myLooper();
                        LogUtils.e("是否在主线程"+ b);
                        return compressedImage.setQuality(80)
                                .setMaxWidth(reqWidth)
                                .setMaxHeight(reqHeight)
                                .setDestinationDirectoryPath(Constant.PATH_UP_LOAD_PICTURES)
                                .compressToFile(file);
//                        return BitmapUtil.compressImage(file,largestSize,reqWidth,reqHeight);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Exception {
                                mFiles.add(file);
                                LogUtils.e("compressImage-accept");
                                if (mView != null&&list.size()==mFiles.size()) {
                                    mView.compressImageResult(mFiles);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("compressImage"+e.toString());
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("complete");
                            }
                        }
                );
        addDisposable(rxDisposable);
    }

}
