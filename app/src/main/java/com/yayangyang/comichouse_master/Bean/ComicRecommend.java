package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ComicRecommend extends Base {

    /**
     * "category_id": 46,
     * "title": "\u5927\u56fe\u63a8\u8350",
     * "sort": 1,
     * "data": [{
     * "cover": "http:\/\/images.dmzj.com\/tuijian\/750_480\/171110wudengfentj2.jpg",
     * "title": "\u4e94\u7b49\u5206\u7684\u72d7\u7cae\u00b7\u53cc\u5341\u4e00\u5feb\u4e50",
     * "sub_title": "\u4e94\u7b49\u5206\u7684\u72d7\u7cae\u00b7\u53cc\u5341\u4e00\u5feb\u4e50",
     * "type": 1,
     * "url": "",
     * "obj_id": 39695,
     * "status": "\u8fde\u8f7d\u4e2d"
     * }]
     */
    public String category_id;
    public String title;
    public String sort;

    public List<DataBean> data;

    public static class DataBean{
        /**
         * "cover": "http:\/\/images.dmzj.com\/tuijian\/750_480\/171110wudengfentj2.jpg",
         * "title": "\u4e94\u7b49\u5206\u7684\u72d7\u7cae\u00b7\u53cc\u5341\u4e00\u5feb\u4e50",
         * "sub_title": "\u4e94\u7b49\u5206\u7684\u72d7\u7cae\u00b7\u53cc\u5341\u4e00\u5feb\u4e50",
         * "type": 1,
         * "url": "",
         * "obj_id": 39695,
         * "status": "\u8fde\u8f7d\u4e2d"
         */
        public String cover;
        public String title;
        public String sub_title;
        public String type;//版块类型
        public String url;//无
        public String obj_id;//无
        public String status;

        public String uid;//便于订阅版块的类实例转化为此类的对象

        public String num;//便于喜欢版块的类实例转化为此类的对象

        public String id;//便于订阅与喜欢版块的类实例转化为此类的对象
    }
}
