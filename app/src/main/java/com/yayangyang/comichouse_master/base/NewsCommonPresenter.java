package com.yayangyang.comichouse_master.base;

import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.ui.contract.NewsRecommendContract;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsCommonPresenter<T extends NewsCommonContract.View> extends RxPresenter<T>
        implements NewsCommonContract.Presenter<T> {

    protected ComicApi comicApi;

    public NewsCommonPresenter(){}

    @Inject
    public NewsCommonPresenter(ComicApi comicApi) {
        LogUtils.e("wwwwwww");
        this.comicApi = comicApi;
    }

    @Override
    public void getNewsCommonBody(final int page) {
        Disposable rxDisposable = comicApi.getNewsCommonBody(page+"",Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<NewsCommonBody>>() {
                            @Override
                            public void accept(List<NewsCommonBody> list) throws Exception {
                                LogUtils.e("getNewsRecommendBody-accept");
                                if (list!=null && mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showNewsCommonBody(list,page);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getNewsRecommendBody-error"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("getNewsRecommendBody-complete");
                                if(mView!=null){
                                    mView.complete();
                                }
                            }
                        }
                );
        addDisposable(rxDisposable);
    }

}
