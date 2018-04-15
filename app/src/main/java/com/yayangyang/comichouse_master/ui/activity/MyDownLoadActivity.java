package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.RefreshMyDownLoadEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.MyDownLoadAdapter;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

/**
 * Created by Administrator on 2018/3/17.
 */

public class MyDownLoadActivity extends BaseActivity implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{

    private ArrayList<ComicDownLoadInfo> mDownLoadInfos=new ArrayList<>();

    private MyDownLoadAdapter mAdapter;

    @BindView(R.id.tv_manage)
    TextView tv_manage;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_all_pause)
    TextView tv_all_pause;
    @BindView(R.id.tv_all_start)
    TextView tv_all_start;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyDownLoadActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ComicDownLoadInfo> data = adapter.getData();
        String comicId = data.get(position).getComicId();
        String title = data.get(position).getTitle();
        ComicDetailActivity.startActivity(this,comicId,title,true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<ComicDownLoadInfo> data = adapter.getData();
        String comicId = data.get(position).getComicId();
        String title = data.get(position).getTitle();
        String cover = data.get(position).getCover();
        List<ComicDetailHeader.ChaptersBean.DataBean> comicChapterInfo = SettingManager.getInstance().getComicChapterInfo(comicId);
        ComicDownLoadActivity.startActivity(this,title,cover,comicId,null,comicChapterInfo);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_all_pause){
            LogUtils.e("停止全部1");
            RxDownload.INSTANCE.stopAll().subscribe();
            LogUtils.e("停止全部2");
        }else if(v.getId()==R.id.tv_all_start){
            LogUtils.e("开始全部1");
            RxDownload.INSTANCE.startAll().subscribe();
            LogUtils.e("开始全部2");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_download;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        refresh();
    }

    @Override
    public void configViews() {
        mAdapter = new MyDownLoadAdapter(R.layout.item_my_download, mDownLoadInfos);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        tv_all_pause.setOnClickListener(this);
        tv_all_start.setOnClickListener(this);
    }

    private void refresh() {
        mDownLoadInfos.clear();
        List<String> stringList = SettingManager.getInstance().getAllComicDownLoadId();
        if(stringList!=null){
            LogUtils.e("stringList.size:"+stringList.size());
            for(int i=0;i<stringList.size();i++){
                LogUtils.e("i:"+i);
                List<ComicChapterDownLoadInfo> infoList =
                        SettingManager.getInstance().getComicChapterDownLoadInfo(stringList.get(i));
                ComicDownLoadInfo info=null;
                if(infoList!=null){
                    long downLoadSize=0,totalSize=0;
                    ArrayList<Mission> missions = new ArrayList<>();
                    for(int j=0;j<infoList.size();j++){
                        LogUtils.e("j:"+j);
                        if(j==0){
                            info=new ComicDownLoadInfo();
                            info.setTitle(infoList.get(j).getTitle());
                            info.setCover(infoList.get(j).getCover());
                        }
                        String[] split = infoList.get(j).getUrl().split("/");
                        File file = new File(Constant.PATH_DOWNLOAD + "/" + split[split.length - 1]);
                        if(file.exists()){
                            downLoadSize+=Long.parseLong(file.length()+"");
                        }
                        totalSize+=Long.parseLong(infoList.get(j).getFilesize());
                        split = infoList.get(j).getUrl().split("/");
                        info.setComicId(split[split.length-2]);
                        missions.add(new Mission(infoList.get(j).getUrl()));
                        LogUtils.e(infoList.get(j).getUrl());
                    }
                    info.setDownLoadSize(downLoadSize);
                    info.setTotalSize(totalSize);
                    info.setMissions(missions);
                    mDownLoadInfos.add(info);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(RefreshMyDownLoadEvent event) {
        LogUtils.e("MyDownLoadActivity-收到");

        String comicId = event.comicId;
        int position=0;
        List<ComicDownLoadInfo> data = mAdapter.getData();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getComicId().equals(comicId)){
                position=i+mAdapter.getHeaderLayoutCount();
                break;
            }
        }
//        mAdapter.getViewByPosition(position,);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
