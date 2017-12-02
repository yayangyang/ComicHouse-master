package com.yayangyang.comichouse_master.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yayangyang.comichouse_master.utils.LogUtils;

/**
 * Created by Administrator on 2017/11/12.
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    // 事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;// 不拦截子控件的事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.e("onTouchEvent");
        // 重写此方法,即使取得事件(子view不消费),触摸时什么都不做,从而实现对滑动事件的禁用
        return true;
    }

}