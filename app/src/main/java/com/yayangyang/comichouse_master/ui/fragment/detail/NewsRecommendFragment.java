package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.NewsRecommendHeader;
import com.yayangyang.comichouse_master.Bean.NewsCommonBody;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerNewsComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.ui.adapter.NewsCommonAdapter;
import com.yayangyang.comichouse_master.ui.adapter.NewsRecommenedAdapter;
import com.yayangyang.comichouse_master.ui.contract.NewsRecommendContract;
import com.yayangyang.comichouse_master.ui.presenter.NewsRecommendPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class NewsRecommendFragment extends BaseRVFragment<NewsRecommendPresenter,NewsCommonBody,BaseViewHolder>
        implements NewsRecommendContract.View,BaseQuickAdapter.OnItemChildClickListener,OnBannerListener{

    private int newsType= Constant.NewsType.RECOMMEND;

    private Banner banner;
    private ArrayList mArrayList=new ArrayList();

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showToast("点击了");
//        Glide.get(mRecyclerView.getContext()).clearMemory();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void OnBannerClick(int position) {

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
        mPresenter.getNewsRecommendHeader();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsCommonBody(newsType,page);
    }

    @Override
    public void initDatas() {
//        banner=new Banner(getActivity());
        banner= (Banner) View.inflate(getActivity(),R.layout.view_banner,null);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(NewsRecommenedAdapter.class,
                R.layout.item_news_common,mArrayList,true,true);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LogUtils.e("newState:"+newState);
                if(newState==2){
                    LogUtils.e("Glide加载停止");
                    Glide.with(recyclerView).pauseRequests();
                }else{
                    if(Glide.with(recyclerView).isPaused()){
                        LogUtils.e("onScrollStateChanged-Glide加载恢复");
                        Glide.with(recyclerView).resumeRequests();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LogUtils.e("dx:"+dx+",dy:"+dy);
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(Glide.with(v).isPaused()){
                        LogUtils.e("onTouch-Glide加载恢复");
                        Glide.with(v).resumeRequests();
                    }
                }
                return false;
            }
        });
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
                (ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(190));
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
        //设置page监听器
        banner.setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("NewsRecommendFragment-onDestroyView");
        banner.stopAutoPlay();
        banner=null;
//        Glide.get(mRecyclerView.getContext()).clearMemory();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtils.e("NewsRecommendFragment-onDestroy");
        super.onDestroy();
    }

}
