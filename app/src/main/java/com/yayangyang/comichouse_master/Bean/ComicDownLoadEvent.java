package com.yayangyang.comichouse_master.Bean;

import java.util.ArrayList;

import zlc.season.rxdownload3.core.Mission;

/**
 * Created by Administrator on 2017/12/3.
 */

public class ComicDownLoadEvent {

    public ArrayList<Mission> mArrayList;
    public String type;

    public ComicDownLoadEvent(ArrayList arrayList) {
        mArrayList=arrayList;
    }

}
