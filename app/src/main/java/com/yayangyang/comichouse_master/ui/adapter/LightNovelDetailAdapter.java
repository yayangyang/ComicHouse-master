package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/11/13.
 */

public class LightNovelDetailAdapter extends BaseQuickAdapter<LightNovel.DataBean,BaseViewHolder> {
    public LightNovelDetailAdapter(int layoutResId, @Nullable List<LightNovel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final LightNovel.DataBean item) {
        ImageView view =helper.getView(R.id.iv_cover);

        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);
        LogUtils.e("哦哦哦"+item.cover);
        helper.addOnClickListener(R.id.frameLayout);
        helper.setText(R.id.tv_title,item.title);
        if(!item.title.equals(item.sub_title)){
            helper.setText(R.id.tv_author,item.sub_title);
        }
    }
}
