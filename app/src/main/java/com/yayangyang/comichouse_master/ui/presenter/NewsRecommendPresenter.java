package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.NewsCommonPresenter;
import com.yayangyang.comichouse_master.base.RxPresenter;
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

public class NewsRecommendPresenter extends NewsCommonPresenter<NewsRecommendContract.View>
        implements NewsRecommendContract.Presenter {

    @Inject
    public NewsRecommendPresenter(ComicApi comicApi) {
        LogUtils.e("NewsRecommendPresenter");
        this.comicApi = comicApi;
    }

    @Override
    public void getNewsRecommendHeader() {
        Disposable rxDisposable = comicApi.getNewsRecommendHeader(Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<NewsRecommendHeader>() {
                            @Override
                            public void accept(NewsRecommendHeader header) throws Exception {
                                if(header!=null){
                                    if (header.data != null&& !header.data.isEmpty() && mView != null) {
                                        mView.showNewsRecommendHeader(header.data);
                                    }
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getNewsRecommendHeader"+e.toString());
                                if (!NetworkUtils.isAvailable(AppUtils.getAppContext())){
                                    ToastUtils.showToast("网络异常");
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }
                );
        addDisposable(rxDisposable);
    }

}
