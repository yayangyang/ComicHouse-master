package com.yayangyang.comichouse_master.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.view.myView.MyImageView;

public class GlideImageLoader extends BaseImageLoader {
    @Override
    public void displayImage(Context context, Object path, MyImageView imageView) {
        LogUtils.e("path:"+path);
//        Glide.with(context).load(R.drawable.ab_back).into(imageView);

        GlideUrl cookie = new GlideUrl((String)path, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        //使用同一个对象才不会引起内存泄漏
//        RoundedCornersTransformation transformation =
//                (RoundedCornersTransformation) GlideUtil.getCommonTransformation();
//        Glide.with(context).load(cookie)
//                .apply(new RequestOptions())
//                .into(imageView);
        GlideApp.with(context).load(cookie)
                .into((ImageView) imageView.findViewById(R.id.iv_cover));
    }
}