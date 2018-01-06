package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.base.Constant;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public abstract class ComicUpdateMultiItemQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public static final int TYPE_NOT_FOUND = -404;

    private SparseIntArray layouts;

    public ComicUpdateMultiItemQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
//        Log.e("mData","ww"+mData.size());
        Object item = mData.get(position);
        if (getRecyclerView().getLayoutManager() instanceof GridLayoutManager) {
            return Constant.GRIDLAYOUT_MANAGER;
        }else{
            return Constant.LINEARLAYOUT_MANAGER;
        }
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(Constant.GRIDLAYOUT_MANAGER, layoutResId);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }
}
