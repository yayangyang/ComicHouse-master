package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class ComicReview extends Base {

    /**
     * {
     * "result": 1000,
     * "msg": "\u53d1\u8868\u6210\u529f",
     * "data": {
     * "id": 3512154,
     * "obj_id": 42167,
     * "content": "\u3002\u3002\u3002",
     * "sender_uid": 105290861,
     * "like_amount": 0,
     * "create_time": 1513421799,
     * "to_uid": 0,
     * "to_comment_id": 0,
     * "origin_comment_id": 0,
     * "masterComment": [],
     * "masterCommentNum": 0,
     * "upload_images": "",
     * "insertedId": 3512154,
     * "cover": "http:\/\/images.dmzj.com\/user\/89\/69\/89694a3a838a917649111452de27ee3f.png",
     * "nickname": "qzuser10673657",
     * "avatar_url": "http:\/\/images.dmzj.com\/user\/89\/69\/89694a3a838a917649111452de27ee3f.png",
     * "sex": 1,
     * "at_cover": "http:\/\/images.dmzj.com\/user\/04\/f5\/04f5eba4e53dfe64e3eb97fbb10a577d.png",
     * "at_nickname": "superdog2014",
     * "at_avatar_url": "http:\/\/images.dmzj.com\/user\/04\/f5\/04f5eba4e53dfe64e3eb97fbb10a577d.png"
     * }
     * }
     */

    public String result;
    public String msg;

    public DataBean data;

    public static class DataBean{
        public String id;
        public String obj_id;
        public String content;
        public String sender_uid;
        public String like_amount;
        public String create_time;
        public String to_uid;
        public String to_comment_id;
        public String origin_comment_id;
        public String masterCommentNum;
        public String upload_images;
        public String insertedId;
        public String cover;
        public String nickname;
        public String avatar_url;
        public String sex;
        public String at_cover;
        public String at_nickname;
        public String at_avatar_url;

        public List masterComment;
    }

}
