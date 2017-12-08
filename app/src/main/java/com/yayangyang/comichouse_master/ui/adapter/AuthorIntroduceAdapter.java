package com.yayangyang.comichouse_master.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.List;

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
        Glide.with(mContext).load(cookie)
                .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                (mContext,6)).into(view);

        helper.setText(R.id.tv_title,item.name);
    }
}
