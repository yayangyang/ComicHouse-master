package com.yayangyang.comichouse_master.ui.viewholder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

/**
 * Created by Administrator on 2018/3/14.
 */

public class DownLoadViewHolder extends BaseViewHolder {

    private int position;
    private BaseQuickAdapter mAdapter;

    private Mission mission;
    private Disposable disposable;
    private Status currentStatus;

    public DownLoadViewHolder(View view) {
        super(view);
        getView(R.id.iv_download_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击了");
                dispatchClick();
            }
        });
    }

    private void dispatchClick() {
        if (currentStatus instanceof Normal) {
            LogUtils.e("Normal");
            start();
        } else if (currentStatus instanceof Suspend) {
            LogUtils.e("Suspend");
            start();
        } else if (currentStatus instanceof Failed) {
            LogUtils.e("Failed");
            start();
        } else if (currentStatus instanceof Downloading) {
            LogUtils.e("Downloading");
            stop();
        } else if (currentStatus instanceof Succeed) {
            LogUtils.e("Succeed");
            install();
        } else if (currentStatus instanceof ApkInstallExtension.Installed) {
            LogUtils.e("Installed");
            open();
        }
    }

    public void setData(int position,Mission mission,BaseQuickAdapter adapter) {
//        String url = "http://shouji.360tpcdn.com/170922/9ffde35adefc28d3740d4e16612f078a/com.tencent.tmgp.sgame_22011304.apk";
//        mission.setUrl(url);
        this.position=position;
        this.mission = mission;
        this.mAdapter=adapter;
    }

    public void onAttach() {
        Log.e("DownLoadViewHolder","onAttach");
        disposable = RxDownload.INSTANCE.create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        //在这个方法里面空指针不报异常,即使不抛异常也不报错
                        LogUtils.e("accept:");
                        LogUtils.e("accept:"+status.percent());
                        LogUtils.e("accept:"+status.formatDownloadSize());
                        LogUtils.e("accept:"+status.formatString());
                        LogUtils.e("accept:"+status.getTotalSize());
                        currentStatus = status;
                        setProgress(status);
                        setActionText(status);
                        removeItem(status);
                    }
                });
    }

    private void removeItem(Status status) {
        if(status instanceof Succeed){
            mAdapter.remove(position);
        }
    }

    private void setProgress(Status status) {
        setMax(R.id.progressBar,(int) status.getTotalSize());
        setProgress(R.id.progressBar,(int) status.getDownloadSize());

        LogUtils.e("TotalSize:"+status.getTotalSize());
        if(status.getTotalSize()!=0){
            setText(R.id.tv_progress,status.formatString());
        }else{
            setText(R.id.tv_progress,"0 M/"+
                    FormatUtils.byteToM(Float.parseFloat(((ComicDownLoadMission)mission).getFileToalSize())));
        }
    }

    private void setActionText(Status status) {
        int id=R.drawable.img_btn_play_blue;
        if (status instanceof Normal) {
            id=R.drawable.img_btn_play_blue;
        } else if (status instanceof Suspend) {
            id=R.drawable.img_down_pause;
        } else if (status instanceof Waiting) {
            id=R.drawable.img_down_wait;
        } else if (status instanceof Downloading) {
//            id=R.drawable.img_btn_play_blue;
        } else if (status instanceof Failed) {
//            id=R.drawable.img_btn_play_blue;
        } else if (status instanceof Succeed) {
//            id=R.drawable.img_btn_play_blue;
        } else if (status instanceof ApkInstallExtension.Installing) {
//            id=R.drawable.img_btn_play_blue;
        } else if (status instanceof ApkInstallExtension.Installed) {
//            id=R.drawable.img_btn_play_blue;
        }
        setImageResource(R.id.iv_download_state,id);
    }

    public void onDetach() {
        Log.e("DownLoadViewHolder","onDetach");
        UtilsKt.dispose(disposable);
    }

    private void start() {
        RxDownload.INSTANCE.start(mission).subscribe();
    }

    private void stop() {
        RxDownload.INSTANCE.stop(mission).subscribe();
    }

    private void install() {
        RxDownload.INSTANCE.extension(mission, ApkInstallExtension.class).subscribe();
    }

    private void open() {
        RxDownload.INSTANCE.extension(mission, ApkOpenExtension.class).subscribe();
    }
}