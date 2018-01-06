package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicReadContract;
import com.yayangyang.comichouse_master.ui.contract.PublishViewPointContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PublishViewPointActivityPresenter extends RxPresenter<PublishViewPointContract.View>
        implements PublishViewPointContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public PublishViewPointActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getComicReadViewPoint(String comicId, String chapterId) {
        Disposable rxDisposable = comicApi.getComicReadViewPoint(comicId,chapterId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicReadViewPoint>>() {
                            @Override
                            public void accept(List<ComicReadViewPoint> list) throws Exception {
                                LogUtils.e("getComicReadViewPoint-accept");
                                if (mView != null) {
                                    mView.showComicReadViewPoint(list);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicReadViewPoint"+e.toString());
                                if(mView!=null){
                                    mView.showError();
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("complete");
                            }
                        }
                );
        addDisposable(rxDisposable);
    }

}
