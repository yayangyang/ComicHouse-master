package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.Bean.user.TencentLoginResult;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ReaderApplication;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseLoginActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerMineComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.presenter.LoginActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginActivity extends BaseLoginActivity {

    @Inject
    LoginActivityPresenter mPresenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMineComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void loginComicHouse(TencentLoginResult result) {
        Map<String,String> params=new HashMap<>();
        params.put("uid",result.openid);
        params.put("os","android_MX4 Pro");
        params.put("channel","qq");
        params.put("token",result.pay_token);

        mPresenter.login(params);
    }

    @Override
    protected void loginCancel() {

    }

    @Override
    public void loginSuccess(Login login) {
        ToastUtils.showToast("动漫之家登录成功:");
        ReaderApplication.sLogin=login;
        SettingManager.getInstance().saveLoginInfo(login);
        setResult(Constant.RETURN_DATA);
        finish();
    }

    @OnClick({R.id.login,R.id.sina,R.id.qq,R.id.wetchat})
    public void login(View view){
        if(view.getId()==R.id.login){

        }else if(view.getId()==R.id.sina){

        }else if(view.getId()==R.id.qq){
            ReaderApplication.mTencent.login(this,"all",loginListener);
        }else if(view.getId()==R.id.wetchat){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.register){

        }
        return super.onOptionsItemSelected(item);
    }
}
