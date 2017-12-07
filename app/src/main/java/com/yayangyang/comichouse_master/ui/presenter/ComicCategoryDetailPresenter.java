package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryContract;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryDetailContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicCategoryDetailPresenter extends RxPresenter<ComicCategoryDetailContract.View>
        implements ComicCategoryDetailContract.Presenter {
    private ComicApi comicApi;

    @Inject
    public ComicCategoryDetailPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getComicCategoryDetailList(String tagId,String type, final int page) {
        Disposable rxDisposable = comicApi.getComicCategoryDetail(tagId,type,page
                +"",Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ComicCategoryDetail>>() {
                            @Override
                            public void accept(List<ComicCategoryDetail> list) throws Exception {
                                LogUtils.e("getComicCategory-accept"+list.size());
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showComicCategoryDetailList(list,page);
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
