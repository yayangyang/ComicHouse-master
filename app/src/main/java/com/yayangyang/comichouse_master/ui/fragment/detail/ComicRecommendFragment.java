package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ReaderApplication;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.ui.adapter.ComicRecommendAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicRecommendContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicRecommendPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComicRecommendFragment extends BaseRVFragment<ComicRecommendPresenter,ComicRecommend,BaseViewHolder>
        implements ComicRecommendContract.View,BaseQuickAdapter.OnItemChildClickListener{

    private Banner banner;
    private ArrayList mArrayList=new ArrayList();
    private Map<String, String> subscriptionParams;
    private Map<String, String> elatedParams;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutResId() {
        LogUtils.e("fragemnet-getLayoutResId");
        return R.layout.fragment_comic_recommend;
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
        mPresenter.getComicRecommendList();
        getData(false);
        getData(true);
        if(ReaderApplication.sLogin!=null
                &&!TextUtils.isEmpty(ReaderApplication.sLogin.data.dmzj_token)
                &&!TextUtils.isEmpty(ReaderApplication.sLogin.data.uid)){
            mPresenter.getSubscriptionComic(subscriptionParams);
        }
        mPresenter.getElatedComic(elatedParams);
    }

    @Override
    public void initDatas() {
//        banner=new Banner(getActivity());
        banner= (Banner) View.inflate(getActivity(),R.layout.view_banner,null);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicRecommendAdapter.class,
                R.layout.item_comic_recommend,mArrayList,true,false);
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
    public void showComicRecommendList(List<ComicRecommend> list) {
        LogUtils.e("showRecommendList");
//        mAdapter.getData().clear();
        mAdapter.setEmptyView(inflate);
//        mRecyclerView.scrollToPosition(0);
        List<ComicRecommend> carouselList=new ArrayList<>();
        List<ComicRecommend> mainlList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                carouselList.add(list.get(i));
            }else{
                mainlList.add(list.get(i));
            }
        }
        List<ComicRecommend.DataBean> data = carouselList.get(0).data;
        ArrayList images=new ArrayList();
        ArrayList<String> titles=new ArrayList();
        for(int i=0;i<data.size();i++){
            images.add(data.get(i).cover);
            titles.add(data.get(i).title);
        }
        initBaner(images,titles);
        mAdapter.setHeaderView(banner);
        mAdapter.setNewData(mainlList);
    }

    @Override
    public void showSubscriptionComic(SubscriptionComic.DataBean data) {

    }

    @Override
    public void showElatedComic(ElatedComic.DataBean data) {

    }

    private void getData(boolean isSubscription) {
        if(!isSubscription){
            elatedParams=new HashMap<>();
            elatedParams.put("category_id","50");
            elatedParams.put("channel", Constant.CHANNEL);
            elatedParams.put("version",Constant.VERSION);
        }
        if(ReaderApplication.sLogin!=null
                &&!TextUtils.isEmpty(ReaderApplication.sLogin.data.dmzj_token)
                &&!TextUtils.isEmpty(ReaderApplication.sLogin.data.uid)){
            if(isSubscription){
                subscriptionParams=new HashMap<>();
                subscriptionParams.put("uid",ReaderApplication.sLogin.data.uid);
                subscriptionParams.put("category_id","49");
                subscriptionParams.put("channel", Constant.CHANNEL);
                subscriptionParams.put("version",Constant.VERSION);
            }else{
                elatedParams.put("uid",ReaderApplication.sLogin.data.uid);
            }
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
