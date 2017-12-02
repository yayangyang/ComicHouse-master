package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerNewsComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.ui.adapter.NewsCommonAdapter;
import com.yayangyang.comichouse_master.ui.contract.NewsRecommendContract;
import com.yayangyang.comichouse_master.ui.presenter.NewsRecommendPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class NewsRecommendFragment extends BaseRVFragment<NewsRecommendPresenter,NewsCommonBody,BaseViewHolder>
        implements NewsRecommendContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private Banner banner;
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
        mPresenter.getNewsCommonBody(page);
        mPresenter.getNewsRecommendHeader();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsCommonBody(page);
    }

    @Override
    public void initDatas() {
//        banner=new Banner(getActivity());
        banner= (Banner) View.inflate(getActivity(),R.layout.view_banner,null);
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
    public void showNewsRecommendHeader(List<NewsRecommendHeader.DataBean> data) {
        LogUtils.e("showNewsRecommendHeader");
        ArrayList images=new ArrayList();
        ArrayList<String> titles=new ArrayList();
        for(int i=0;i<data.size();i++){
            images.add(data.get(i).pic_url);
            titles.add(data.get(i).title);
        }
        initBaner(images,titles);
        mAdapter.setHeaderView(banner);
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


    private void initBaner(ArrayList images,ArrayList<String> titles) {
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(250));
        banner.setLayoutParams(params);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
    }
}
