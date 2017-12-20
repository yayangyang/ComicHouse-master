package com.yayangyang.comichouse_master.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class AuthorIntroduceAdapter extends BaseQuickAdapter<AuthorIntroduce.DataBean,BaseViewHolder> {

    public AuthorIntroduceAdapter(int id, List<AuthorIntroduce.DataBean> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthorIntroduce.DataBean item) {
        LogUtils.e("AuthorIntroduceAdapter-convert:"+item.cover);
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
//        Glide.with(mContext).load(cookie)
//                .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
//                (mContext,6)).into(view);
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_title,item.name);
    }
}
