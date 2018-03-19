package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseDownLoadAdapter;
import com.yayangyang.comichouse_master.ui.viewholder.DownLoadViewHolder;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import zlc.season.rxdownload3.core.Mission;

/**
 * Created by Administrator on 2018/3/14.
 */

public class ComicDownLoadingAdapter extends BaseDownLoadAdapter<ComicDownLoadMission,DownLoadViewHolder> {

    public ComicDownLoadingAdapter(int layoutResId, @Nullable List<ComicDownLoadMission> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(DownLoadViewHolder helper, ComicDownLoadMission item) {
        LogUtils.e("ComicDownLoadingAdapter-convert");
        helper.setData(helper.getLayoutPosition(),item,this);

        helper.setText(R.id.tv_chapter_title,item.getChapterTitle());
    }

//    @Override
//    public DownLoadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LogUtils.e("onCreateDefViewHolder-type:"+viewType);
//        return super.onCreateViewHolder(parent, viewType);
//    }
//
//    @Override
//    protected DownLoadViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
//        LogUtils.e("onCreateDefViewHolder");
//        return super.onCreateDefViewHolder(parent, viewType);
//    }
//
//    @Override
//    protected DownLoadViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
//        LogUtils.e("createBaseViewHolder11");
//        return super.createBaseViewHolder(parent, layoutResId);
//    }
//
//    @Override
//    protected DownLoadViewHolder createBaseViewHolder(View view) {
//        LogUtils.e("createBaseViewHolder22");
//        DownLoadViewHolder baseViewHolder = super.createBaseViewHolder(view);
//        return baseViewHolder;
//    }
}
