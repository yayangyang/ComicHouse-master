package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;
import com.yayangyang.comichouse_master.utils.FileUtils;
import com.yayangyang.comichouse_master.utils.FormatUtils;

import java.util.ArrayList;

import zlc.season.rxdownload3.core.Mission;

/**
 * Created by Administrator on 2018/3/18.
 */

public class ComicDownLoadInfo extends Base {

    private String comicId;
    private String title;
    private String cover;
    private long downLoadSize;
    private long totalSize;

    private String downLoadState;

    public String getDownLoadState() {
        return downLoadState;
    }

    public void setDownLoadState(String downLoadState) {
        this.downLoadState = downLoadState;
    }

    private ArrayList<Mission> mMissions;

    public ArrayList<Mission> getMissions() {
        return mMissions;
    }

    public void setMissions(ArrayList<Mission> missions) {
        mMissions = missions;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getDownLoadSize() {
        return downLoadSize;
    }

    public void setDownLoadSize(long downLoadSize) {
        this.downLoadSize = downLoadSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String formatString(){
        return FormatUtils.byteToM(downLoadSize)+"/"+FormatUtils.byteToM(totalSize);
    }
}
