package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategory;
import com.yayangyang.comichouse_master.Bean.NovelCategoryDetail;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicCategoryAdapter;
import com.yayangyang.comichouse_master.ui.adapter.NovelCategoryAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicCategoryContract;
import com.yayangyang.comichouse_master.ui.contract.NovelCategoryContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicCategoryPresenter;
import com.yayangyang.comichouse_master.ui.presenter.NovelCategoryActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/11/30.
 */

public class NovelCategoryActivity extends BaseRVActivity<NovelCategory,BaseViewHolder>
        implements NovelCategoryContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private ArrayList mArrayList=new ArrayList();

    @Inject
    NovelCategoryActivityPresenter mPresenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, NovelCategoryActivity.class));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        NovelCategory novelCategory = (NovelCategory) adapter.getData().get(position);
        NovelCategoryDetailActivity.startActivity(this,novelCategory.tag_id);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_novel_category;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLightNovelComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        LogUtils.e("NovelCategoryActivity-initToolBar");
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getNovelCategoryList();
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(NovelCategoryAdapter.class,
                R.layout.item_novel_category,mArrayList,true,false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
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
    public void showNovelCategoryList(List<NovelCategory> list) {
        mAdapter.setEmptyView(inflate);
        mAdapter.setNewData(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
