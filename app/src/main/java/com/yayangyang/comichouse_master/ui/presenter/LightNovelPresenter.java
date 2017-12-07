package com.yayangyang.comichouse_master.ui.presenter;

import android.util.Log;

import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.LightNovelContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LightNovelPresenter extends RxPresenter<LightNovelContract.View>
        implements LightNovelContract.Presenter {
    private ComicApi comicApi;

    @Inject
    public LightNovelPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getNovelList() {
        Disposable rxDisposable = comicApi.getLightNovel(Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<LightNovel>>() {
                            @Override
                            public void accept(List<LightNovel> list) throws Exception {
                                LogUtils.e("getRecommend-accept");
                                if (list != null) {
                                    if (mView != null) {
                                        LogUtils.e("eeeeeeeeeeeee");
                                        mView.showNovelList(list);
                                    }
                                }else{
                                    Log.e("recommend","为空");
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getRecommendList", e.toString());
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
