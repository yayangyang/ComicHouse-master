package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ComicDetailBody extends Base {

    /**
     * [{
     * "id": 3494541,
     * "is_passed": 1,
     * "top_status": 0,
     * "is_goods": 0,
     * "upload_images": "",
     * "obj_id": 20926,
     * "content": "\u6211\u597d\u50cf\u6709\uff1f",
     * "sender_uid": 102178355,
     * "like_amount": "0",
     * "create_time": 1513165948,
     * "to_uid": 100262587,
     * "to_comment_id": 3484986,
     * "origin_comment_id": 3484986,
     * "reply_amount": 0,
     * "hot_comment_amount": 0,
     * "cover": "http:\/\/images.dmzj.com\/user\/61\/fc\/61fc069e7a4ff5d28eae00b637d7c32f.png",
     * "nickname": "\u9ed1\u8272\u9e8b\u9e7f",
     * "sex": 1,
     * "masterComment": [{
     * "id": 3484986,
     * "is_passed": 1,
     * "top_status": 0,
     * "is_goods": 0,
     * "upload_images": "",
     * "obj_id": 20926,
     * "content": "\u6709\u8c01\u6536\u85cf\u4e86\u72ac\u6577\u5c4b\u554a\uff1f",
     * "sender_uid": 100262587,
     * "like_amount": "0",
     * "create_time": 1513040717,
     * "to_uid": 0,
     * "to_comment_id": 0,
     * "origin_comment_id": 0,
     * "reply_amount": 1,
     * "cover": "http:\/\/images.dmzj.com\/user\/99\/10\/991080146ba644d2eed3f1fefdf31e59.png",
     * "nickname": "\u5343\u5343\u5343Sama",
     * "hot_comment_amount": 1,
     * "sex": 1
     * }],
     * "masterCommentNum": 2
     * },
     * 8731]": 1
     * }]
     */

    public String id;
    public String is_passed;
    public String top_status;
    public String is_goods;
    public String upload_images;
    public String obj_id;
    public String content;
    public String sender_uid;
    public String like_amount;
    public String create_time;
    public String to_uid;
    public String to_comment_id;
    public String origin_comment_id;
    public String reply_amount;
    public String hot_comment_amount;
    public String cover;
    public String nickname;
    public String sex;
    public String masterCommentNum;

    public List<DataBean> masterComment;

    public static class DataBean implements Serializable{
        public String content;
        public String cover;
        public String create_time;
        public String hot_comment_amount;
        public String id;
        public String is_goods;
        public String is_passed;
        public String like_amount;
        public String nickname;
        public String obj_id;
        public String orgin_comment_id;
        public String reply_amount;
        public String sender_uid;
        public String sex;
        public String to_comment_id;
        public String to_uid;
        public String top_status;
        public String upload_images;
    }
}
