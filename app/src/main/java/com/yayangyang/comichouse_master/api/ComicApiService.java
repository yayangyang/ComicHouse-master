package com.yayangyang.comichouse_master.api;


import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.Bean.ComicSpecialTopic;
import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ComicApiService {

    /**
     * 获取推荐漫画
     * @param channel
     * @param version
     * @return
     */
    @GET("/v3/recommend.json")
    Observable<List<ComicRecommend>> getComicRecommend(@Query("channel") String channel, @Query("version") String version);

    @Headers("Cache-Control: max-age=0")
    @GET("/recommend/batchUpdate")
    Observable<SubscriptionComic> getSubsciptionComic(@QueryMap Map<String, String> params);

    @Headers("Cache-Control: max-age=0")
    @GET("/recommend/batchUpdate")
    Observable<ElatedComic> getElatedComic(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("http://user.dmzj.com/login/three_confirm")
    Observable<Login> login(@FieldMap Map<String, String> params);

    /**
     * 获取轻小说页
     * @param channel
     * @param version
     * @return
     */
    @GET("/novel/recommend.json")
    Observable<List<LightNovel>> getLightNovel(@Query("channel") String channel,@Query("version") String version);

    /**
     * 获取新闻推荐页header
     * @param channel
     * @param version
     * @return
     */
    @GET("/v3/article/recommend/header.json")
    Observable<NewsRecommendHeader> getNewsRecommendHeader(@Query("channel") String channel, @Query("version") String version);

    /**
     * 获取新闻body
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/v3/article/list/{newsType}/2/{page}.json")
    Observable<List<NewsCommonBody>> getNewsCommonBody(@Path("newsType") String newsType,@Path("page") String page, @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取漫画更新页
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/latest/{type}/{page}.json")
    Observable<List<ComicUpdate>> getComicUpdate(@Path("type") String type,@Path("page") String page,@Query("channel") String channel, @Query("version") String version);


    /**
     * 获取漫画分类页
     * @param channel
     * @param version
     * @return
     */
    @GET("/0/category.json")
    Observable<List<ComicCategory>> getComicCategory(@Query("channel") String channel, @Query("version") String version);

    /**
     * 获取漫画排行页
     * @param comicType
     * @param date
     * @param rankType
     * @param page
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/rank/{comicType}/{date}/{rankType}/{page}.json")
    Observable<List<ComicInfo>> getComicRank(@Path("comicType") String comicType, @Path("date") String date,
                                             @Path("rankType") String rankType, @Path("page") String page,
                                             @Query("channel") String channel, @Query("version") String version);

    @Headers("HasPage: 1")
    @GET("/subject/0/{page}.json")
    Observable<List<ComicSpecialTopic>> getComicSpecialTopic(@Path("page") String page,
                                                     @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取漫画分类详情
     * @param tagId
     * @param type
     * @param page
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/classify/{tagId}/{type}/{page}.json")
    Observable<List<ComicCategoryDetail>> getComicCategoryDetail(@Path("tagId") String tagId,@Path("type") String type,@Path("page") String page,
                                                           @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取最新小说
     * @param page
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/novel/recentUpdate/{page}.json")
    Observable<List<NewestNovel>> getNewestLightNovel(@Path("page") String page,
                                                      @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取小说分类页
     * @param channel
     * @param version
     * @return
     */
    @GET("/1/category.json")
    Observable<List<NovelCategory>> getNovelCategory(@Query("channel") String channel, @Query("version") String version);

    /**
     * 获取小说排行
     * @param tagId
     * @param type
     * @param page
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/novel/rank/{type}/{tagId}/{page}.json")
    Observable<List<NovelRank>> getNovelRank(@Path("type") String type,@Path("tagId") String tagId, @Path("page") String page,
                                                       @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取小说分类详情
     * @param tagId
     * @param scheduleType
     * @param type
     * @param page
     * @param channel
     * @param version
     * @return
     */
    @Headers("HasPage: 1")
    @GET("/novel/{tagId}/{scheduleType}/{type}/{page}.json")
    Observable<List<NovelCategoryDetail>> getNovelCategoryDetail(@Path("tagId") String tagId, @Path("scheduleType") String scheduleType, @Path("type") String type,@Path("page") String page,
                                                       @Query("channel") String channel, @Query("version") String version);

    /**
     * 获取新漫周刊页
     * @param object_id
     * @param channel
     * @param version
     * @return
     */
    @GET("/subject/{object_id}.json")
    Observable<NewComicWeekly> getNewComicWeekly(@Path("object_id") String object_id,
                                                 @Query("channel") String channel, @Query("version") String version);

    @GET("/UCenter/author/{object_id}.json")
    Observable<AuthorIntroduce> getAuthorIntroduce(@Path("object_id") String object_id,
                                                        @Query("channel") String channel, @Query("version") String version);
}