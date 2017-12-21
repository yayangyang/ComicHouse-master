package com.yayangyang.comichouse_master.utils;

import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.manager.SettingManager;

/**
 * Created by Administrator on 2017/12/6.
 */

public class LoginUtil {

    public static boolean isLogin(){
        if(ComicApplication.sLogin!=null
                && ComicApplication.sLogin.data!=null
                && ComicApplication.sLogin.data.uid!=null){
            return true;
        }else{
            ComicApplication.sLogin=null;
            SettingManager.getInstance().saveLoginInfo(null);
        }
        return false;
    }

}
