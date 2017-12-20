package com.yayangyang.comichouse_master.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.loader.GlideImageLoader;
import com.yayangyang.comichouse_master.utils.DeviceUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/12/8.
 */

public class TestActivity  extends BaseActivity {

    private ArrayList<String> mArrayLists=new ArrayList<>();
    private ArrayList<String> mTitleLists=new ArrayList<>();

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.recyclerview02)
    RecyclerView recyclerview02;
    @BindView(R.id.recyclerview03)
    RecyclerView recyclerview03;

    @BindView(R.id.image01)
    ImageView image01;
    @BindView(R.id.image02)
    ImageView image02;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
//        for(int i=0;i<5;i++){
//            mArrayLists.add("http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg");
//        }
        mArrayLists.add("http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg");
        mArrayLists.add("http://images.dmzj.com/tuijian/750_480/171206youxitj1.jpg");
        mArrayLists.add("http://images.dmzj.com/tuijian/750_480/171207benghuaitj1.jpg");
        mArrayLists.add("http://images.dmzj.com/tuijian/750_480/1204kongxiang01.jpg");
        mArrayLists.add("http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg");

        mTitleLists.add("ww");mTitleLists.add("ww");
        mTitleLists.add("ww");mTitleLists.add("ww");mTitleLists.add("ww");

        initBaner(mArrayLists,mTitleLists);
    }

    @Override
    public void configViews() {
//        mRecyclerView.addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(6)));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        mRecyclerView.setAdapter(new TestAdapter(R.layout.item_test,mArrayLists));
//
//        recyclerview02.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerview02.setAdapter(new TestAdapter(R.layout.item_test,mArrayLists));
//
//        recyclerview03.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerview03.setAdapter(new TestAdapter(R.layout.item_test,mArrayLists));

        ImageView view = findViewById(R.id.iv);
        GlideUrl cookie = new GlideUrl("http://images.dmzj.com/tuijian/320_170/170623yinglingtj2.jpg", new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
//        Glide.with(this).load(cookie)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.drawable.a)
//                .transform(new GlideRoundTransform(this,6))
//                .into(view);

//        MultiTransformation multi = new MultiTransformation(
//                new RoundedCornersTransformation(20, 0));
        float density = getResources().getDisplayMetrics().density;
        Log.e("density","density"+density);
        RoundedCornersTransformation roundedCornersTransformation =
                new RoundedCornersTransformation((int) (density*20), 0);
//        Glide.with(this).loadOptions.bitmapTransform(roundedCornersTransformation))
//                .into(view);(cookie)
//                .apply(Request
//
        DeviceUtils.printDisplayInfo(this);

        Glide.with(this).load(cookie)
                .apply((RequestOptions.bitmapTransform(roundedCornersTransformation)))
                .into(image01);
        Glide.with(this).load(cookie)
                .into(image02);
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
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected void onDestroy() {
        banner.stopAutoPlay();
        super.onDestroy();
    }
}
