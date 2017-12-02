package com.yayangyang.comichouse_master.Bean.support;

public class SelectionEvent {

    public int index;
    public int currentComicType;
    public int currentDate;
    public int currentRankType;

    public SelectionEvent(int index,int currentComicType,int currentDate,int currentRankType) {
        this.index=index;
        this.currentComicType = currentComicType;
        this.currentDate = currentDate;
        this.currentRankType = currentRankType;
    }

}
