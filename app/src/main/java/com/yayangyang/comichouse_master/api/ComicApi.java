package com.yayangyang.comichouse_master.api;


import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.ComicSpecialTopic;
import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.Bean.NewestNovel;
import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.Bean.user.ComicUpdate;
import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.base.Constant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;

public class ComicApi {

    public static ComicApi instance;

    private ComicApiService service;

    public ComicApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(ComicApiService.class);
    }

    public static ComicApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new ComicApi(okHttpClient);
        return instance;
    }

    public Observable<List<ComicRecommend>> getComicRecommend(String channel, String version) {
        return service.getComicRecommend(channel,version);
    }

    public Observable<SubscriptionComic> getSubsciptionComic(@QueryMap Map<String, String> params){
        return service.getSubsciptionComic(params);
    }

    public Observable<ElatedComic> getElatedComic(@QueryMap Map<String, String> params){
        return service.getElatedComic(params);
    }

    public Observable<Login> login(Map<String, String> params){
        return service.login(params);
    }

    public Observable<List<LightNovel>> getLightNovel(String channel, String version){
        return service.getLightNovel(channel,version);
    }

    public Observable<NewsRecommendHeader> getNewsRecommendHeader(String channel, String version){
        return service.getNewsRecommendHeader(channel,version);
    }

    public Observable<List<NewsCommonBody>> getNewsCommonBody(String newsType,String page, String channel, String version){
        return service.getNewsCommonBody(newsType,page,channel,version);
    }

    public Observable<List<ComicUpdate>> getComicUpdate(String type,String page,String channel, String version) {
        return service.getComicUpdate(type,page,channel,version);
    }

    public Observable<List<ComicCategory>> getComicCategory(String channel, String version) {
        return service.getComicCategory(channel,version);
    }

    public Observable<List<ComicInfo>> getComicRank(String comicType, String date, String rankType, String page, String channel, String version) {
        return service.getComicRank(comicType,date,rankType,page,channel,version);
    }

    public Observable<List<ComicSpecialTopic>> getComicSpecialTopic(String page, String channel, String version) {
        return service.getComicSpecialTopic(page,channel,version);
    }

    public Observable<List<ComicCategoryDetail>> getComicCategoryDetail(String tagId,String type, String page, String channel, String version){
        return service.getComicCategoryDetail(tagId,type,page,channel,version);
    }

    public Observable<List<NewestNovel>> getNewestLightNovel(String page,String channel, String version){
        return service.getNewestLightNovel(page,channel,version);
    }

    public Observable<List<NovelCategory>> getNovelCategory(String channel, String version) {
        return service.getNovelCategory(channel,version);
    }

    public Observable<List<NovelRank>> getNovelRank(String type, String tagId, String page, String channel, String version){
        return service.getNovelRank(type,tagId,page,channel,version);
    }

    public Observable<List<NovelCategoryDetail>> getNovelCategoryDetail(String tagId, String scheduleType,String type, String page, String channel, String version){
        return service.getNovelCategoryDetail(tagId,scheduleType,type,page,channel,version);
    }

    public Observable<NewComicWeekly> getNewComicWeekly(String object_id, String channel, String version){
        return service.getNewComicWeekly(object_id,channel,version);
    }

    public Observable<AuthorIntroduce> getAuthorIntroduce(String object_id, String channel, String version){
        return service.getAuthorIntroduce(object_id,channel,version);
    }

}
