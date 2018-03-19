package com.yayangyang.comichouse_master.Bean;


import org.jetbrains.annotations.NotNull;

import zlc.season.rxdownload3.core.Mission;

public class ComicDownLoadMission extends Mission {

    private boolean isSelected=false;//用于判断数据是否选中(自己增加)

    private String chapterTitle = "";
    private String fileToalSize = "";

    public ComicDownLoadMission(@NotNull String url, String chapterTitle,String fileToalSize) {
        super(url);
        this.chapterTitle = chapterTitle;
        this.fileToalSize = fileToalSize;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public String getFileToalSize() {
        return fileToalSize;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
