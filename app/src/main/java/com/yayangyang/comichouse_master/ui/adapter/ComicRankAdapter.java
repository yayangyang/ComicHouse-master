package com.yayangyang.comichouse_master.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class ComicRankAdapter extends BaseQuickAdapter<ComicInfo,BaseViewHolder> {

    public ComicRankAdapter(int id, List<ComicInfo> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicInfo item) {
        LogUtils.e("LinearLayout");
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        Glide.with(mContext).load(cookie)
                .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                (mContext,6)).into(view);

        helper.addOnClickListener(R.id.frameLayout);

        helper.setText(R.id.tv_title,item.title);
        helper.setText(R.id.tv_author,item.authors);
        helper.setText(R.id.tv_type,item.types);
        helper.setText(R.id.tv_date,item.last_updatetime);
        helper.setText(R.id.tv_rank,helper.getLayoutPosition()+1+"");
    }
}
