package com.yayangyang.comichouse_master.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.ui.viewholder.DownLoadViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public abstract class BaseDownLoadAdapter<T, K extends BaseDownLoadViewHolder> extends BaseQuickAdapter<T,K> {

    public BaseDownLoadAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public void onViewAttachedToWindow(K holder) {
        super.onViewAttachedToWindow(holder);
        holder.onAttach();
    }

    @Override
    public void onViewDetachedFromWindow(K holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onDetach();
    }

}
