package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.AuthorIntroduceContract;
import com.yayangyang.comichouse_master.ui.contract.NewComicWeeklyContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthorIntroduceActivityPresenter extends RxPresenter<AuthorIntroduceContract.View>
        implements AuthorIntroduceContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public AuthorIntroduceActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getAuthorIntroduce(String object_id) {
        Disposable rxDisposable = comicApi.getAuthorIntroduce(object_id,
                Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<AuthorIntroduce>() {
                            @Override
                            public void accept(AuthorIntroduce authorIntroduce) throws Exception {
                                LogUtils.e("getAuthorIntroduce-accept");
                                if (mView != null) {
                                    mView.showAuthorIntroduce(authorIntroduce);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getAuthorIntroduce"+e.toString());
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
