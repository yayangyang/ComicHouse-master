package com.yayangyang.comichouse_master.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BasePermissionActivity;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BasePermissionActivity{

    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
//    @BindView(R.id.tvSkip)
//    TextView tvSkip;

    private int count=0;

    private Runnable allow,refuse;
    private String[] permissionCollection={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

//    @Inject
//    public SplashActivityPresenter mPresenter;

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else{
//                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            }
            finish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        setupActivityComponent(ComicApplication.getsInstance().getAppComponent());
//        mPresenter.attachView(this);

        if (SettingManager.getInstance().isFirstEnter()) {
            init();
            requeset();
        } else {
            goHome();
        }
    }

    private void init() {
        allow=new Runnable(){
            @Override
            public void run() {
                //同意
                LogUtils.e("同意");

                count++;
                if(count==permissionCollection.length){
                    goHome();
                }
            }
        };
        refuse=new Runnable(){
            @Override
            public void run() {
                //拒绝
                LogUtils.e("拒绝");

                count++;
                if(count==permissionCollection.length){
                    goHome();
                }
            }
        };
    }

    private void requeset() {
        ToastUtils.showToast("request");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0后诸多权限才需要申请
            requestPermission(0, permissionCollection,allow,refuse);

            //申请调节亮度需要的WRITE_SETTING权限(放在前面申请的权限后面才会弹出框)
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }else{
            goHome();
        }

        SettingManager.getInstance().savaFirstEnter(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    private synchronized void goHome() {
        mHandler.sendEmptyMessage(0);

        //确保有权限后验证是否需要登录
        if(ComicApplication.sLogin==null){
            LogUtils.e("sLogin为空");
//            mHandler.sendEmptyMessageDelayed(0,1000);
        }else if(!TextUtils.isEmpty(ComicApplication.sLogin.data.dmzj_token)){
//            mPresenter.checkLogin(ComicApplication.sLogin.token);
        }
    }

    public void setupActivityComponent(AppComponent appComponent) {
//        DaggerMyComponent.builder()
//                .appComponent(appComponent)
//                .build()
//                .inject(this);
    }

//    @Override
//    public void showIsLogin(boolean isLogin) {
//        if(!isLogin){
//            ComicApplication.sLogin=null;
//            SettingManager.getInstance().saveLoginInfo(null);
//            mHandler.sendEmptyMessageDelayed(1,1000);
//        }else{
//            mHandler.sendEmptyMessageDelayed(0,1000);
//        }
//    }
//
//    @Override
//    public void showError() {
//        if(!NetworkUtils.isAvailable(this)){
//            ToastUtils.showToast("网络有问题");
//        }
//        mHandler.sendEmptyMessageDelayed(1,1000);
//    }
//
//    @Override
//    public void complete() {
//
//    }
//
    @Override
    protected void onDestroy() {
        LogUtils.e("SplashActivity-onDestroy++++++++++++++++++++++");
        super.onDestroy();
//        mPresenter.detachView();
        BitmapDrawable drawable = (BitmapDrawable) rl_root.getBackground();
        drawable.getBitmap().recycle();
        System.runFinalization();
        System.gc();
    }

}
