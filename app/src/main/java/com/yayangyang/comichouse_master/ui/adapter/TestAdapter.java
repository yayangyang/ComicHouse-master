package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/12/8.
 */

public class TestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_cover_normal=helper.getView(R.id.iv_cover_normal);

        GlideUrl cookie = new GlideUrl(item, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        RoundedCornersTransformation transformation =
                (RoundedCornersTransformation) GlideUtil.getCommonTransformation();
        Glide.with(mContext).load(cookie)
                .apply(RequestOptions.bitmapTransform(transformation))
                .into(iv_cover_normal);
        LogUtils.e("哦哦哦"+item);
    }
}
