package com.yayangyang.comichouse_master.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicRead;
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
    public void getComicChapter(String comicId,String chapterId) {
        Disposable rxDisposable = comicApi.getComicChapter(comicId,chapterId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicRead>>() {
                            @Override
                            public void accept(List<ComicRead> list) throws Exception {
                                LogUtils.e("getComicChapter-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showComicChapter(list);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicChapter"+e.toString());
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

}
