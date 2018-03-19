package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.ComicCategorySelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.activity.ComicDetailActivity;
import com.yayangyang.comichouse_master.ui.adapter.ComicCategoryDetailAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryDetailContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicCategoryDetailPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ComicCategoryDetailFragment extends BaseRVFragment<ComicCategoryDetailPresenter,ComicCategoryDetail,BaseViewHolder>
        implements ComicCategoryDetailContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private String tagId="0",type="0";

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicCategoryDetail comicCategoryDetail = (ComicCategoryDetail) adapter.getData().get(position);
        ComicDetailActivity.startActivity(getActivity(),comicCategoryDetail.id,comicCategoryDetail.title,true);
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
        mPresenter.getComicCategoryDetailList(tagId,type,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComicCategoryDetailList(tagId,type,page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(ComicCategorySelectionEvent event) {
        this.tagId=event.tagId;
        this.type=event.type;
        showDialog();
        onRefresh();
    }

    @Override
    public void initDatas() {
        tagId= (String) getArguments().get(Constant.TAG_ID);
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicCategoryDetailAdapter.class,
                R.layout.item_comic_category_detail,null,true,true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setAdapter(mAdapter);//setAdapter会绑定GridLayoutManager的span监听
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
        dismissDialog();
    }

    @Override
    public void showComicCategoryDetailList(List<ComicCategoryDetail> list,int page) {
        LogUtils.e("showComicCategoryDetailList");
        boolean isRefresh = page == 0;
        if(isRefresh){
            LogUtils.e("刷新");
            this.page=0;
            mAdapter.getData().clear();
            mAdapter.setEmptyView(inflate);
            mRecyclerView.scrollToPosition(0);
            mAdapter.setNewData(list);
            this.page++;
        }else if(list==null||list.isEmpty()){
            LogUtils.e("loadMoreEnd");
            mAdapter.loadMoreEnd();
            LogUtils.e("loadMoreEnd");
        }else{
            LogUtils.e("loadMoreComplete"+list.size());
            mAdapter.loadMoreComplete();
            mAdapter.addData(list);
            this.page++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
