package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.HotSearch;
import com.yayangyang.comichouse_master.Bean.SearchInfo;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.AuthorIntroduceContract;
import com.yayangyang.comichouse_master.ui.contract.SearchContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivityPresenter extends RxPresenter<SearchContract.View>
        implements SearchContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public SearchActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getHotSearch(int type) {
        Disposable rxDisposable = comicApi.getHotSearch(type+"",
                Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<HotSearch>>() {
                            @Override
                            public void accept(List<HotSearch> list) throws Exception {
                                LogUtils.e("getHotSearch-accept");
                                if (mView != null) {
                                    mView.showHotSearch(list);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getHotSearch"+e.toString());
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
    public void getSearchInfo(int type,String keyWord,int page) {
        Disposable rxDisposable = comicApi.getSearchInfo(type+"",keyWord,page+"",
                Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<SearchInfo>>() {
                            @Override
                            public void accept(List<SearchInfo> list) throws Exception {
                                LogUtils.e("getSearchInfo-accept");
                                if (mView != null) {
                                    mView.showSearchInfo(list);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getSearchInfo"+e.toString());
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
