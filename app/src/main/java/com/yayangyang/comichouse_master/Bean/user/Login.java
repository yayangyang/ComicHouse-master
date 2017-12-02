package com.yayangyang.comichouse_master.Bean.user;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.io.Serializable;

public class Login extends Base {

    /**
     * "result": 1,
     * "msg": "OK",
     * "data": {
     * "uid": "105290861",
     * "nickname": "qzuser10673657",
     * "pic": "89694a3a838a917649111452de27ee3f",
     * "photo": "http:\/\/images.dmzj.com\/user\/89\/69\/89694a3a838a917649111452de27ee3f.png",
     * "dmzj_token": "1f751c3f8c325126d5f017128c4195b9"
     * }
     */

    public String result;
    public String msg;
    public DataBean data;

    public static class DataBean{
        public String uid;
        public String nickname;
        public String pic;
        public String photo;
        public String dmzj_token;
    }

}
