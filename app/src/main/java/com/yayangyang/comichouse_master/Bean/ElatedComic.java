package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ElatedComic extends Base {

    /**
     * code": 0,
     * "msg": "",
     * "data": {
     * "category_id": 50,
     * "title": "\u731c\u4f60\u559c\u6b22",
     * "sort": 5,
     * "data": [{
     * "id": 16227,
     * "title": "\u63a0\u593a\u8005",
     * "authors": "\u6c34\u65e0\u6708\u3059\u3046",
     * "status": "\u8fde\u8f7d\u4e2d",
     * "cover": "http:\/\/images.dmzj.com\/webpic\/1\/170831lueduozhe.jpg",
     * "num": 19243790
     * }]
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean{

        /**
         * "category_id": 50,
         * "title": "\u731c\u4f60\u559c\u6b22",
         * "sort": 5,
         * "data": [{
         * "id": 16227,
         * "title": "\u63a0\u593a\u8005",
         * "authors": "\u6c34\u65e0\u6708\u3059\u3046",
         * "status": "\u8fde\u8f7d\u4e2d",
         * "cover": "http:\/\/images.dmzj.com\/webpic\/1\/170831lueduozhe.jpg",
         * "num": 19243790
         * }]
         */

        public String category_id;
        public String sort;
        public String title;
        public List<Bean> data;

        public static class Bean{

            /**
             * "id": 16227,
             * "title": "\u63a0\u593a\u8005",
             * "authors": "\u6c34\u65e0\u6708\u3059\u3046",
             * "status": "\u8fde\u8f7d\u4e2d",
             * "cover": "http:\/\/images.dmzj.com\/webpic\/1\/170831lueduozhe.jpg",
             * "num": 19243790
             */

            public String id;
            public String title;
            public String authors;
            public String status;
            public String cover;
            public String num;

        }
    }
}
