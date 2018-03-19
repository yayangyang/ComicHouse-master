package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

/**
 * Created by Administrator on 2018/3/18.
 */

public class ComicDownLoadInfo extends Base {

    private String title;
    private String cover;
    private String downLoadSize;
    private String totalSize;

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

    public String getDownLoadSize() {
        return downLoadSize;
    }

    public void setDownLoadSize(String downLoadSize) {
        this.downLoadSize = downLoadSize;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String formatString(){
        return downLoadSize+"/"+totalSize;
    }
}
