package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadingEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicDownLoadingAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

/**
 * Created by Administrator on 2018/3/9.
 */

public class ComicDownLoadingFragment extends BaseFragment {

    private String cover,comicId;

    private ArrayList<ComicChapterDownLoadInfo> mDownLoadInfoList=new ArrayList<>();
    private ArrayList<ComicChapterDownLoadInfo> arrayList;
    private ArrayList<Mission> mMissions;

    ComicDownLoadingAdapter mAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_downloading;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle arguments = getArguments();
        cover = arguments.getString(Constant.COVER);
        comicId = arguments.getString(Constant.COMIC_ID);
        arrayList = (ArrayList) arguments.getSerializable(Constant.DATA_BEANS);

        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
//        ArrayList<ComicDownLoadMission> arrayList1 = new ArrayList<>();
//        arrayList1.add(new ComicDownLoadMission("ww","xx","11"));

        mAdapter = new ComicDownLoadingAdapter(R.layout.item_comic_downloading, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        refresh();
    }

    private void refresh() {
        mDownLoadInfoList.clear();
        List<ComicChapterDownLoadInfo> comicChapterDownLoadInfo = SettingManager.getInstance().getComicChapterDownLoadInfo(comicId);
        LogUtils.e("comicChapterDownLoadInfo:"+comicChapterDownLoadInfo);
        LogUtils.e("arrayList.size:"+arrayList.size());
        if(comicChapterDownLoadInfo!=null&&!comicChapterDownLoadInfo.isEmpty()){
            mDownLoadInfoList.addAll(comicChapterDownLoadInfo);
            for(int i=0;i<arrayList.size();i++){
                for(int j=0;j<comicChapterDownLoadInfo.size();j++){
                    if(arrayList.get(i).getUrl().equals(comicChapterDownLoadInfo.get(j).getUrl())){
                        break;
                    }
                    if(!arrayList.get(i).getUrl().equals(comicChapterDownLoadInfo.get(j).getUrl())
                            &&j==comicChapterDownLoadInfo.size()-1){
                        mDownLoadInfoList.add(arrayList.get(i));
                    }
                }
            }
        }else{
            for(int i=0;i<arrayList.size();i++){
                mDownLoadInfoList.add(arrayList.get(i));
            }
        }

        SettingManager.getInstance().saveComicChapterDownLoadInfo(comicId,mDownLoadInfoList);//保存想要下载但没开始下载的集合

        RxDownload.INSTANCE.getAllMission()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Mission>>() {
                    @Override
                    public void accept(List<Mission> missions) throws Exception {
                        LogUtils.e("accept+++++++++++"+missions.size());
                        ArrayList<Mission> missionArrayList=new ArrayList<>();
                        for(int i=0;i<missions.size();i++){
                            if(missions.get(i).getUrl().contains(comicId)){
                                missionArrayList.add(missions.get(i));
                            }
                        }

                        mMissions=new ArrayList<>(missionArrayList);
                        if(missionArrayList.size()==0){
//                            LogUtils.e("mMissions.size11:"+missionArrayList.size());
                            for(int i=0;i<mDownLoadInfoList.size();i++){
                                mMissions.add(new Mission(mDownLoadInfoList.get(i).getUrl()));
                            }
                        }else{
                            LogUtils.e("mMissions.size22:"+missionArrayList.size());
                            try {
                                for(int i=0;i<mDownLoadInfoList.size();i++){
//                                    LogUtils.e("i:"+i);
                                    for(int j=0;j<missionArrayList.size();j++){
//                                        LogUtils.e("j1:"+j);
//                                        LogUtils.e("j2:"+mDownLoadInfoList.get(i).getUrl());
//                                        LogUtils.e("j3:"+missionArrayList.get(j).getUrl());
                                        if(mDownLoadInfoList.get(i).getUrl().equals(missionArrayList.get(j).getUrl())){
                                            break;
                                        }
                                        if(!mDownLoadInfoList.get(i).getUrl().equals(missionArrayList.get(j).getUrl())
                                                &&j==missionArrayList.size()-1){
                                            mMissions.add(new Mission(mDownLoadInfoList.get(i).getUrl()));
                                        }
                                    }
                                }
                            }catch (Exception e){
                                LogUtils.e("出错:"+e.toString());
                                e.printStackTrace();
                            }
                        }

                        LogUtils.e("accept2222222222"+mMissions.size());
                        ArrayList<ComicDownLoadMission> comicDownLoadMissions = new ArrayList<>();
                        for(int i=0;i<mMissions.size();i++){
//                            LogUtils.e("accept00000"+mMissions.get(i).getUrl());
                            for(int j=0;j<mDownLoadInfoList.size();j++){
                                if(mMissions.get(i).getUrl().equals(mDownLoadInfoList.get(j).getUrl())){
                                    ComicChapterDownLoadInfo info = mDownLoadInfoList.get(j);
                                    String[] split = info.getUrl().split("/");
                                    File file = new File(Constant.PATH_DOWNLOAD + "/" + split[split.length - 1]);
                                    //文件存在或文件大小小于记录里的大小就显示
                                    if(!file.exists()||file.length()<Integer.parseInt(info.getFilesize())){
                                        comicDownLoadMissions.add(new ComicDownLoadMission(info.getUrl(),info.getChapterTitle(),info.getFilesize()));
                                    }
                                    break;
                                }
                            }
                        }

                        LogUtils.e("wwwww");
                        mAdapter.setNewData(comicDownLoadMissions);
//                        RxDownload.INSTANCE.createAll(mMissions).subscribe();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(RefreshDownLoadingEvent event) {
        LogUtils.e("ComicDownLoadingFragment-收到");
        comicId=event.comicId;
        arrayList=event.urlList;

        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
