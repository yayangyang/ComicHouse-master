package com.yayangyang.comichouse_master.ui.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseDownLoadViewHolder;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;
import zlc.season.rxdownload3.extension.ApkInstallExtension;
import zlc.season.rxdownload3.extension.ApkOpenExtension;
import zlc.season.rxdownload3.helper.UtilsKt;

public class DownLoadInfoViewHolder extends BaseDownLoadViewHolder {

    private long totalSize;
    private String comicId;

    private ComicDownLoadInfo info;

    private ArrayList<Mission> missions;
    private Boolean isDownLoadingZ[];
    private Disposable disposable;

    public DownLoadInfoViewHolder(View view) {
        super(view);
    }

    public void setData(ComicDownLoadInfo info) {
        this.totalSize = info.getTotalSize();
        this.comicId=info.getComicId();
        this.missions = info.getMissions();
        this.info=info;
    }

    @Override
    public void onAttach() {
        Log.e("DownLoadInfoViewHolder","onAttach");

        isDownLoadingZ=new Boolean[missions.size()];
        for(int i=0;i<isDownLoadingZ.length;i++){
            isDownLoadingZ[i]=false;
        }
        for(int i=0;i<missions.size();i++){
            int finalI = i;
            disposable = RxDownload.INSTANCE.create(missions.get(i))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Status>() {
                        @Override
                        public void accept(Status status) throws Exception {
                            //在这个方法里面空指针不报异常,即使不抛异常也不报错
                            String downLoadingState="";
                            if(status instanceof Normal||status instanceof Suspend||status instanceof Waiting){
                                isDownLoadingZ[finalI]=false;
                                for(int i=0;i<isDownLoadingZ.length;i++){
                                    if(isDownLoadingZ[i]==true){
                                        downLoadingState="正在下载";
                                        break;
                                    }
                                }
                                downLoadingState="等待下载...";
                            }else if(status instanceof Downloading){
                                isDownLoadingZ[finalI]=true;
                                downLoadingState="正在下载";
                            }

                            LogUtils.e("accept:");
                            LogUtils.e("accept:"+status.percent());
                            LogUtils.e("accept:"+status.formatDownloadSize());
                            LogUtils.e("accept:"+status.formatString());
                            LogUtils.e("accept:"+status.getTotalSize());
                            setActionState(status,downLoadingState);
                        }
                    });
            addDisposable(disposable);
        }
    }

    private void setActionState(Status status,String downLoadState) {
        int id=R.drawable.img_btn_play_blue;
        String downLoadProgress="";

        setVisible(R.id.iv_download_state,true);
        if (downLoadState.equals("等待下载...")) {
            downLoadState="等待下载...";
            id=R.drawable.img_btn_play_blue;
        }else if (downLoadState.equals("正在下载")) {
            LogUtils.e("Downloading");
            downLoadState="正在下载...";
            id=R.drawable.img_down_pause;
        } else if (status instanceof Failed) {
            LogUtils.e("Failed");
        } else if (status instanceof Succeed) {
            LogUtils.e("Succeed");
            String allSucceed = isAllSucceed();
            downLoadProgress=allSucceed;
            String[] split = allSucceed.split("/");
            if(split[0].equals(split[1])){
                setVisible(R.id.iv_download_state,false);
                downLoadState="下载完成";
                info.setDownLoadState(downLoadState);
                setText(R.id.tv_download_state,downLoadState);
            }
            setText(R.id.tv_download_progress,downLoadProgress);
            return;
        }

        info.setDownLoadState(downLoadState);
        setText(R.id.tv_download_state,downLoadState);
        setImageResource(R.id.iv_download_state,id);
    }

    private String isAllSucceed() {
        List<ComicChapterDownLoadInfo> infoList =
                SettingManager.getInstance().getComicChapterDownLoadInfo(comicId);
        long downLoadSize=0,totalSize=0;
        String formatString="";
        if(infoList!=null){
            for(int i=0;i<infoList.size();i++){
                String[] split = infoList.get(i).getUrl().split("/");
                File file = new File(Constant.PATH_DOWNLOAD + "/" + split[split.length - 1]);
                if(file.exists()){
                    downLoadSize+=Long.parseLong(file.length()+"");
                }
                totalSize+=Long.parseLong(infoList.get(i).getFilesize());
                formatString=FormatUtils.byteToM(downLoadSize)+"/"+FormatUtils.byteToM(totalSize);
            }
            info.setDownLoadSize(downLoadSize);
            info.setTotalSize(totalSize);
        }
        return formatString;
    }

    @Override
    public void onDetach() {
        Log.e("DownLoadInfoViewHolder","onDetach");
//        UtilsKt.dispose(disposable);
        mCompositeDisposable.clear();
    }

    private CompositeDisposable mCompositeDisposable;

    protected void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}