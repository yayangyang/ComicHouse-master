package com.yayangyang.comichouse_master.Bean;

/**
 * Created by Administrator on 2017/12/3.
 */

public class NovelCategoryDetailSelectionEvent {

    public String tagId;
    public String scheduleType;
    public String type;

    public NovelCategoryDetailSelectionEvent(String tagId,String scheduleType, String type) {
        this.tagId=tagId;
        this.scheduleType=scheduleType;
        this.type=type;
    }

}
