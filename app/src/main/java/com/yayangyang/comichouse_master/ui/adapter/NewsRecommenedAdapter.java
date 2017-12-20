package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.MyTransform;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsRecommenedAdapter extends BaseQuickAdapter<NewsCommonBody,BaseViewHolder> {

    public NewsRecommenedAdapter(int layoutResId, @Nullable List<NewsCommonBody> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsCommonBody item) {
        LogUtils.e("convert-getLayoutPosition"+helper.getLayoutPosition());//getLayoutPosition为总的数据索引,包含头等

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日  E");
        String dateString=format.format(new Date(Long.parseLong(item.create_time)*1000L));
        String preDateString="";
        if(helper.getLayoutPosition()>1){
            preDateString = format.format(new Date(Long.parseLong(mData.get(helper.getLayoutPosition()-2).create_time)*1000L));
            LogUtils.e(dateString+item.create_time);
            LogUtils.e(preDateString+mData.get(helper.getLayoutPosition()-1).create_time);
        }

        if(helper.getLayoutPosition()==1){
            helper.setVisible(R.id.tv_time,true);
            helper.setText(R.id.tv_time,dateString);

        }else if(!dateString.equals(preDateString)){
            LogUtils.e("ppppppppppp");
            helper.setGone(R.id.tv_time,true);
            helper.setText(R.id.tv_time,dateString);
        }else if(dateString.equals(preDateString)){
            helper.setGone(R.id.tv_time,false);
        }

        ImageView view =helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.row_pic_url, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_title,item.title);

        view =helper.getView(R.id.iv_author);
        cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(view);

        helper.setText(R.id.tv_author,item.nickname);
        helper.setText(R.id.tv_flower,item.mood_amount);
        helper.setText(R.id.tv_discussion_number,item.comment_amount);

    }
}
