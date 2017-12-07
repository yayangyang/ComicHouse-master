package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryDetailContract;
import com.yayangyang.comichouse_master.ui.contract.NovelRankContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NovelRankPresenter extends RxPresenter<NovelRankContract.View>
        implements NovelRankContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public NovelRankPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getNovelRankList(String tagId,String type, final int page) {
        Disposable rxDisposable = comicApi.getNovelRank(type,tagId,page
                +"",Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<NovelRank>>() {
                            @Override
                            public void accept(List<NovelRank> list) throws Exception {
                                LogUtils.e("getNovelRankList-accept"+list.size());
                                if (mView != null) {
                                    mView.showNovelRankList(list,page);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getNovelRankList"+e.toString());
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
