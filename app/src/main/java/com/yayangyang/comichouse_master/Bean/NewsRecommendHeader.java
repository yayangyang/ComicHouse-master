package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class NewsRecommendHeader extends Base {
    /**
     * code": 0,
     * "msg": "\u6210\u529f",
     * "data": [{
     * "id": 1419,
     * "title": "P\u7ad9\u7f8e\u56fe\u63a8\u8350\u2014\u2014\u7ef7\u5e26\u7eb1\u5e03\u7279\u8f91\uff08\u4e8c\uff09",
     * "pic_url": "http:\/\/images.dmzj.com\/news\/recommend\/15115173711593.jpg",
     * "object_id": 12441,
     * "object_url": "http:\/\/v2.api.dmzj.com\/v3\/article\/show\/12441.html"
     * }]
     */

    public String code;
    public String msg;

    public List<DataBean> data;

    public static class DataBean{
        /**
         * "id": 1419,
         * "title": "P\u7ad9\u7f8e\u56fe\u63a8\u8350\u2014\u2014\u7ef7\u5e26\u7eb1\u5e03\u7279\u8f91\uff08\u4e8c\uff09",
         * "pic_url": "http:\/\/images.dmzj.com\/news\/recommend\/15115173711593.jpg",
         * "object_id": 12441,
         * "object_url": "http:\/\/v2.api.dmzj.com\/v3\/article\/show\/12441.html"
         */

        public String id;
        public String title;
        public String pic_url;
        public String object_id;
        public String object_url;
    }
}
