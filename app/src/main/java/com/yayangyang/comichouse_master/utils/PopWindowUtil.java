package com.yayangyang.comichouse_master.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.view.CustomPopWindow;

/**
 * Created by Administrator on 2018/3/7.
 */

public class PopWindowUtil {

    public static CustomPopWindow createPopupWindow(Context mContext,View contentView) {
        return new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //取消遮罩()
//                        WindowManager.LayoutParams lp =getWindow().getAttributes();
//                        lp.alpha = 0.5f;
//                        getWindow().setAttributes(lp);
                    }
                })
                .create();//创建PopupWindow
    }

}
