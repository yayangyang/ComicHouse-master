package com.yayangyang.comichouse_master.base;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public abstract class BaseDownLoadViewHolder extends BaseViewHolder {

    public BaseDownLoadViewHolder(View view) {
        super(view);
    }

    public abstract void onAttach();

    public abstract void onDetach();

}
