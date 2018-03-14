package com.yayangyang.comichouse_master.module;

import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.api.support.BaseInterceptor;
import com.yayangyang.comichouse_master.api.support.HeaderInterceptor;
import com.yayangyang.comichouse_master.api.support.NetWorkInterceptor;
import com.yayangyang.comichouse_master.api.support.RewriteCacheControlInterceptor;
import com.yayangyang.comichouse_master.base.Constant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ComicApiModule {

    @Provides
    public OkHttpClient provideOkHttpClient() {

//        LoggingInterceptor logging = new LoggingInterceptor(new Logger());
//        logging.setLevel(LoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置缓存路径
        File httpCacheDirectory = new File(Constant.PATH_RESPONSES);
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .addInterceptor(new BaseInterceptor())
                .addNetworkInterceptor(new RewriteCacheControlInterceptor())
                .cache(cache);
        return builder.build();
    }

    @Provides
    protected ComicApi provideBookService(OkHttpClient okHttpClient) {
        return ComicApi.getInstance(okHttpClient);
    }
}