package com.yayangyang.comichouse_master.api.support;

import android.text.TextUtils;
import android.util.Log;

import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/24.
 */

public class BaseInterceptor implements Interceptor{


    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.e("缓存拦截++++++++++++++++++");
        Request request = chain.request();

//        LogUtils.e(request.url().queryParameter("page")+"rrrrrr");
        LogUtils.e("HasPage"+request.header("HasPage"));

        boolean isCache=false;
        if(!TextUtils.isEmpty(request.header("HasPage"))){
            String hasPage = request.header("HasPage");
            LogUtils.e(request.url());
            int i = Integer.parseInt(hasPage);
            ArrayList<Integer> allIndex = StringUtils.getAllIndex(request.url().toString(), "/");
            LogUtils.e(allIndex.toString());
            LogUtils.e(allIndex.size());
            int index = allIndex.get(allIndex.size() - i);//从后面取
            LogUtils.e("index:"+index);
            String number = StringUtils.getNumberByIndex(request.url().toString(), index);
            LogUtils.e("Eeee"+number);
            if(!TextUtils.isEmpty(number)){
                int page = Integer.parseInt(number);
                boolean b = page == 0 ? (isCache = true) : (isCache = false);
                LogUtils.e("Eeee");
            }
            LogUtils.e("isCache:"+isCache);
        }else{
            isCache=true;
        }
        LogUtils.e("网络是否可用:"+NetworkUtils.isAvailable(AppUtils.getAppContext()));
        if (!NetworkUtils.isAvailable(AppUtils.getAppContext())&&isCache) {
            /**
             * 离线缓存控制  总的缓存时间=在线缓存时间(在这个时间内只会走缓存)+设置离线缓存时间(在线缓存时间结束开始离线缓存时间倒计时)
             */
            //如果没网络,若有缓存设置缓存离线保存时间(没更新缓存只有第一次有用)
            int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
            CacheControl tempCacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
            LogUtils.e("wwwwww", "intercept:no network ");
        }
        return chain.proceed(request);
    }
}
