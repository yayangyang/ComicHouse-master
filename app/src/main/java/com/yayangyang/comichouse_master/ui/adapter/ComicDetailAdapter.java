package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComicDetailAdapter extends BaseQuickAdapter<ComicDetailBody,BaseViewHolder> {

    public ComicDetailAdapter(int layoutResId, @Nullable List<ComicDetailBody> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicDetailBody item) {
        LogUtils.e("convert+++++++++++++++");
        ImageView iv_cover=helper.getView(R.id.iv_cover);
        GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getCircleCornerRequestOptions())
                .into(iv_cover);

        helper.setText(R.id.tv_nickname,item.nickname);
        float aFloat = Float.parseFloat(item.sex);
        LogUtils.e("aFloat:"+(int)aFloat);
        if("1".equals((int)aFloat+"")){
            LogUtils.e("xxxxxxxxxxxxxxxxxxxxxxxx");
            helper.setImageResource(R.id.iv_sender,R.drawable.img_newcomment_boy);
        }else{
            helper.setImageResource(R.id.iv_sender,R.drawable.img_newcomment_girl);
        }

        helper.setText(R.id.tv_content,item.content);
        BigDecimal bd = new BigDecimal(item.create_time);
        LogUtils.e("bd:"+bd);
        String dateString = FormatUtils.getStringByTimeStamp("yyyy-MM-dd", bd.toPlainString());
        helper.setText(R.id.tv_date,dateString);
        aFloat = Float.parseFloat(item.like_amount);
        helper.setText(R.id.rb_like_amount, (int) aFloat+"");
        helper.addOnClickListener(R.id.rb_like_amount);

        aFloat = Float.parseFloat(item.obj_id);
        String obj_id=(int)aFloat+"";
        aFloat = Float.parseFloat(item.id);
        String id=(int)aFloat+"";
        boolean isHelpful = SettingManager.getInstance().getIsHelpful(obj_id + "," + id);
        if(isHelpful){
            helper.setChecked(R.id.rb_like_amount,true);
        }
    }

}
