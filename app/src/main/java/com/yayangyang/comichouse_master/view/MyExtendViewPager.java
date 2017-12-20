package com.yayangyang.comichouse_master.view;

import android.content.Context;
import android.support.v4.view.ViewPager;

        import android.content.Context;
        import android.support.v4.view.ViewPager;
        import android.util.AttributeSet;
        import android.view.MotionEvent;

import com.yayangyang.comichouse_master.utils.LogUtils;


public class MyExtendViewPager extends ViewPager {
    private boolean scrollable = true;

    public MyExtendViewPager(Context context) {
        super(context);
    }

    public MyExtendViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.scrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.scrollable && super.onInterceptTouchEvent(ev);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public void computeScroll() {
//        LogUtils.e("computeScroll"+getCurrentItem());
        super.computeScroll();
    }

    @Override
    public void scrollTo(int x, int y) {
//        LogUtils.e("x:"+x);
        super.scrollTo(x, y);
    }
}

