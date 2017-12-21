package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewestNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/12/4.
 */

public class NewestNovelAdapter extends BaseQuickAdapter<NewestNovel,BaseViewHolder> {

    public NewestNovelAdapter(int layoutResId, @Nullable List<NewestNovel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewestNovel item) {
        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_title,item.name);
        helper.setText(R.id.tv_author,item.authors);
        String type="";
        for(int i=0;i<item.types.length;i++){
            type+=item.types[i];
        }
        helper.setText(R.id.tv_type,type);
        String dateString = FormatUtils.getStringByTimeStamp("yyyy-MM-dd", item.last_update_time);
        helper.setText(R.id.tv_date,dateString);
        helper.setText(R.id.bt_last_update_chapter_name,item.last_update_chapter_name);
    }
}
