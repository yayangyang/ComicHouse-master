package com.yayangyang.comichouse_master.base;

import com.yayangyang.comichouse_master.Bean.user.Login;

public interface BaseLoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess(Login login);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void login(String uid, String token, String platform);
    }
}
