package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

/**
 * Created by Administrator on 2018/3/6.
 */

public class NewsBean extends Base {

    /**
     * {
     * "code": 0,
     * "msg": "ok",
     * "data": {
     * "comment_amount": "0",
     * "mood_amount": 4,
     * "row_pic_url": "https:\/\/images.dmzj.com\/news\/article\/13474\/row_5a9e633823bdb.jpg",
     * "title": "JUMP\u4e3e\u529e\u65b0\u4f5c\u53d1\u8868\u4f1a\uff01\u5cb8\u672c\u9f50\u53f2\u3001\u4e45\u4fdd\u5e26\u4eba\u7b49\u90fd\u5c06\u51fa\u65b0\u4f5c"
     * }
     * }
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean{
        public String comment_amount;
        public String mood_amount;
        public String row_pic_url;
        public String title;
    }

}
