package com.yayangyang.comichouse_master.Bean;

import java.util.ArrayList;

public class RefreshDownLoadingEvent {

    public String comicId;
    public ArrayList<ComicChapterDownLoadInfo> urlList;

    public RefreshDownLoadingEvent(String comicId, ArrayList<ComicChapterDownLoadInfo> urlList) {
        this.comicId=comicId;
        this.urlList = urlList;
    }

}
