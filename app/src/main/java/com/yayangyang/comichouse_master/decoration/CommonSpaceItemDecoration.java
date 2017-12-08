package com.yayangyang.comichouse_master.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/11/16.
 */

public class CommonSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CommonSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(space,space,space,space);
    }

}