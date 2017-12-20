package com.yayangyang.comichouse_master.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ComicRecommendDetailAdapter extends BaseQuickAdapter<ComicRecommend.DataBean,BaseViewHolder> {
    public ComicRecommendDetailAdapter(int layoutResId, @Nullable List<ComicRecommend.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ComicRecommend.DataBean item) {
        ImageView view;
        ImageView iv_cover_normal=helper.getView(R.id.iv_cover_normal);
        ImageView iv_cover_big=helper.getView(R.id.iv_cover_big);
        FrameLayout fr_normal=helper.getView(R.id.fr_normal);
        FrameLayout fr_big=helper.getView(R.id.fr_big);
        fr_normal.setVisibility(View.GONE);
        fr_big.setVisibility(View.GONE);
        GridLayoutManager layoutManager = (GridLayoutManager) getRecyclerView().getLayoutManager();
        if(layoutManager.getSpanCount()==2){
            view =iv_cover_big;
            fr_big.setVisibility(View.VISIBLE);
            LogUtils.e("gggggggggg");
        }else{
            view =iv_cover_normal;
            fr_normal.setVisibility(View.VISIBLE);
        }

//        float density = mContext.getResources().getDisplayMetrics().density;
//        RoundedCornersTransformation roundedCornersTransformation =
//                new RoundedCornersTransformation((int) (density*20), 0);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
//                .apply(RequestOptions.bitmapTransform(roundedCornersTransformation))
                .into(view);
        LogUtils.e("哦哦哦"+item.cover);
        helper.addOnClickListener(R.id.fr_normal);
        helper.addOnClickListener(R.id.fr_big);
        helper.setText(R.id.tv_title,item.title);
        if(!item.title.equals(item.sub_title)){
            helper.setText(R.id.tv_author,item.sub_title);
            helper.setVisible(R.id.tv_author,true);
        }else{
            helper.setGone(R.id.tv_author,false);
        }
    }
}
