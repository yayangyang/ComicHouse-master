package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ComicRead extends Base {

    /**
     * {
     * "chapter_id": 67625,
     * "comic_id": 9949,
     * "title": "\u7b2c125\u8bdd",
     * "chapter_order": 2630,
     * "direction": 1,
     * "page_url": ["http:\/\/imgsmall.dmzj.com\/y\/9949\/67625\/0.jpg"],
     * "picnum": 132,
     * "comment_count": 62011
     */

    public String chapter_id;
    public String comic_id;
    public String title;
    public String chapter_order;
    public String direction;
    public String picnum;
    public String comment_count;

    public ArrayList<String> page_url;

}
