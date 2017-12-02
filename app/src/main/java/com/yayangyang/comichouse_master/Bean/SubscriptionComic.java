package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SubscriptionComic extends Base {

    /**
     * "code": 0,
     * "msg": "",
     * "data": {
     * "category_id": 49,
     * "title": "\u6211\u7684\u8ba2\u9605",
     * "sort": 2,
     * "data": [{
     * "uid": 105290861,
     * "id": 40393,
     * "title": "K.O.I \u5076\u50cf\u4e4b\u738b",
     * "authors": "\u82e5\u6728\u6c11\u559c",
     * "cover": "http:\/\/images.dmzj.com\/webpic\/19\/KOIouxiangzhiwang.jpg",
     * "status": "\u8fde\u8f7d\u4e2d"
     * }]
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean{

        /**
         * "category_id": 49,
         * "title": "\u6211\u7684\u8ba2\u9605",
         * "sort": 2,
         * "data": [{
         * "uid": 105290861,
         * "id": 40393,
         * "title": "K.O.I \u5076\u50cf\u4e4b\u738b",
         * "authors": "\u82e5\u6728\u6c11\u559c",
         * "cover": "http:\/\/images.dmzj.com\/webpic\/19\/KOIouxiangzhiwang.jpg",
         * "status": "\u8fde\u8f7d\u4e2d"
         * }]
         */

        public String category_id;
        public String sort;
        public String title;
        public List<Bean> data;

        public static class Bean{

            /**
             * "uid": 105290861,
             * "id": 40393,
             * "title": "K.O.I \u5076\u50cf\u4e4b\u738b",
             * "authors": "\u82e5\u6728\u6c11\u559c",
             * "cover": "http:\/\/images.dmzj.com\/webpic\/19\/KOIouxiangzhiwang.jpg",
             * "status": "\u8fde\u8f7d\u4e2d"
             */

            public String uid;
            public String id;
            public String title;
            public String authors;
            public String cover;
            public String status;

        }
    }
}
