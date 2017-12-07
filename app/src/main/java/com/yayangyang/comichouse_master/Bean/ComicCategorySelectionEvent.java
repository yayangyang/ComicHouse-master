package com.yayangyang.comichouse_master.Bean;

/**
 * Created by Administrator on 2017/12/3.
 */

public class ComicCategorySelectionEvent {

    public String tagId;
    public String type;

    public ComicCategorySelectionEvent(String tagId, String type) {
        this.tagId=tagId;
        this.type=type;
    }

}
