package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.base.NewsCommonContract;
import com.yayangyang.comichouse_master.base.NewsCommonPresenter;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerNewsComponent;
import com.yayangyang.comichouse_master.ui.adapter.NewsCommonAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsAppreciatePictureFragment extends BaseRVFragment<NewsCommonPresenter<NewsCommonContract.View>,NewsCommonBody,BaseViewHolder>
        implements NewsCommonContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private int newsType=Constant.NewsType.APPRECIATE_PICTURE;

    private ArrayList mArrayList=new ArrayList();

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        LogUtils.e("fragemnet-getLayoutResId");
        return R.layout.fragment_news_common;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getNewsCommonBody(newsType,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsCommonBody(newsType,page);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(NewsCommonAdapter.class,
                R.layout.item_news_common,mArrayList,true,true);
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
    public void showNewsCommonBody(List<NewsCommonBody> list, int page) {
        LogUtils.e("showNewsRecommendBody");
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

}
