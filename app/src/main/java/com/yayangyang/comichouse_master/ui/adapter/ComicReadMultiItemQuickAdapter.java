package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ChapterReadBean;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public abstract class ComicReadMultiItemQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public static final int TYPE_NOT_FOUND = -404;

    private SparseIntArray layouts;

    public ComicReadMultiItemQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        ChapterReadBean item = (ChapterReadBean) mData.get(position);
        if (item.mHotViews==null) {
            return Constant.ITEM_COMIC_READ;
        }else{
            return Constant.ITEM_COMIC_READ_FOOTER;
        }
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(Constant.ITEM_COMIC_READ, layoutResId);
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
