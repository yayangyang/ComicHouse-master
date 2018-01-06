package com.yayangyang.comichouse_master.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ChapterReadBean;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.activity.ComicReadActivity;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ComicReadAdapter extends ComicReadMultiItemQuickAdapter<ChapterReadBean,BaseViewHolder> {

    private int colorZ[]={R.color.voice_color_01,R.color.voice_color_02,R.color.voice_color_03};

    private ComicReadActivity.OnMyTagClickListener onMyTagClickListener;

    public ComicReadAdapter(int layoutResId, @Nullable List<ChapterReadBean> data) {
        super(data);
        addItemType(Constant.ITEM_COMIC_READ, R.layout.item_comic_read);
        addItemType(Constant.ITEM_COMIC_READ_FOOTER, R.layout.item_comic_read_footer);
        //context是在onCreateViewHolder方法中有parent(为RecyclerView)取得
//        LogUtils.e("mContext:"+mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChapterReadBean item) {
        LogUtils.e("convert+++++++++++++++"+helper.getLayoutPosition());
        if(helper.getItemViewType()==Constant.ITEM_COMIC_READ){
            ImageView image=helper.getView(R.id.image);
            GlideUrl cookie = new GlideUrl(item.page_url, new LazyHeaders.Builder()
                    .addHeader("Referer", Constant.IMG_BASE_URL)
                    .addHeader("Accept-Encoding","gzip").build());

            //设置最小高度防止未满屏多次创建绑定item(在这里可能只是起到标示作用,不会因view小于预定值强行改变其大小)
            image.setMinimumHeight(ScreenUtils.getScreenHeight()*2);

            GlideApp.with(mContext).load(cookie)
                    .into(image);
//            ViewGroup.LayoutParams params = image.getLayoutParams();
//            params.height=ScreenUtils.getScreenHeight()*2;
//            image.setLayoutParams(params);

//            GlideApp.with(mContext).asBitmap().load(cookie).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                    int imageWidth = resource.getWidth();
//                    int imageHeight = resource.getHeight();
//                    int height = ScreenUtils.getScreenWidth() * imageHeight / imageWidth;
//                    ViewGroup.LayoutParams para = image.getLayoutParams();
//                    para.height = height;
//                    para.width = ScreenUtils.getScreenWidth();
//                    image.setImageBitmap(resource);
//                }
//            });
        }else{
            TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
            TagAdapter<ComicReadHotView> tagAdapter = new TagAdapter<ComicReadHotView>(item.mHotViews) {
                @Override
                public View getView(FlowLayout parent, int position, ComicReadHotView item) {
                    View view = View.inflate(mContext, R.layout.item_read_tag, null);
                    ImageView iv_cover = view.findViewById(R.id.iv_cover);
                    GlideUrl cookie = new GlideUrl(item.photo, new LazyHeaders.Builder()
                            .addHeader("Referer", Constant.IMG_BASE_URL)
                            .addHeader("Accept-Encoding", "gzip").build());
                    GlideApp.with(mContext).load(cookie)
                            .apply(GlideUtil.getCircleCornerRequestOptions())
                            .into(iv_cover);

                    int colorIndex = (int) (Math.random() * 3);
                    LogUtils.e("colorIndex:"+colorIndex);
                    LogUtils.e("colorZ[colorIndex]:"+colorZ[colorIndex]);
                    GradientDrawable drawable = (GradientDrawable) view.getBackground();
                    drawable.setStroke(ScreenUtils.dpToPxInt(1),mContext.getResources().getColor(colorZ[colorIndex]));

                    TextView textView = view.findViewById(R.id.tv_content);
                    if(SettingManager.getInstance().getIsVisibleReview()){
                        textView.setText(item.title);
                    }else{
                        textView.setText(FormatUtils.getMeaninglessStringByStringLength(item.title.length()));
                    }
                    textView.setTextColor(mContext.getResources().getColor(colorZ[colorIndex]));

                    ImageView iv_praise = view.findViewById(R.id.iv_praise);
                    if (colorIndex == 0) {
                        iv_praise.setImageResource(R.drawable.img_ad_praise);
                    } else if (colorIndex == 1) {
                        iv_praise.setImageResource(R.drawable.img_ad_praise2);
                    } else if (colorIndex == 2) {
                        iv_praise.setImageResource(R.drawable.img_ad_praise3);
                    }
                    return view;
                }
            };
            flowLayout.setAdapter(tagAdapter);
            flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    onMyTagClickListener.tagClick(tagAdapter.getItem(position).id);
                    return true;
                }
            });
            View tv_close_review = helper.getView(R.id.tv_close_review);
            View tv_visible_review = helper.getView(R.id.tv_visible_review);
            if(!SettingManager.getInstance().getIsVisibleReview()){
                tv_close_review.setVisibility(View.GONE);
                tv_visible_review.setVisibility(View.VISIBLE);
                flowLayout.setAlpha(0.5f);
            }
            tv_close_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_close_review.setVisibility(View.GONE);
                    tv_visible_review.setVisibility(View.VISIBLE);
                    flowLayout.setAlpha(0.5f);
                    SettingManager.getInstance().saveIsVisibleReview(false);
                    tagAdapter.notifyDataChanged();
                }
            });
            tv_visible_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_close_review.setVisibility(View.VISIBLE);
                    tv_visible_review.setVisibility(View.GONE);
                    flowLayout.setAlpha(1.0f);
                    SettingManager.getInstance().saveIsVisibleReview(true);
                    tagAdapter.notifyDataChanged();
                }
            });

            helper.addOnClickListener(R.id.flowLayout);
            helper.addOnClickListener(R.id.tv_subscribe);
            helper.addOnClickListener(R.id.tv_share);
            helper.addOnClickListener(R.id.tv_more_review);
            helper.addOnClickListener(R.id.tv_publish);
        }
    }

    public void setOnMyTagClickListener(ComicReadActivity.OnMyTagClickListener onMyTagClickListener){
        this.onMyTagClickListener=onMyTagClickListener;
    }

}
