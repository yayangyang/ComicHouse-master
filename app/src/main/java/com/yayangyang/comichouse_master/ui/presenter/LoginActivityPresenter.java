package com.yayangyang.comichouse_master.ui.presenter;

import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.base.BaseLoginContract;
import com.yayangyang.comichouse_master.base.RxPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginActivityPresenter extends RxPresenter<BaseLoginContract.View>
        implements BaseLoginContract.Presenter<BaseLoginContract.View> {
    private ComicApi comicApi;

    @Inject
    public LoginActivityPresenter(ComicApi comicApi) {
        this.comicApi = comicApi;
    }

    @Override
    public void login(Map<String, String> params) {
        Disposable rxDisposable = comicApi.login(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Login>() {
                            @Override
                            public void accept(Login data) throws Exception {
//                                if(data.user!=null){
//                                    LogUtils.e("收到了"+data.toString());
//                                }else{
//                                    LogUtils.e("user为空"+data.ok);
//                                }
//                                if (data != null && mView != null) {
//                                    mView.loginSuccess(data);
//                                    LogUtils.e(data.user.toString());
//                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                ToastUtils.showToast("登录失败");
                                LogUtils.e("login" + e.toString());
                                if(mView!=null){
                                    mView.showError();
                                }
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                LogUtils.e("完成");
                                if(mView!=null){
                                    mView.complete();
                                }
                            }
                        }
                );
        addDisposable(rxDisposable);
    }
}
