package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class LightNovel extends Base {

    /**
     * "category_id": 57,
     * "sort": 0,
     * "title": "\u8f6e\u756a\u56fe",
     * "data": [{
     * "id": 2550,
     * "obj_id": 2013,
     * "title": "[\u5b8c\u7ed3]\u52c7\u8005\u7684\u5e08\u5085\u5927\u4eba",
     * "cover": "http:\/\/images.dmzj.com\/tuijian\/xiaoshuo\/750-480\/194.jpg",
     * "url": "",
     * "type": 2,
     * "sub_title": "",
     * "status": "\u5df2\u5b8c\u7ed3"
     * }]
     */
    public String category_id;
    public String title;
    public String sort;

    public List<DataBean> data;

    public static class DataBean{
        /**
         * "id": 2550,
         * "obj_id": 2013,
         * "title": "[\u5b8c\u7ed3]\u52c7\u8005\u7684\u5e08\u5085\u5927\u4eba",
         * "cover": "http:\/\/images.dmzj.com\/tuijian\/xiaoshuo\/750-480\/194.jpg",
         * "url": "",
         * "type": 2,
         * "sub_title": "",
         * "status": "\u5df2\u5b8c\u7ed3"
         */
        public String id;
        public String obj_id;
        public String title;
        public String cover;
        public String url;
        public String type;
        public String sub_title;
        public String status;
    }
}
