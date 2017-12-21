package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.decoration.CommonSpaceItemDecoration;
import com.yayangyang.comichouse_master.ui.activity.ComicCategoryActivity;
import com.yayangyang.comichouse_master.ui.adapter.ComicCategoryAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicCategoryPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ComicCategoryFragment extends BaseRVFragment<ComicCategoryPresenter,ComicCategory,BaseViewHolder>
        implements ComicCategoryContract.View,BaseQuickAdapter.OnItemChildClickListener{

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicCategory comicCategory = (ComicCategory) adapter.getData().get(position);
        ComicCategoryActivity.startActivity(getActivity(),comicCategory.tag_id);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_category;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getComicCategoryList();
    }

    @Override
    public void initDatas() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LogUtils.e("newState:"+newState);
                if(newState==2){
                    LogUtils.e("Glide加载停止");
                    Glide.with(recyclerView).pauseRequests();
                }else{
                    if(Glide.with(recyclerView).isPaused()){
                        LogUtils.e("onScrollStateChanged-Glide加载恢复");
                        Glide.with(recyclerView).resumeRequests();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LogUtils.e("dx:"+dx+",dy:"+dy);
            }
        });
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicCategoryAdapter.class,
                R.layout.item_comic_category,null,true,false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(10)));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        onRefresh();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showComicCategoryList(List<ComicCategory> list) {
        mAdapter.setEmptyView(inflate);
        mAdapter.setNewData(list);
    }
}
