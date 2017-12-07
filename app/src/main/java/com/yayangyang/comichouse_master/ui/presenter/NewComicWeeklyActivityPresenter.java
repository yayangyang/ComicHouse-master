package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryDetailContract;
import com.yayangyang.comichouse_master.ui.contract.NewComicWeeklyContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewComicWeeklyActivityPresenter extends RxPresenter<NewComicWeeklyContract.View>
        implements NewComicWeeklyContract.Presenter {
    private ComicApi comicApi;

    @Inject
    public NewComicWeeklyActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getNewComicWeekly(String object_id) {
        Disposable rxDisposable = comicApi.getNewComicWeekly(object_id,
                Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<NewComicWeekly>() {
                            @Override
                            public void accept(NewComicWeekly newComicWeekly) throws Exception {
                                LogUtils.e("getNewComicWeekly-accept");
                                if (mView != null) {
                                    mView.showNewComicWeekly(newComicWeekly);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getNewComicWeekly"+e.toString());
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
