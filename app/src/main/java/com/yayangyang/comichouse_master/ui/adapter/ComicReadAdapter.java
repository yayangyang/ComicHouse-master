package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRead;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ComicReadAdapter extends BaseQuickAdapter<ComicRead,BaseViewHolder> {

    public ComicReadAdapter(int layoutResId, @Nullable List<ComicRead> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicRead item) {

    }
}
