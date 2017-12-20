package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.view.View;

import com.bumptech.glide.MemoryCategory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.ComicSpecialTopic;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicRankAdapter;
import com.yayangyang.comichouse_master.ui.adapter.ComicSpecialTopicAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicRecommendContract;
import com.yayangyang.comichouse_master.ui.contract.ComicSpecialTopicContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicRecommendPresenter;
import com.yayangyang.comichouse_master.ui.presenter.ComicSpecialTopicPresenter;
import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */

public class ComicSpecialTopicFragment extends BaseRVFragment<ComicSpecialTopicPresenter,ComicSpecialTopic,BaseViewHolder>
        implements ComicSpecialTopicContract.View,BaseQuickAdapter.OnItemChildClickListener {

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_special_topic;
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
        mPresenter.getComicSpecialTopicList(page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComicSpecialTopicList(page);
    }

    @Override
    public void initDatas() {
        LogUtils.e("initDatas-size"+GlideApp.get(mRecyclerView.getContext()).getBitmapPool().getMaxSize());
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicSpecialTopicAdapter.class,
                R.layout.item_comic_special_topic,null,true,true);
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
    public void showComicSpecialTopicList(List<ComicSpecialTopic> list, int page) {
        LogUtils.e("showComicSpecialTopicList");
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
        LogUtils.e("ComicSpecialTopicFragment-onDestroyView");
//        GlideApp.with(mRecyclerView).clear(mRecyclerView);
        super.onDestroyView();
        //getView()应该返回的是包裹布局的view
        if(getView()==null){
            LogUtils.e("getView()为空");
        }
        if(parentView==null){
            LogUtils.e("parentView为空");
        }
    }
}
