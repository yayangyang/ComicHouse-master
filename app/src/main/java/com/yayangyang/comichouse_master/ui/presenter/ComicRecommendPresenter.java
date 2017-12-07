package com.yayangyang.comichouse_master.ui.presenter;

import android.util.Log;
import android.widget.Toast;

import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicRecommendContract;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicRecommendPresenter extends RxPresenter<ComicRecommendContract.View>
        implements ComicRecommendContract.Presenter {
    private ComicApi comicApi;

    @Inject
    public ComicRecommendPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getComicRecommendList() {
//        String key = StringUtils.creatAcacheKey("recommend-list",channel,version);
//        Observable<List<ComicRecommend>> fromNetWork = comicApi.getRecommend(channel,version)
//                .compose(RxUtil.<List<ComicRecommend>>rxCacheListHelper(key));

        //依次检查disk、network
//        Observable.concat(RxUtil.rxCreateDiskObservable(key,ComicRecommend.class),fromNetWork)
        Disposable rxDisposable = comicApi.getComicRecommend(Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicRecommend>>() {
                            @Override
                            public void accept(List<ComicRecommend> list) throws Exception {
                                LogUtils.e("getRecommend-accept");
                                if (list != null) {
                                    if (mView != null) {
                                        LogUtils.e("eeeeeeeeeeeee");
                                        mView.showComicRecommendList(list);
                                    }
                                }else{
                                    Log.e("recommend","为空");
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getRecommendList"+e.toString());
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
    public void getSubscriptionComic(Map<String, String> params) {
        Disposable rxDisposable = comicApi.getSubsciptionComic(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<SubscriptionComic>() {
                            @Override
                            public void accept(SubscriptionComic subscriptionComic) throws Exception {
                                if (subscriptionComic.data!=null && mView != null) {
                                    mView.showSubscriptionComic(subscriptionComic.data);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getSubscriptionComic-error"+e.toString());
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

    @Override
    public void getElatedComic(Map<String, String> params) {
        Disposable rxDisposable = comicApi.getElatedComic(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<ElatedComic>() {
                            @Override
                            public void accept(ElatedComic elatedComic) throws Exception {
                                if (elatedComic.data!=null && mView != null) {
                                    mView.showElatedComic(elatedComic.data);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getElatedComic-error"+e.toString());
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
