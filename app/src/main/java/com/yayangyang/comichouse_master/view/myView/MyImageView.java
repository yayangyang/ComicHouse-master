package com.yayangyang.comichouse_master.view.myView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yayangyang.comichouse_master.R;

/**
 * Created by Administrator on 2017/12/14.
 */

public class MyImageView extends FrameLayout {

    private View view;

    public MyImageView(@NonNull Context context) {
        this(context, null);
    }

    public MyImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view=View.inflate(context, R.layout.view_my_imageview,this);
    }

}
