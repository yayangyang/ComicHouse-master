package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadInfo;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseDownLoadAdapter;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.ui.viewholder.DownLoadInfoViewHolder;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class MyDownLoadAdapter extends BaseDownLoadAdapter<ComicDownLoadInfo,DownLoadInfoViewHolder> {

    public MyDownLoadAdapter(int layoutResId, @Nullable List<ComicDownLoadInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(DownLoadInfoViewHolder helper, ComicDownLoadInfo item) {
        LogUtils.e("convert-cover:"+item.getCover());
        helper.setData(item);

        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.getCover(), new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.addOnClickListener(R.id.frameLayout);

        helper.setText(R.id.tv_title,item.getTitle());
        if(item.getDownLoadSize()>=item.getTotalSize()){
            helper.setText(R.id.tv_download_state,"下载完成");
            helper.setImageResource(R.id.iv_download_state,R.drawable.img_down_arr_pause_grey);
        }else{
//            helper.setText(R.id.tv_download_state,"等待下载...");
//            helper.setImageResource(R.id.iv_download_state,R.drawable.img_down_arr_run_blue);
        }
        helper.setText(R.id.tv_download_progress,item.formatString());
    }

}
