package com.yayangyang.comichouse_master.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicDetailAdapter;
import com.yayangyang.comichouse_master.ui.adapter.ComicReadAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicReadContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicReadActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ComicReadActivity extends BaseRVActivity<ComicRead,BaseViewHolder>
        implements ComicReadContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener {

    @Inject
    ComicReadActivityPresenter mPresenter;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicReadAdapter.class,
                R.layout.item_comic_read,null,false,true);
        mAdapter.setOnItemChildClickListener(this);
        onRefresh();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {

    }

    @Override
    public void showComicChapter(List<ComicRead> list) {
        LogUtils.e("showComicChapter");
        boolean isRefresh = page == 0;
        if(isRefresh){
            LogUtils.e("刷新");
            page=1;
            mAdapter.getData().clear();
            mAdapter.setEmptyView(inflate);
//            mRecyclerView.scrollToPosition(0);
            mAdapter.setNewData(list);
            page++;
        }else if(list==null||list.isEmpty()){
            LogUtils.e("loadMoreEnd");
            mAdapter.loadMoreEnd();
            LogUtils.e("loadMoreEnd");
        }else{
            LogUtils.e("loadMoreComplete"+list.size());
            mAdapter.loadMoreComplete();
            mAdapter.addData(list);
            page++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
