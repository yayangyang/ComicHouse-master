package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AuthorIntroduce extends Base {

    /**
     * {
     * "nickname": "\u8d30\u74f6\u52c9",
     * "description": null,
     * "cover": "http:\/\/img.178.com\/acg1\/201204\/129153842704\/129176578787.jpg",
     * "data": [{
     * "id": 48,
     * "name": "blame",
     * "cover": "http:\/\/images.dmzj.com\/webpic\/8\/blame.jpg",
     * "status": "\u5df2\u5b8c\u7ed3"
     * }]
     */

    public String nickname;
    public String description;
    public String cover;

    public ArrayList<DataBean> data;

    public static class DataBean{
        public String id;
        public String name;
        public String cover;
        public String status;
    }
}
