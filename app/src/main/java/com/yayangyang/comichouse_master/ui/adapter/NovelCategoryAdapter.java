package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

public class NovelCategoryAdapter extends BaseQuickAdapter<NovelCategory,BaseViewHolder> {

    public NovelCategoryAdapter(int layoutResId, @Nullable List<NovelCategory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NovelCategory item) {
        LogUtils.e("convert+++++++++++++++");
        ImageView iv_cover=helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        Glide.with(mContext).load(cookie)
                .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                (mContext,6)).into(iv_cover);

        helper.addOnClickListener(R.id.frameLayout);

        helper.setText(R.id.tv_title,item.title);
    }

}
