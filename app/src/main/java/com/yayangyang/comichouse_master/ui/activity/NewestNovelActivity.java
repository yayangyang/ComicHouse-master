package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewestNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicCategoryDetailAdapter;
import com.yayangyang.comichouse_master.ui.adapter.NewestNovelAdapter;
import com.yayangyang.comichouse_master.ui.contract.NewestNovelContract;
import com.yayangyang.comichouse_master.ui.presenter.NewestNovelActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class NewestNovelActivity extends BaseRVActivity<NewestNovel,BaseViewHolder>
        implements NewestNovelContract.View,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{

    private ArrayList mArrayList=new ArrayList();

    @Inject
    NewestNovelActivityPresenter mPresenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, NewestNovelActivity.class));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newest_novel;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLightNovelComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getNewestNovelList(page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewestNovelList(page);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(NewestNovelAdapter.class,
                R.layout.item_newest_novel,mArrayList,true,true);
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
    }

    @Override
    public void showNewestNovelList(List<NewestNovel> list,int page) {
        LogUtils.e("showNewestNovelList");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
