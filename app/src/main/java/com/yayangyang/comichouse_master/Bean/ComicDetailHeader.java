package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ComicDetailHeader extends Base {

    /**
     * {
     * "id": 39695,
     * "islong": 2,
     * "direction": 1,
     * "title": "\u4e94\u7b49\u5206\u7684\u82b1\u5ac1",
     * "is_dmzj": 0,
     * "cover": "http:\/\/images.dmzj.com\/webpic\/5\/wdfdhj12654869l.jpg",
     * "description": "\u8c01\u8bf4\u5973\u4e3b\u89d2\u53ea\u80fd\u6709\u4e00\u4e2a\u7684\uff1f\u521d\u6b21\u89c1\u9762\u7684\u5973\u4e3b\u89d2\u4eec\uff01\u5927\u5bb6\u90fd\u5404\u6709\u5343\u79cb\u53ef\u7231\u975e\u5e38\uff01",
     * "last_updatetime": 1513128012,
     * "copyright": 0,
     * "first_letter": "w",
     * "hot_num": 26042003,
     * "hit_num": 45695170,
     * "uid": null,
     * "types": [{
     * "tag_id": 8,
     * "tag_name": "\u7231\u60c5"
     * }],
     * "status": [{
     * "tag_id": 2309,
     * "tag_name": "\u8fde\u8f7d\u4e2d"
     * }],
     * "authors": [{
     * "tag_id": 7919,
     * "tag_name": "\u6625\u573a\u306d\u304e"
     * }],
     * "subscribe_num": 130999,
     * "chapters": [{
     * "title": "\u8fde\u8f7d",
     * "data": [{
     * "chapter_id": 72045,
     * "chapter_title": "17\u8bdd",
     * "updatetime": 1513128014,
     * "filesize": 5503334,
     * "chapter_order": 170
     * }],
     * "comment": {
     * "comment_count": 9543,
     * "latest_comment": [{
     * "comment_id": 3499115,
     * "uintent": "\u8bdd\u8bf4\u7537\u4e3b\u7684\u753b\u98ce\u592a\u9634\u6c89\u4e86\u5427\u2026\u2026",
     * "crd": 104770154,
     * "coeatetime": 1513232416,
     * "nickname": "qzuser73504517",
     * "avatar": "http:\/\/images.dmzj.com\/user\/94\/af\/94af8b3d0cde6d781f68b30a0f27962b.png"
     * }]
     * }
     * }
     */

    public String id;
    public String islong;
    public String direction;
    public String title;
    public String is_dmzj;
    public String cover;
    public String description;
    public String last_updatetime;
    public String copyright;
    public String first_letter;
    public String hot_num;
    public String hit_num;
    public String uid;
    public String subscribe_num;
    public ArrayList<TagBean> types;
    public ArrayList<TagBean> status;
    public ArrayList<TagBean> authors;
    public ArrayList<ChaptersBean> chapters;
    public Comment comment;

    public static class TagBean implements Serializable{
        public String tag_id;
        public String tag_name;
    }

    public static class ChaptersBean implements Serializable{
        public String title;
        public ArrayList<DataBean> data;

        public static class DataBean implements Serializable{
            public String chapter_id;
            public String chapter_title;
            public String updatetime;
            public String filesize;
            public String chapter_order;

            private boolean isSelected=false;//用于判断数据是否选中(自己增加)

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }
        }
    }

    public static class Comment implements Serializable{
        public String comment_count;
        public ArrayList<ChaptersBean> latest_comment;

        public static class CommentBean implements Serializable{
            public String comment_id;
            public String uid;
            public String content;
            public String createtime;
            public String nickname;
            public String avatar;
        }
    }


}
