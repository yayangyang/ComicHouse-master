package com.yayangyang.comichouse_master.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.ui.contract.ComicDetailContract;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicDetailActivityPresenter extends RxPresenter<ComicDetailContract.View>
        implements ComicDetailContract.Presenter {

    private ComicApi comicApi;

    @Inject
    public ComicDetailActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void getComicDetailHeader(String comicId) {
        Disposable rxDisposable = comicApi.getComicDetailHeader(comicId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<ComicDetailHeader>() {
                            @Override
                            public void accept(ComicDetailHeader comicDetail) throws Exception {
                                LogUtils.e("getComicDetailHeader-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showComicDetailHeader(comicDetail);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicDetailHeader"+e.toString());
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
    public void getComicDetailBody(String comicId, int page) {
        Disposable rxDisposable = comicApi.getComicDetailBody(comicId,page+"",Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Object>>() {
                            @Override
                            public void accept(List<Object> list) throws Exception {
                                LogUtils.e("getComicDetailBody-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    List<ComicDetailBody> comicDetailBodies = new ArrayList<>();
                                    Gson gson = new Gson();
                                    for(int i=0;i<list.size();i++){
                                        if(list.get(i).getClass().getSimpleName().equals("LinkedTreeMap")){
                                            LinkedTreeMap map = (LinkedTreeMap) list.get(i);
                                            LogUtils.e("like_amount:"+map.get("like_amount"));
//                                            LogUtils.e("map.toString():"+map.toString());
//                                            LogUtils.e("gson.toJson(map):"+gson.toJson(map));//map转json
                                            comicDetailBodies.add(gson.fromJson(gson.toJson(map),ComicDetailBody.class));
                                        }
//                                        LogUtils.e("size:"+comicDetailBodies.size());
                                    }
//                                    LogUtils.e("sex:"+comicDetailBodies.get(0).sex);
//                                    LogUtils.e("getSimpleName:"+comicDetailBodies.get(0).masterComment.getClass().getSimpleName());
                                    mView.showComicDetailBody(comicDetailBodies,page);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getComicDetailBody"+e.toString());
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
    public void getAnnouncement(String comicId) {
        Disposable rxDisposable = comicApi.getAnnouncement(comicId,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Announcement>() {
                            @Override
                            public void accept(Announcement announcement) throws Exception {
                                LogUtils.e("getAnnouncement-accept");
                                if (mView != null&&announcement!=null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showAnnouncement(announcement);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getAnnouncement"+e.toString());
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
    public void getIsHelpful(String obj_id, String comment_id, String type) {
        Disposable rxDisposable = comicApi.getIsHelpful(obj_id,comment_id,type,Constant.CHANNEL,Constant.VERSION).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<IsHelpful>() {
                            @Override
                            public void accept(IsHelpful isHelpful) throws Exception {
                                LogUtils.e("getIsHelpful-accept");
                                if (mView != null) {
                                    LogUtils.e("eeeeeeeeeeeee");
                                    mView.showIsHelpful(isHelpful,obj_id,comment_id);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                LogUtils.e("getIsHelpful"+e.toString());
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
