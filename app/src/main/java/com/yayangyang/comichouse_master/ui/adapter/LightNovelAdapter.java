package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.decoration.SpaceItemDecoration;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class LightNovelAdapter extends BaseQuickAdapter<LightNovel,BaseViewHolder> {
    public LightNovelAdapter(int layoutResId, @Nullable List<LightNovel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LightNovel item) {
        LogUtils.e("convert+++++++++++++++");
        RecyclerView recyclerView = helper.getView(R.id.recyclerview);
        GridLayoutManager gridLayoutManager=null;
//        SpaceItemDecoration decoration=null;
        if(item.data.size()==3||item.data.size()==6){
            gridLayoutManager=new GridLayoutManager(mContext, 3);
//            decoration=new SpaceItemDecoration(ScreenUtils.dpToPxInt(10),3);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(0));
//        recyclerView.addItemDecoration(decoration);
        LightNovelDetailAdapter mAdapter =
                new LightNovelDetailAdapter(R.layout.item_light_novel_detail, item.data);
        mAdapter.setOnItemChildClickListener(getOnItemChildClickListener());
        recyclerView.setAdapter(mAdapter);
    }

}