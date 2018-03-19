package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadCompleteEvent;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicDownLoadCompleteAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

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

public class ComicDownLoadCompleteFragment extends BaseFragment
        implements BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener{

    private String comicId;

    private ComicDownLoadCompleteAdapter mAdapter;

    private ArrayList<ComicDownLoadMission> list=new ArrayList<>();

    @BindView(R.id.tv_chapter_size)
    TextView tv_chapter_size;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(canSelect){
            ComicDownLoadMission dataBean = (ComicDownLoadMission) adapter.getData().get(position);
            dataBean.setSelected(!dataBean.isSelected());
            view.setSelected(!view.isSelected());

            setChapterSize(adapter.getData());
        }else{
            //打开阅读界面
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showToast("点击了item");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_download_complete;
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
        comicId = arguments.getString(Constant.COMIC_ID);

        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        mAdapter = new ComicDownLoadCompleteAdapter(R.layout.item_comic_chapter_download, null);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        mRecyclerView.setAdapter(mAdapter);

        refresh();
    }

    private void refresh() {
        LogUtils.e("ComicDownLoadCompleteFragment-refresh");
        list.clear();
        RxDownload.INSTANCE.getAllMission()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Mission>>() {
                    @Override
                    public void accept(List<Mission> missions) throws Exception {
                        LogUtils.e("ComicDownLoadCompleteFragment-accept"+missions.size());
                        //取出此漫画全部开始过下载的数据
                        ArrayList<Mission> missionArrayList=new ArrayList<>();
                        for(int i=0;i<missions.size();i++){
                            if(missions.get(i).getUrl().contains(comicId)){
                                missionArrayList.add(missions.get(i));
                            }
                        }

                        List<ComicChapterDownLoadInfo> infoList
                                = SettingManager.getInstance().getComicChapterDownLoadInfo(comicId);

                        for(int i=0;i<missionArrayList.size();i++){
//                            LogUtils.e("accept00000"+missionArrayList.get(i).getUrl());
                            for(int j=0;j<infoList.size();j++){
                                if(missionArrayList.get(i).getUrl().equals(infoList.get(j).getUrl())){
                                    ComicChapterDownLoadInfo info = infoList.get(j);
                                    String[] split = info.getUrl().split("/");
                                    File file = new File(Constant.PATH_DOWNLOAD + "/" + split[split.length - 1]);
                                    if(file.exists()||file.length()>=Integer.parseInt(info.getFilesize())){
                                        ComicDownLoadMission mission =
                                                new ComicDownLoadMission(info.getUrl(), info.getChapterTitle(), info.getFilesize());
                                        list.add(mission);
                                    }
                                    break;
                                }
                            }
                        }
//                        for(int i=0;i<list.size();i++){
//                            LogUtils.e("list:"+list.get(i).chapter_title);
//                        }
                        tv_chapter_size.setText(list.size()+"个章节");
                        mAdapter.setNewData(list);
                    }
                });
    }

    private boolean canSelect=false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(RefreshDownLoadCompleteEvent event) {
        LogUtils.e("ComicDownLoadCompleteFragment-收到");

        List<ComicDownLoadMission> dataList = mAdapter.getData();
        String text=event.text;
        if(text.equals(Constant.NEW_DATA)){
            refresh();
            canSelect=false;
        }else if(text.equals(Constant.CLEAR_SELECT)){
            for(int i=0;i<dataList.size();i++){
                dataList.get(i).setSelected(false);
            }
            canSelect=false;
            mAdapter.notifyDataSetChanged();
        }else if(text.equals(Constant.CAN_SELECT)){
            canSelect=true;
        }else if(text.equals(Constant.DELETE)){
            ArrayList<ComicDownLoadMission> dataBeans = new ArrayList<>();
            for(int i=0;i<dataList.size();i++){
                if(dataList.get(i).isSelected()){
                    dataBeans.add(dataList.get(i));
//                    RxDownload.INSTANCE.delete(dataBeans.get())
                }
            }
        }else{
            if(text.equals("全选")){
                for(int i=0;i<dataList.size();i++){
                    dataList.get(i).setSelected(true);
                }
            }else if(text.equals("反选")){
                for(int i=0;i<dataList.size();i++){
                    dataList.get(i).setSelected(!dataList.get(i).isSelected());
                }
            }
            mAdapter.notifyDataSetChanged();
            setChapterSize(dataList);
        }
    }

    private void setChapterSize(List<ComicDownLoadMission> dataList) {
        int selectSize=0;
        for(int i=0;i<dataList.size();i++){
            if(dataList.get(i).isSelected()){
                selectSize++;
            }
        }
        EventBus.getDefault().post(new RefreshDownLoadEvent(selectSize+""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
