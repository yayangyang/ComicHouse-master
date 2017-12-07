package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NovelRankAdapter extends BaseQuickAdapter<NovelRank,BaseViewHolder> {

    public NovelRankAdapter(int layoutResId, @Nullable List<NovelRank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NovelRank item) {
        LogUtils.e("NovelRankAdapter-convert");
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        Glide.with(mContext).load(cookie)
                .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                (mContext,6)).into(view);

        helper.addOnClickListener(R.id.frameLayout);

        helper.setText(R.id.tv_title,item.name);
        helper.setText(R.id.tv_author,item.authors);
        String type="";
        for(int i=0;i<item.types.length;i++){
            type+=item.types[i];
        }
        helper.setText(R.id.tv_type,type);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=format.format(new Date(Long.parseLong(item.last_update_time)*1000L));
        helper.setText(R.id.tv_date,dateString);
        helper.setText(R.id.tv_rank,helper.getLayoutPosition()+1+"");
    }

}
