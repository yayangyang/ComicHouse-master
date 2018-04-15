package com.yayangyang.comichouse_master.api.support;

import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.DeviceUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Header拦截器。用于保存和设置Cookies
 */
public final class HeaderInterceptor implements Interceptor {

    final String vmName = DeviceUtils.getCurrentRuntimeValue();
    final String vmVersion = DeviceUtils.getVmVersion();
    final String MODEL = Build.MODEL;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String url = original.url().toString();
        if (url.contains("book/") ||
                url.contains("book-list/") ||
                url.contains("toc/") ||
                url.contains("post/") ||
                url.contains("user/")) {
            LogUtils.e("请求头11111111111"+chain.request().toString());
            original = original.newBuilder()
                    .addHeader("User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]") // 不能转UTF-8
                    .addHeader("X-User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]")
                    .addHeader("X-Device-Id", DeviceUtils.getIMEI(AppUtils.getAppContext()))
                    .addHeader("Host", "api.zhuishushenqi.com")
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("If-None-Match", "W/\"2a04-4nguJ+XAaA1yAeFHyxVImg\"")
                    .addHeader("If-Modified-Since", "Tue, 02 Aug 2016 03:20:06 UTC")
                    .build();
        }else{
            LogUtils.e("请求头22222222222222"+chain.request().toString());
            boolean isMainThread=Looper.getMainLooper() == Looper.myLooper();
            LogUtils.e("是否主线程:"+ isMainThread);

            String userAgent=vmName+"/"+vmVersion+" (Linux; U; Android "+android.os.Build.VERSION.RELEASE
                    +"; "+MODEL+" Build/NYC)";
            LogUtils.e(userAgent);
            original = original.newBuilder()
                    //"Dalvik/2.1.0 (Linux; U; Android 7.1.1; Android SDK built for x86 Build/NYC)"
                    .addHeader("User-Agent",userAgent)//未在arm架构上测试
//                    .addHeader("Referer",Constant.IMG_BASE_URL)//出现403错误(取消了这个头,增加了User-Agent头)
//                    .addHeader("Accept-Encoding","gzip")//加上报错,不知原因
                    .build();
        }

        return chain.proceed(original);
    }
}
