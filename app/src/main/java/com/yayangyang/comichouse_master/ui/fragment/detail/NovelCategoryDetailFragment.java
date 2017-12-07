package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NovelCategoryDetail;
import com.yayangyang.comichouse_master.Bean.NovelCategoryDetailSelectionEvent;
import com.yayangyang.comichouse_master.Bean.NovelRank;
import com.yayangyang.comichouse_master.Bean.NovelRankSelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.adapter.NovelCategoryDetailAdapter;
import com.yayangyang.comichouse_master.ui.adapter.NovelRankAdapter;
import com.yayangyang.comichouse_master.ui.contract.NovelCategoryDetailContract;
import com.yayangyang.comichouse_master.ui.contract.NovelRankContract;
import com.yayangyang.comichouse_master.ui.presenter.NovelCategoryDetailPresenter;
import com.yayangyang.comichouse_master.ui.presenter.NovelRankPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class NovelCategoryDetailFragment extends BaseRVFragment<NovelCategoryDetailPresenter,NovelCategoryDetail,BaseViewHolder>
        implements NovelCategoryDetailContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private String tagId="0",scheduleType="0",type="0";
    private ArrayList mArrayList=new ArrayList();

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_novel_category_detail;
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
        LogUtils.e("scheduleType:"+scheduleType);
        LogUtils.e("type:"+type);
        LogUtils.e("page:"+page);
        mPresenter.getNovelCategoryDetailList(tagId,scheduleType,type,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNovelCategoryDetailList(tagId,scheduleType,type,page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(NovelCategoryDetailSelectionEvent event) {
        this.tagId=event.tagId;
        this.scheduleType=event.scheduleType;
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
        initAdapter(NovelCategoryDetailAdapter.class,
                R.layout.item_novel_category_detail,mArrayList,true,true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
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
        dismissDialog();
    }

    @Override
    public void showNovelCategoryDetailList(List<NovelCategoryDetail> list,int page) {
        LogUtils.e("showNovelCategoryDetailList");
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
