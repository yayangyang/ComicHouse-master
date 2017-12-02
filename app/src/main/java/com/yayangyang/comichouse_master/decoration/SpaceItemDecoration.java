package com.yayangyang.comichouse_master.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.base.Base;
import com.yayangyang.comichouse_master.utils.LogUtils;

/**
 * Created by Administrator on 2017/11/16.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int size;

    public SpaceItemDecoration(int space,int size) {
        this.space = space;
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
//        outRect.bottom = space;
//        if(parent.getAdapter() instanceof BaseQuickAdapter){
//            BaseQuickAdapter adapter = (BaseQuickAdapter) parent.getAdapter();
//        }
//        LogUtils.e("size:"+size);
//        LogUtils.e("qqqqqqq000"+parent.getChildLayoutPosition(view));
//        LogUtils.e("qqqqqqq111"+parent.getChildLayoutPosition(view) % size);
        if (parent.getChildLayoutPosition(view) % size == 0) {
//            LogUtils.e("qqqqqqq222"+parent.getChildLayoutPosition(view) % size);
            outRect.left = 0;
        }
    }

}