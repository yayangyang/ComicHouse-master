package com.yayangyang.comichouse_master.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.tauth.Tencent;
import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.Receiver.NetworkChangeReceiver;
import com.yayangyang.comichouse_master.Receiver.TimeChangeReceiver;
import com.yayangyang.comichouse_master.api.support.HeaderInterceptor;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.CrashHandler;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerAppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.module.AppModule;
import com.yayangyang.comichouse_master.module.ComicApiModule;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import zlc.season.rxdownload3.core.DownloadConfig;
import zlc.season.rxdownload3.extension.ApkInstallExtension;
import zlc.season.rxdownload3.extension.ApkOpenExtension;
import zlc.season.rxdownload3.http.OkHttpClientFactory;

public class ComicApplication extends Application {

    private static ComicApplication sInstance;
    public static Tencent mTencent;
    private AppComponent appComponent;


    public static Login sLogin;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        LeakCanary.install(this);

        sInstance = this;
        initCompoent();
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        initPrefs();
        initNightMode();
        //initHciCloud();

        initLoginInfo();
        compatibleVersion();
        initReceiver();
        initRxDownLoad();
    }

    private void initRxDownLoad() {
        DownloadConfig.Builder builder = DownloadConfig.Builder.Companion.create(this)
                .enableDb(true)
//                .setDbActor(new CustomSqliteActor(this))
                .enableService(true)
                .enableNotification(true)
                .addExtension(ApkInstallExtension.class)
                .addExtension(ApkOpenExtension.class)
                .setOkHttpClientFacotry(new OkHttpClientFactory() {
                    @NotNull
                    @Override
                    public OkHttpClient build() {
                        return new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(15, TimeUnit.SECONDS)
                                .writeTimeout(15, TimeUnit.SECONDS)
                                .addNetworkInterceptor(new HeaderInterceptor()).build();
                    }
                });

        DownloadConfig.INSTANCE.init(builder);
    }

    private void initReceiver() {
        //注册监听网络状态改变广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(NetworkChangeReceiver.getInstance(), filter);

        //注册监听系统时间广播
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(TimeChangeReceiver.getInstance(), filter);
    }

    private void compatibleVersion() {
        // 解决android7.0系统拍照的问题(置入一个不设防的VmPolicy,违背了google的初衷,不是标准解决方法)
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
    }

    private void initLoginInfo() {
        mTencent = Tencent.createInstance("222222", AppUtils.getAppContext());
        sLogin= SettingManager.getInstance().getLoginInfo();
    }

    public static ComicApplication getsInstance() {
        return sInstance;
    }

    private void initCompoent() {
        LogUtils.e("wwwwwwwww");
        appComponent = DaggerAppComponent.builder()
                .comicApiModule(new ComicApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    protected void initNightMode() {
        boolean isNight = SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
        LogUtils.d("isNight=" + isNight);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}
