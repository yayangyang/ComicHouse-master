package com.yayangyang.comichouse_master.api.support;

import com.yayangyang.comichouse_master.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/24.
 */

public class RewriteCacheControlInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.e("网络拦截++++++++++++++++++++");
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
//        int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒//一分钟内只走缓存
//        return originalResponse.newBuilder()
//                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                .removeHeader("Cache-Control")
//                .header("Cache-Control", "public, max-age=" + maxAge)
//                .build();

        LogUtils.e(originalResponse.header("Cache-Control"));
        int maxAge = 0; // 有网络时 设置缓存超时时间0个小时
        LogUtils.e("has network maxAge="+maxAge);
        return originalResponse.newBuilder()
                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();
        //按照原来的response新建一个新的response,可直接返回,
        // 如果没直接返回response记得将新的response赋值给旧的response(在这里相当于没添加在线缓存策略,会报504错误)
    }
}
