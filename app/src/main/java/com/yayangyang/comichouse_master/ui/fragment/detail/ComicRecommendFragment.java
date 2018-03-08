package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.BannerBean;
import com.yayangyang.comichouse_master.Bean.ComicInfo;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.ElatedComic;
import com.yayangyang.comichouse_master.Bean.SubscriptionComic;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.decoration.CommonSpaceItemDecoration;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.activity.AuthorIntroduceActivity;
import com.yayangyang.comichouse_master.ui.activity.ComicDetailActivity;
import com.yayangyang.comichouse_master.ui.activity.NewComicWeeklyActivity;
import com.yayangyang.comichouse_master.ui.activity.NewsActivity;
import com.yayangyang.comichouse_master.ui.adapter.ComicRecommendDetailAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicRecommendContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicRecommendPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.LoginUtil;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ComicRecommendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        ComicRecommendContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener,OnBannerListener{

    private String strz[]={"我的订阅","近期必看","火热专题","猜你喜欢","大师级作者怎能不看","国漫也精彩",
            "美漫大事件","热门连载","条漫专区","最新上架"};

    private Map<String, String> subscriptionParams;
    private Map<String, String> elatedParams;

    private List<HeaderViewHolder> mHeaderViewHolderArrayList;

    @BindView(R.id.sw)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.banner)
    com.yayangyang.comichouse_master.view.Banner banner;

    @BindViews({R.id.rv_my_subscribe,R.id.rv_recent_must_look,R.id.rv_host_special,
            R.id.rv_guess_you_like,R.id.rv_master,R.id.rv_good_china_comic,
            R.id.rv_american_comic,R.id.rv_hot_serial,R.id.rv_webtoon,
            R.id.rv_newest_shelves})
    RecyclerView rvz[];

    private List<ComicRecommendDetailAdapter> mAdapterList;

    @Inject
    ComicRecommendPresenter mPresenter;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicRecommend.DataBean dataBean = (ComicRecommend.DataBean) adapter.getData().get(position);
        if(!TextUtils.isEmpty(dataBean.type)){
            if(dataBean.type.equals("1")){
                if(dataBean.obj_id==null){
                    ComicDetailActivity.startActivity(getActivity(),dataBean.id,dataBean.title);
                }else{
                    ComicDetailActivity.startActivity(getActivity(),dataBean.obj_id,dataBean.title);
                }
            }else if(dataBean.type.equals("5")){
                NewComicWeeklyActivity.startActivity(getActivity(),dataBean.obj_id);
            }else if(dataBean.type.equals("6")){

            }else if(dataBean.type.equals("8")){
                AuthorIntroduceActivity.startActivity(getActivity(),dataBean.obj_id);
            }
        }else{
            ToastUtils.showToast("最新上架");
            ComicDetailActivity.startActivity(getActivity(),dataBean.id,dataBean.title);
        }
    }

    @Override
    public void onClick(View v) {
        ToastUtils.showToast("点击了");
        Glide.get(getActivity()).clearMemory();
        System.runFinalization();
        System.gc();
    }

    @Override
    public void OnBannerClick(int position) {
        List<BannerBean> list = banner.getImages();
        BannerBean dataBean = list.get(position);
        if(dataBean.type.equals("1")){
            ComicDetailActivity.startActivity(getActivity(),dataBean.obj_id,dataBean.title);
        }else if(dataBean.type.equals("5")){
            NewComicWeeklyActivity.startActivity(getActivity(),dataBean.obj_id);
        }else if(dataBean.type.equals("6")){

        }else if(dataBean.type.equals("7")){
            NewsActivity.startActivity(getActivity(),dataBean.obj_id,null,null);
        }
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
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.getComicRecommendList();
        getData();
//        if(LoginUtil.isLogin()){
//            getData(true);
//            mPresenter.getSubscriptionComic(subscriptionParams);
//        }else{
//            rvz[0].setVisibility(View.GONE);
//        }
    }

    @Override
    public void initDatas() {
        LogUtils.e("initDatas-rvz.length"+rvz.length);
        mHeaderViewHolderArrayList=new ArrayList<>();
        for(int i=0;i<rvz.length;i++){
            View view = View.inflate(getActivity(), R.layout.header_comic_recommend_detail, null);
            HeaderViewHolder holder = new HeaderViewHolder(view);
            if(i==0){
                holder.iv_cover.setOnClickListener(this);
            }
            if(i==0||i==2||i==3||i==5||i==7){
                holder.iv_more.setOnClickListener(this);
                holder.iv_more.setVisibility(View.VISIBLE);
            }
            if(i==3||i==5||i==7){
                holder.iv_more.setImageResource(R.drawable.img_refrsh_s);
            }
            if(i==0) holder.iv_cover.setImageResource(R.drawable.img_order_refresh);
            if(i==1) holder.iv_cover.setImageResource(R.drawable.img_recent);
            if(i==2) holder.iv_cover.setImageResource(R.drawable.img_hot);
            if(i==3) holder.iv_cover.setImageResource(R.drawable.img_youlike);
            if(i==4) holder.iv_cover.setImageResource(R.drawable.img_master_work);
            if(i==5) holder.iv_cover.setImageResource(R.drawable.img_inner_cartoon);
            if(i==6) holder.iv_cover.setImageResource(R.drawable.img_americ_eve);
            if(i==7) holder.iv_cover.setImageResource(R.drawable.img_hot_serial);
            if(i==8) holder.iv_cover.setImageResource(R.drawable.img_strip_cart);
            if(i==9) holder.iv_cover.setImageResource(R.drawable.img_latest_pub);
            holder.tv_title.setText(strz[i]);
            mHeaderViewHolderArrayList.add(holder);
        }
        LogUtils.e("mHeaderViewHolderArrayList.size"+mHeaderViewHolderArrayList.size());
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");

        mAdapterList=new ArrayList<>();
        for(int i=0;i<rvz.length;i++){
            ComicRecommendDetailAdapter adapter = new ComicRecommendDetailAdapter
                    (R.layout.item_comic_recommend_detail, null);
            adapter.setOnItemChildClickListener(this);
            mAdapterList.add(adapter);
        }
        for(int i=0;i<rvz.length;i++){
            ComicRecommendDetailAdapter adapter=mAdapterList.get(i);
            rvz[i].setNestedScrollingEnabled(false);
            if(i==2||i==6||i==8){
                rvz[i].setLayoutManager(new GridLayoutManager(getActivity(),2));
//                rvz[i].removeItemDecoration(rvz[i].getItemDecorationAt(0));
            }else{
                rvz[i].setLayoutManager(new GridLayoutManager(getActivity(),3));
            }
            rvz[i].addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(5)));
            //使用了brvah框架bindToRecyclerView方法里面执行了setAdapter,
            //执行onAttachedToRecyclerView方法绑定spanCount,所以应该在setLayoutManager之后才能绑定spancount
            adapter.bindToRecyclerView(rvz[i]);
        }
        LogUtils.e("mAdapterList.size"+mAdapterList.size());

        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void showError() {
        LogUtils.e("showError");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showComicRecommendList(List<ComicRecommend> list) {
        LogUtils.e("showRecommendList");

        List<ComicRecommend> carouselList=new ArrayList<>();
        List<ComicRecommend> mainlList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                carouselList.add(list.get(i));
            }else{
                mainlList.add(list.get(i));
            }
        }
        List<ComicRecommend.DataBean> dataBeans = carouselList.get(0).data;
        ArrayList<BannerBean> dataList=new ArrayList();
        ArrayList<String> titles=new ArrayList();
        for(int i=0;i<dataBeans.size();i++){
            BannerBean dataBean = new BannerBean();
            dataBean.cover=dataBeans.get(i).cover;
            dataBean.obj_id=dataBeans.get(i).obj_id;
            dataBean.status=dataBeans.get(i).status;
            dataBean.sub_title=dataBeans.get(i).sub_title;
            dataBean.title=dataBeans.get(i).title;
            dataBean.type=dataBeans.get(i).type;
            dataBean.url=dataBeans.get(i).url;
            dataList.add(dataBean);
//            images.add(data.get(i).cover);
            LogUtils.e("cover:"+dataBeans.get(i).cover);
            titles.add(dataBeans.get(i).title);
        }

        initBaner(dataList,titles);

        for(int i=0;i<mainlList.size();i++){
            LogUtils.e("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+i);
            if(i>=2){
                if(mAdapterList.get(i+2).getHeaderLayout()==null){
                    mAdapterList.get(i+2).setHeaderView(mHeaderViewHolderArrayList.get(i+2).view);
                }
                mAdapterList.get(i+2).setNewData(mainlList.get(i).data);
            }else{
                if(mAdapterList.get(i+1).getHeaderLayout()==null){
                    mAdapterList.get(i+1).setHeaderView(mHeaderViewHolderArrayList.get(i+1).view);
                }
                mAdapterList.get(i+1).setNewData(mainlList.get(i).data);
            }
        }
    }

    @Override
    public void showSubscriptionComic(SubscriptionComic.DataBean data) {
//        ComicRecommend comicRecommend = new ComicRecommend();
//        comicRecommend.category_id=data.category_id;
//        comicRecommend.sort=data.sort;
//        comicRecommend.title=data.title;
        ArrayList<ComicRecommend.DataBean> beans = new ArrayList<>();
        for(int i=0;i<data.data.size();i++){
            ComicRecommend.DataBean bean = new ComicRecommend.DataBean();
            bean.cover=data.data.get(i).cover;
            bean.title=data.data.get(i).title;
            bean.sub_title=data.data.get(i).authors;
            bean.type="1";
            bean.status=data.data.get(i).status;

            bean.id=data.data.get(i).id;
        }
//        comicRecommend.data=beans;
        if(mAdapterList.get(0).getHeaderLayout()==null&&data.data!=null&&!data.data.isEmpty()){
            mAdapterList.get(0).setHeaderView(mHeaderViewHolderArrayList.get(0).view);
        }
        mAdapterList.get(0).setNewData(beans);
//        rvz[0].setVisibility(View.VISIBLE);
    }

    @Override
    public void showElatedComic(ElatedComic.DataBean data) {
        LogUtils.e("showElatedComic"+data.data.size());
        ArrayList<ComicRecommend.DataBean> beans = new ArrayList<>();
        for(int i=0;i<data.data.size();i++){
            ComicRecommend.DataBean bean = new ComicRecommend.DataBean();
            bean.cover=data.data.get(i).cover;
            bean.title=data.data.get(i).title;
            bean.sub_title=data.data.get(i).authors;
            bean.type="1";
            bean.status=data.data.get(i).status;
            bean.num=data.data.get(i).num;
            bean.id=data.data.get(i).id;
            beans.add(bean);
        }

        if(mAdapterList.get(3).getHeaderLayout()==null){
            mAdapterList.get(3).setHeaderView(mHeaderViewHolderArrayList.get(3).view);
        }
        mAdapterList.get(3).setNewData(beans);
    }

    private void getData() {
        if(elatedParams==null){
            elatedParams=new HashMap<>();
            elatedParams.put("category_id","50");
            elatedParams.put("channel", Constant.CHANNEL);
            elatedParams.put("version",Constant.VERSION);
        }

        if(subscriptionParams==null){
            subscriptionParams=new HashMap<>();
            subscriptionParams.put("category_id","49");
            subscriptionParams.put("channel", Constant.CHANNEL);
            subscriptionParams.put("version",Constant.VERSION);
        }

        if(LoginUtil.isLogin()){
            elatedParams.put("uid", ComicApplication.sLogin.data.uid);
            subscriptionParams.put("uid", ComicApplication.sLogin.data.uid);

            //由于使用qq的测试码登录所以取不到我的订阅的数据
            rvz[0].setVisibility(View.VISIBLE);
            mPresenter.getSubscriptionComic(subscriptionParams);
        }else{
            elatedParams.remove("uid");
            subscriptionParams.remove("uid");

            rvz[0].setVisibility(View.GONE);
        }
        mPresenter.getElatedComic(elatedParams);
    }

    private void initBaner(ArrayList images,ArrayList<String> titles) {
//        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams
//                (ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(200));
//        banner.setLayoutParams(params);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置page监听器
        banner.setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("ComicRecommendFragment-onDestroyView");
        mPresenter.detachView();

        mAdapterList=null;
        mHeaderViewHolderArrayList=null;
        //stopAutoPlay方法放在onDestroyView方法之前,
        //BaseFragment的onDestroyView方法里unbind.unbind方法应该是将这些它赋值的变量置为空了
        banner.stopAutoPlay();
        super.onDestroyView();
    }

    static class HeaderViewHolder {
        @BindView(R.id.iv_cover)
        ImageView iv_cover;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_more)
        ImageView iv_more;

        public View view;

        public HeaderViewHolder(View view) {
            this.view=view;
            ButterKnife.bind(this, view);
        }
    }

}
