package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadInfo;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.MyDownLoadAdapter;
import com.yayangyang.comichouse_master.utils.FileUtils;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onClick(View v) {

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refresh() {
        mDownLoadInfos.clear();
        List<String> stringList = SettingManager.getInstance().getAllComicDownLoadId();
        if(stringList!=null){
            LogUtils.e("stringList.size:"+stringList.size());
            for(int i=0;i<stringList.size();i++){
                LogUtils.e("i:"+i);
                ComicDownLoadInfo info = new ComicDownLoadInfo();
                List<ComicChapterDownLoadInfo> infoList =
                        SettingManager.getInstance().getComicChapterDownLoadInfo(stringList.get(i));
                if(infoList!=null){
                    long downLoadSize=0,totalSize=0;
                    for(int j=0;j<infoList.size();j++){
                        LogUtils.e("j:"+j);
                        if(j==0){
                            info.setTitle(infoList.get(j).getTitle());
                            info.setCover(infoList.get(j).getCover());
                        }
                        String[] split = infoList.get(j).getUrl().split("/");
                        File file = new File(Constant.PATH_DOWNLOAD + "/" + split[split.length - 1]);
                        if(file.exists()){
                            downLoadSize+=Long.parseLong(file.length()+"");
                        }
                        totalSize+=Long.parseLong(infoList.get(j).getFilesize());
                    }
                    info.setDownLoadSize(FormatUtils.byteToM(downLoadSize));
                    info.setTotalSize(FormatUtils.byteToM(totalSize));
                    mDownLoadInfos.add(info);
                }
            }
        }
    }

}
