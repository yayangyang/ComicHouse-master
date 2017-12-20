package com.yayangyang.comichouse_master.app;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.GlideUtil;

/**
 * Created by Administrator on 2017/12/11.
 */

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                GlideUtil.getDefaultRequestOptions());
    }
}
