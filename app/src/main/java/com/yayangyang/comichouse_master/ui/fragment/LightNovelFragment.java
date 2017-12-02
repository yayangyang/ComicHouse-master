package com.yayangyang.comichouse_master.ui.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.ui.adapter.LightNovelAdapter;
import com.yayangyang.comichouse_master.ui.contract.LightNovelContract;
import com.yayangyang.comichouse_master.ui.presenter.LightNovelPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public class LightNovelFragment extends BaseRVFragment<LightNovelPresenter,LightNovel,BaseViewHolder>
        implements LightNovelContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener {

    private Banner banner;
    private View headerView;
    private ArrayList mArrayList=new ArrayList();

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_search){

        }else if(v.getId()==R.id.bt_chasing_novels){

        }else if(v.getId()==R.id.bt_looking_for_novel){

        }else if(v.getId()==R.id.bt_ranking_list){

        }
    }

    @Override
    public int getLayoutResId() {
        LogUtils.e("LightNovelFragment");
        return R.layout.fragment_light_novel;
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
        mPresenter.getNovelList();
    }

    @Override
    public void initDatas() {
        headerView = View.inflate(getActivity(), R.layout.header_light_novel,null);
        banner= headerView.findViewById(R.id.banner);
        headerView.findViewById(R.id.iv_search).setOnClickListener(this);
        headerView.findViewById(R.id.bt_chasing_novels).setOnClickListener(this);
        headerView.findViewById(R.id.bt_looking_for_novel).setOnClickListener(this);
        headerView.findViewById(R.id.bt_ranking_list).setOnClickListener(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(LightNovelAdapter.class,
                R.layout.item_light_novel,mArrayList,true,false);
        mAdapter.setOnItemChildClickListener(this);
        onRefresh();
    }

    @Override
    public void showError() {
        loaddingError();
        mAdapter.loadMoreFail();
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNovelList(List<LightNovel> list) {
        LogUtils.e("showNovelList");
        mAdapter.getData().clear();
        mAdapter.setEmptyView(inflate);
        mRecyclerView.scrollToPosition(0);
        List<LightNovel> carouselList=new ArrayList<>();
        List<LightNovel> mainlList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                carouselList.add(list.get(i));
            }else{
                mainlList.add(list.get(i));
            }
        }
        List<LightNovel.DataBean> data = carouselList.get(0).data;
        ArrayList images=new ArrayList();
        ArrayList<String> titles=new ArrayList();
        for(int i=0;i<data.size();i++){
            images.add(data.get(i).cover);
            titles.add(data.get(i).title);
        }
        initBaner(images,titles);
        mAdapter.setHeaderView(headerView);
        mAdapter.setNewData(mainlList);
    }

    private void initBaner(ArrayList images,ArrayList<String> titles) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,700);
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

}
