package com.yayangyang.comichouse_master.utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ReaderApplication;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.transform.GlideCircleTransform;

/**
 * Created by Administrator on 2017/12/6.
 */

public class LoginUtil {

    public static boolean isLogin(){
        if(ReaderApplication.sLogin!=null
                &&ReaderApplication.sLogin.data!=null
                &&ReaderApplication.sLogin.data.uid!=null){
            return true;
        }else{
            ReaderApplication.sLogin=null;
            SettingManager.getInstance().saveLoginInfo(null);
        }
        return false;
    }

}
