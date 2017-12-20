package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ComicChapterAdapter extends BaseQuickAdapter<ComicDetailHeader.ChaptersBean.DataBean,BaseViewHolder> {

    public ComicChapterAdapter(int layoutResId, @Nullable List<ComicDetailHeader.ChaptersBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicDetailHeader.ChaptersBean.DataBean item) {
        LogUtils.e("ComicChapterAdapter-convert"+helper.getLayoutPosition());
        LogUtils.e("getItemCount():"+getItemCount());
        LogUtils.e("mData.size():"+mData.size());
        helper.setText(R.id.tv_chapter,item.chapter_title);

        helper.addOnClickListener(R.id.frameLayout);
    }
}
