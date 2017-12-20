package com.yayangyang.comichouse_master.utils;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.app.ReaderApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 没有list指gson解析得到的类中有无list或返回的数据本身就不是list
 * 加载更多的RecyclerView:loadMoreRcRxCreateDiskObservable+rxCacheListHelper//一般RecyclerView基本取得数据都有list
 * 没有加载更多的RecyclerView:rxCreateDiskObservable+rxCacheListHelper//一般RecyclerView基本取得数据都有list
 * 不是RecyclerView且取得数据有list:rxCreateDiskObservable+rxCacheListHelper
 * 不是RecyclerView且取得数据没有list:rxCreateDiskObservable+rxCacheBeanHelper
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable loadMoreRcRxCreateDiskObservable(final String key, final int start, final int size, final Class<T> clazz) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                threadInfo("本地处理");
                LogUtils.e("get data from disk: key==" + key);
                String json = ACache.get(ReaderApplication.getsInstance()).getAsString(key);
                LogUtils.e("get data from disk finish , json==" + json);

                //对于设置可以加载更多的RecyclerView来说,刷新时缓存小于10的时候不显示,其他的不会影响需要显示
                //默认设置数据给非加载更多的RecyclerView请使用rxCreateDiskObservable
                if (!TextUtils.isEmpty(json)) {
                    try{
                        String className =clazz.getSimpleName();
                        Log.e("跟类型",className);
                        if(className.equalsIgnoreCase("List")||
                                className.equalsIgnoreCase("ArrayList")){
                            //即使json转换失败还是会走一遍网络
                            List<T> list =getObjectList(json, clazz);;

                            if(start!=0||list.size()>=size){
                                e.onNext(json);
                            }
                        }else{
                            //即使json转换失败还是会走一遍网络(单个类解析还没实现)
                            T t = new Gson().fromJson(json, clazz);

                            Field[] fields = clazz.getFields();
                            for (Field field : fields) {
                                className = field.getType().getSimpleName();
                                Log.e("className01",className);
                                // 得到属性值
                                if (className.equalsIgnoreCase("List")) {
                                    List list = (List) field.get(t);
                                    //这个判断适合类中只有一个list,而且是关键数据
                                    if(start!=0||list.size()>=size){//刷新时缓存小于10的时候不显示(10按照需求定义,需保证10个item满屏或超出)
                                        e.onNext(json);
                                        break;
                                    }
                                }
                            }
                            e.onNext(json);
                        }
                    }catch (Exception ee){
                        LogUtils.e("Gson转换失败");
                        ee.printStackTrace();
                    }
                }
                LogUtils.e("运行到了");
                e.onComplete();
            }
        }).map(new Function<String,List<T>>() {
            @Override
            public List<T> apply(String s) throws Exception {
                LogUtils.e("callwwwwwwwww");
//                return new Gson().fromJson(s, clazz);
                return getObjectList(s, clazz);
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> rxCacheListHelper(final String key) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                threadInfo("网络处理0000");
                return upstream
                        .subscribeOn(Schedulers.io())//指定doOnNext执行线程是新线程
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(final T data) throws Exception {
                                threadInfo("网络处理11111");
                                Schedulers.io().createWorker().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        threadInfo("网络处理");
                                        LogUtils.e("get data from network finish ,start cache...");
                                        if (data == null)
                                            return;
                                        //通过反射获取List,再判空决定是否缓存
                                        Class clazz = data.getClass();
                                        LogUtils.e("data类名:"+clazz.getName());
                                        if(clazz.getSimpleName().equalsIgnoreCase("List")||
                                                clazz.getSimpleName().equalsIgnoreCase("ArrayList")){
                                            List list=(List) data;
                                            if(!list.isEmpty()){
                                                try {
                                                    ACache.get(ReaderApplication.getsInstance())
                                                            .put(key, new Gson().toJson(data, clazz));
                                                    LogUtils.e("cache finish0000000"+key);
                                                }catch (Exception e){
                                                    LogUtils.e("报错了");
                                                    e.printStackTrace();
                                                }
                                            }
                                        }else{
                                            Field[] fields = clazz.getFields();
                                            for (Field field : fields) {
                                                String className = field.getType().getSimpleName();
                                                Log.e("className",className);
                                                // 得到属性值
                                                //这里当类里有List才缓存(自己根据实际情况确定缓存类是否符合list为空就不缓存,如果是,那list是关键数据)
                                                //只有list才缓存可能是为了优化,避免缓存不必要的数据(但重要数据为空直接不缓存)
                                                if (className.equalsIgnoreCase("List")||
                                                        clazz.getSimpleName().equalsIgnoreCase("ArrayList")) {
                                                    try {
                                                        List list = (List) field.get(data);
                                                        LogUtils.e("list==" + list);
                                                        if (list != null && !list.isEmpty()) {
                                                            ACache.get(ReaderApplication.getsInstance())
                                                                    .put(key,new Gson().toJson(data, clazz));
                                                            LogUtils.e("cache finish");
                                                            //如果缓存成功可以跳出防止类中有多个list多次缓存(自己觉得)
                                                            //类中只有一个list也可以不跳出
                                                        }
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 适用于不可以加载更多的RecyclerView,或者其他
     * (写第二个方法原因是使用brvah若有加载更多功能会默认加载更多,这时因为线程问题可能会造成数据错乱)
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Observable rxCreateDiskObservable(final String key, final Class<T> clazz) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                threadInfo("本地处理");
                String json = ACache.get(ReaderApplication.getsInstance()).getAsString(key);

                if (!TextUtils.isEmpty(json)) {
                    try{
//                        SubsectionLogUtil.e("ww",json);

                        List<ComicRecommend> list = (List<ComicRecommend>) getObjectList(json, clazz);
                        ComicRecommend recommend = list.get(list.size()-1);
                        LogUtils.e(recommend.data.get(0).cover+"wwwwwww");

                        e.onNext(json);
                    }catch (Exception ee){
                        LogUtils.e("Gson转换失败"+ee.toString());
                        ee.printStackTrace();
                    }
                }else{
                    LogUtils.e("json为空"+json);
                }
                LogUtils.e("运行到了");
                e.onComplete();
            }
        }).map(new Function<String,List<T>>() {
            @Override
            public List<T> apply(String s) throws Exception {
                LogUtils.e("callwwwwwwwww");
//                return new Gson().fromJson(s, clazz);
                return getObjectList(s, clazz);
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> rxCacheBeanHelper(final String key) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())//指定doOnNext执行线程是新线程
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(final T data) throws Exception {
                                Schedulers.io().createWorker().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtils.e("get data from network finish ,start cache...");
                                        ACache.get(ReaderApplication.getsInstance())
                                                .put(key, new Gson().toJson(data, data.getClass()));
                                        LogUtils.e("cache finish");
                                    }
                                });
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> List<T> getObjectList(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public static <T> List<T> StringToList(String jsonString, Class<T[]> type) {
//        T[] list  = new Gson().fromJson(jsonString, type);
//        return Arrays.asList(list);
//    }

    //打印当前线程的名称
    public static void threadInfo(String caller) {
        LogUtils.e(caller + " => " + Thread.currentThread().getName());
    }
}
