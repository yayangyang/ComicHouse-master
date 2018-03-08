package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

/**
 * Created by Administrator on 2018/3/8.
 */

public class BannerBean extends Base {

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

    public String object_url;//后缀为html

}
