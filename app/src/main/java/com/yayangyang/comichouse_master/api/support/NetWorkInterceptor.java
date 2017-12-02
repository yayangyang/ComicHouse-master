package com.yayangyang.comichouse_master.api.support;

import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealInterceptorChain;

/**
 * Created by Administrator on 2017/11/18.
 */

public class NetWorkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
        if (!NetworkUtils.isAvailable(AppUtils.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtils.e("no network");
        }

        //取得响应结果,每个添加进client自定义的插补器调用proceed都会取得响应结果,越后添加越先取得,
        //具体的请求是在okhttp3定义的拦截器ConnectInterceptor里执行的,调用proceed方法若存在下一个
        //拦截器则会调用下一个拦截器的intercept方法,ConnectInterceptor就添加在所有的拦截器的最后面
        //ConnectInterceptor取得响应结果就递归返回
        Response response = chain.proceed(request);

        if (NetworkUtils.isAvailable(AppUtils.getAppContext())) {
            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
            LogUtils.e("has network maxAge="+maxAge);
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            LogUtils.e("network error");
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            LogUtils.e("has maxStale="+maxStale);
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
            LogUtils.e("response build maxStale="+maxStale);
        }

        return response;
    }
}
