package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.SearchInfo;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class SearchAdapter extends BaseQuickAdapter<SearchInfo,BaseViewHolder> {

    public SearchAdapter(int layoutResId, @Nullable List<SearchInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo item) {
        LogUtils.e("SearchAdapter-convert:"+item.cover);
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());

        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_title,item.title);
        helper.setText(R.id.tv_author,item.authors);
        helper.setText(R.id.tv_type,item.types);
        helper.setText(R.id.tv_last_update_chapter_name,item.last_name);

        helper.addOnClickListener(R.id.frameLayout);
    }
}
