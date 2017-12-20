package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.Bean.RefreshRankEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.activity.ComicDetailActivity;
import com.yayangyang.comichouse_master.ui.adapter.ComicRankAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicRankDetailContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicRankDetailPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ComicRankDetailFragment extends BaseRVFragment<ComicRankDetailPresenter,ComicInfo,BaseViewHolder>
        implements ComicRankDetailContract.View,BaseQuickAdapter.OnItemChildClickListener {

    private int index= 0;
    private int currentComicType= Constant.ComicType.ALL;
    private int currentDate= Constant.DateType.DAY;
    private int currentRankType= Constant.POPULAR_RANK;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicInfo comicInfo = (ComicInfo) adapter.getData().get(position);
        ComicDetailActivity.startActivity(getActivity(),comicInfo.comic_id,comicInfo.title);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        LogUtils.e("fragemnet-getLayoutResId");
        return R.layout.fragment_comic_rank_detail;
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
        LogUtils.e("currentComicType:"+currentComicType);
        LogUtils.e("currentDate:"+currentDate);
        LogUtils.e("currentRankType:"+currentRankType);
        LogUtils.e("page:"+page);
        mPresenter.getComicRankList(currentComicType,currentDate,currentRankType,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComicRankList(currentComicType,currentDate,currentRankType,page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(RefreshRankEvent event) {
        //getUserVisibleHint方法用在判断ViewPager,在这里不行
        if (event.index==index) {
            LogUtils.e("event.index==index");
            mSwipeRefreshLayout.setRefreshing(true);
            currentComicType=event.currentComicType;
            currentDate=event.currentDate;
            currentRankType=event.currentRankType;
            onRefresh();
        }
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        index= (int) arguments.get(Constant.MY_INDEX);
        currentComicType= (int) arguments.get(Constant.CURRENT_COMIC_TYPE);
        currentDate= (int) arguments.get(Constant.CURRENT_DATE);
        currentRankType= (int) arguments.get(Constant.CURRENT_RANK_TYPE);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicRankAdapter.class,
                R.layout.item_comic_rank,null,true,true);
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
    public void showComicRankList(List<ComicInfo> list, int page) {
        LogUtils.e("showComicRankList");
        boolean isRefresh = page == 0;
        if(isRefresh){
            LogUtils.e("刷新");
            this.page=0;
//            mAdapter.getData().clear();
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
