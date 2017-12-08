package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicCategory;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ReaderApplication;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ComicCategoryFragment extends BaseRVFragment<ComicCategoryPresenter,ComicCategory,BaseViewHolder>
        implements ComicCategoryContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private ArrayList mArrayList=new ArrayList();

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

    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicCategoryAdapter.class,
                R.layout.item_comic_category,mArrayList,true,false);
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
