package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.user.ComicUpdate;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.MyBaseMultiItemQuickAdapter;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class ComicUpdateAdapter extends MyBaseMultiItemQuickAdapter<ComicUpdate,BaseViewHolder> {

    public ComicUpdateAdapter(int id,List<ComicUpdate> data) {
        super(data);
        addItemType(Constant.LINEARLAYOUT_MANAGER, R.layout.item_comic_update_linear);
        addItemType(Constant.GRIDLAYOUT_MANAGER, R.layout.item_comic_update_grid);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicUpdate item) {
        if(helper.getItemViewType()==Constant.GRIDLAYOUT_MANAGER){
            LogUtils.e("GridLayout");
            ImageView view =helper.getView(R.id.iv_cover);
            GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                    .addHeader("Referer", Constant.IMG_BASE_URL)
                    .addHeader("Accept-Encoding","gzip").build());
            Glide.with(mContext).load(cookie)
                    .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                    (mContext,6)).into(view);

            helper.addOnClickListener(R.id.frameLayout);
//            helper.setText(R.id.tv_last_update_chapter_name,item.last_update_chapter_name);
            helper.setText(R.id.tv_title,item.title);
        }else if(helper.getItemViewType()==Constant.LINEARLAYOUT_MANAGER){
            LogUtils.e("LinearLayout");
            ImageView view =helper.getView(R.id.iv_cover);
            GlideUrl cookie = new GlideUrl(item.cover, new LazyHeaders.Builder()
                    .addHeader("Referer", Constant.IMG_BASE_URL)
                    .addHeader("Accept-Encoding","gzip").build());
            Glide.with(mContext).load(cookie)
                    .placeholder(R.drawable.avatar_default) .transform(new GlideRoundTransform
                    (mContext,6)).into(view);

            helper.setText(R.id.tv_title,item.title);
            helper.setText(R.id.tv_author,item.authors);
            helper.setText(R.id.tv_type,item.types);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateString=format.format(new Date(Long.parseLong(item.last_updatetime)*1000L));
            helper.setText(R.id.tv_date,dateString);
            helper.setText(R.id.bt_last_update_chapter_name,item.last_update_chapter_name);
        }
    }
}
