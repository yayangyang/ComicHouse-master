package com.yayangyang.comichouse_master.base;

import com.yayangyang.comichouse_master.Bean.user.Login;

import java.util.Map;

public interface BaseLoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess(Login login);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void login(Map<String, String> params);
    }
}
