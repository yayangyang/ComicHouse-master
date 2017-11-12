package com.yayangyang.comichouse_master.utils;

import android.view.View;
import android.view.ViewGroup;


public class ViewUtils {
    public static void setEmptyViewLayoutParams(int width,int height,View view){
        if(view!=null){
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width=width;
            layoutParams.height=height;
            view.setLayoutParams(layoutParams);
        }
    }
}
