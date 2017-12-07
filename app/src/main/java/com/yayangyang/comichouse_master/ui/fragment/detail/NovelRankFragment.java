package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategoryDetail;
import com.yayangyang.comichouse_master.Bean.ComicCategorySelectionEvent;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.Bean.NovelRankSelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicCategoryDetailAdapter;
import com.yayangyang.comichouse_master.ui.adapter.NovelRankAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryDetailContract;
import com.yayangyang.comichouse_master.ui.contract.NovelRankContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicCategoryDetailPresenter;
import com.yayangyang.comichouse_master.ui.presenter.NovelRankPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class NovelRankFragment extends BaseRVFragment<NovelRankPresenter,NovelRank,BaseViewHolder>
        implements NovelRankContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private String tagId="0",type="0";
    private ArrayList mArrayList=new ArrayList();

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

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
        DaggerLightNovelComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        LogUtils.e("tagId:"+tagId);
        LogUtils.e("type:"+type);
        LogUtils.e("page:"+page);
        mPresenter.getNovelRankList(tagId,type,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNovelRankList(tagId,type,page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(NovelRankSelectionEvent event) {
        this.tagId=event.tagId;
        this.type=event.type;
        showDialog();
        onRefresh();
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(NovelRankAdapter.class,
                R.layout.item_novel_rank,mArrayList,true,true);
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
    public void showNovelRankList(List<NovelRank> list,int page) {
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
