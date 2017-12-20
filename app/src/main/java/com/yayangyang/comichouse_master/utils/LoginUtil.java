package com.yayangyang.comichouse_master.utils;

import com.yayangyang.comichouse_master.app.ReaderApplication;
import com.yayangyang.comichouse_master.manager.SettingManager;

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
