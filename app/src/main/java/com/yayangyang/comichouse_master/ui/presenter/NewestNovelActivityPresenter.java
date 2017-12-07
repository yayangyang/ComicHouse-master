package com.yayangyang.comichouse_master.ui.presenter;

import android.util.Log;

import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.Bean.NewestNovel;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.LightNovelContract;
import com.yayangyang.comichouse_master.ui.contract.NewestNovelContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/4.
 */

public class NewestNovelActivityPresenter extends RxPresenter<NewestNovelContract.View>
        implements NewestNovelContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public NewestNovelActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getNewestNovelList(final int page) {
        Disposable rxDisposable = comicApi.getNewestLightNovel(page+"",Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<NewestNovel>>() {
                            @Override
                            public void accept(List<NewestNovel> list) throws Exception {
                                LogUtils.e("getNewestNovelList-accept");
                                if (mView != null) {
                                    mView.showNewestNovelList(list,page);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getNewestNovelList", e.toString());
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