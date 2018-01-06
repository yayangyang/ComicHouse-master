package com.yayangyang.comichouse_master.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicDetailContract;
import com.yayangyang.comichouse_master.ui.contract.ComicReadContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicReadActivityPresenter extends RxPresenter<ComicReadContract.View>
        implements ComicReadContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public ComicReadActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getComicChapterDetail(String comicId,String chapterId,boolean isLoadTop) {
        Disposable rxDisposable = comicApi.getComicChapter(comicId,chapterId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<ComicRead>() {
                            @Override
                            public void accept(ComicRead comicRead) throws Exception {
                                LogUtils.e("getComicChapter-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showComicChapter(comicRead,isLoadTop);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicChapter"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                    mView.showError(isLoadTop);
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
    public void getComicReadHotView(String comicId, String chapterId,boolean isLoadTop) {
        Disposable rxDisposable = comicApi.getComicReadHotView(comicId,chapterId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicReadHotView>>() {
                            @Override
                            public void accept(List<ComicReadHotView> list) throws Exception {
                                LogUtils.e("getComicReadHotView-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showComicReadHotView(list,chapterId,isLoadTop);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicReadHotView"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                    mView.showError(isLoadTop);
                                }
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

    @Override
    public void getComicReadViewPoint(String comicId, String chapterId,boolean isLoadTop) {
        Disposable rxDisposable = comicApi.getComicReadViewPoint(comicId,chapterId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicReadViewPoint>>() {
                            @Override
                            public void accept(List<ComicReadViewPoint> list) throws Exception {
                                LogUtils.e("getComicReadViewPoint-accept");
                                if (mView != null) {
                                    mView.showComicReadViewPoint(list,chapterId,isLoadTop);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicReadViewPoint"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                    mView.showError(isLoadTop);
                                }
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
