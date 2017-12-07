package com.yayangyang.comichouse_master.Bean;

public class RefreshRankEvent {

    public int index;
    public int currentComicType;
    public int currentDate;
    public int currentRankType;

    public RefreshRankEvent(int index, int currentComicType, int currentDate, int currentRankType) {
        this.index=index;
        this.currentComicType = currentComicType;
        this.currentDate = currentDate;
        this.currentRankType = currentRankType;
    }

}
