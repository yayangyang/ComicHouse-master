package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryContract;
import com.yayangyang.comichouse_master.ui.contract.NovelCategoryContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NovelCategoryActivityPresenter extends RxPresenter<NovelCategoryContract.View>
        implements NovelCategoryContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public NovelCategoryActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getNovelCategoryList() {
        Disposable rxDisposable = comicApi.getNovelCategory(Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<NovelCategory>>() {
                            @Override
                            public void accept(List<NovelCategory> list) throws Exception {
                                LogUtils.e("getComicCategoryList-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showNovelCategoryList(list);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicCategoryList"+e.toString());
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
