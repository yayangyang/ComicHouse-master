package com.yayangyang.comichouse_master.ui.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.BannerBean;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.LightNovel;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.ui.activity.NewestNovelActivity;
import com.yayangyang.comichouse_master.ui.activity.NovelCategoryActivity;
import com.yayangyang.comichouse_master.ui.activity.NovelRankActivity;
import com.yayangyang.comichouse_master.ui.activity.SearchActivity;
import com.yayangyang.comichouse_master.ui.adapter.LightNovelAdapter;
import com.yayangyang.comichouse_master.ui.contract.LightNovelContract;
import com.yayangyang.comichouse_master.ui.presenter.LightNovelPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.view.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/12.
 */

public class LightNovelFragment extends BaseRVFragment<LightNovelPresenter,LightNovel,BaseViewHolder>
        implements LightNovelContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener,OnBannerListener {

    private Banner banner;
    private View headerView;
    private ArrayList mArrayList=new ArrayList();

    @BindView(R.id.iv_search)
    ImageView iv_search;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        NewestNovelActivity.startActivity(getActivity());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_search){
            SearchActivity.startActivity(getActivity(), Constant.NOVEL_TYPE);
        }else if(v.getId()==R.id.bt_chasing_novels){
            NewestNovelActivity.startActivity(getActivity());
        }else if(v.getId()==R.id.bt_looking_for_novel){
            NovelCategoryActivity.startActivity(getActivity());
        }else if(v.getId()==R.id.bt_ranking_list){
            NovelRankActivity.startActivity(getActivity());
        }
    }

    @Override
    public void OnBannerClick(int position) {

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
        iv_search.setOnClickListener(this);

        headerView = View.inflate(getActivity(), R.layout.header_light_novel,null);
        banner= headerView.findViewById(R.id.banner);
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
        ArrayList<BannerBean> dataList=new ArrayList();
        ArrayList<String> titles=new ArrayList();
        for(int i=0;i<data.size();i++){
            BannerBean dataBean = new BannerBean();
            dataBean.cover=data.get(i).cover;
            dataBean.id=data.get(i).id;
            dataBean.obj_id=data.get(i).obj_id;
            dataBean.status=data.get(i).status;
            dataBean.sub_title=data.get(i).sub_title;
            dataBean.title=data.get(i).title;
            dataBean.type=data.get(i).type;
            dataBean.url=data.get(i).url;
            dataList.add(dataBean);
//            images.add(data.get(i).cover);
            titles.add(data.get(i).title);
        }
        initBaner(dataList,titles);
        mAdapter.setHeaderView(headerView);
        mAdapter.setNewData(mainlList);
    }

    private void initBaner(ArrayList images,ArrayList<String> titles) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(200));
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
    public void onHiddenChanged(boolean hidden) {
        LogUtils.e("onHiddenChanged-hidden:"+hidden);
        if(hidden){
            banner.stopAutoPlay();
        }else{
            banner.startAutoPlay();
        }
    }
}
