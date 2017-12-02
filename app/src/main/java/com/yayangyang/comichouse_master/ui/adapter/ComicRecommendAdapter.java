package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.decoration.SpaceItemDecoration;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ComicRecommendAdapter extends BaseQuickAdapter<ComicRecommend,BaseViewHolder> {
    public ComicRecommendAdapter(int layoutResId, @Nullable List<ComicRecommend> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicRecommend item) {
        LogUtils.e("convert+++++++++++++++");
        RecyclerView recyclerView = helper.getView(R.id.recyclerview);
        GridLayoutManager gridLayoutManager=null;
        SpaceItemDecoration decoration=null;
        if(item.data.size()==3||item.data.size()==6){
            gridLayoutManager=new GridLayoutManager(mContext, 3);
            decoration=new SpaceItemDecoration(ScreenUtils.dpToPxInt(0),3);
        }else if(item.data.size()==4){
            gridLayoutManager=new GridLayoutManager(mContext, 2);
            decoration=new SpaceItemDecoration(ScreenUtils.dpToPxInt(10),2);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(0));
        recyclerView.addItemDecoration(decoration);
        ComicRecommendDetailAdapter mAdapter =
                new ComicRecommendDetailAdapter(R.layout.item_comic_recommend_detail, item.data);
        mAdapter.setOnItemChildClickListener(getOnItemChildClickListener());
        LogUtils.e("fffffffffffffff");
        mAdapter.bindToRecyclerView(recyclerView);
//        recyclerView.setAdapter(mAdapter);
    }

}
