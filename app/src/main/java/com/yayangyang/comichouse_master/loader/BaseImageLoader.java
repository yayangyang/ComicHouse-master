package com.yayangyang.comichouse_master.loader;

import android.content.Context;
import android.widget.ImageView;

import com.yayangyang.comichouse_master.view.myView.MyImageView;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by Administrator on 2017/12/14.
 */

public abstract class BaseImageLoader implements ImageLoaderInterface<MyImageView> {

    @Override
    public MyImageView createImageView(Context context) {
        MyImageView imageView = new MyImageView(context);
        return imageView;
    }
}
