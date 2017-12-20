package com.yayangyang.comichouse_master.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.GlideUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/11/28.
 */

public class NewComicWeeklyAdapter extends BaseQuickAdapter<NewComicWeekly.DataBean,BaseViewHolder> {

    public NewComicWeeklyAdapter(int id, List<NewComicWeekly.DataBean> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewComicWeekly.DataBean item) {
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_title,item.name);
        helper.setText(R.id.tv_recommend_brief,item.recommend_brief);
        helper.setText(R.id.tv_recommend_reason,item.recommend_reason);
    }
}
