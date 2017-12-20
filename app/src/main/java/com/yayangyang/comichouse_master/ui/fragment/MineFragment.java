package com.yayangyang.comichouse_master.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.app.ReaderApplication;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.activity.LoginActivity;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.LoginUtil;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/11/12.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{

    private String[] mTitles={"漫画","小说","更新"};
    private ArrayList<View> mViewArrayList=new ArrayList<>();

    @BindView(R.id.iv_cover)
    ImageView iv_cover;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_login_description)
    TextView tv_login_description;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getLayoutResId() {
        LogUtils.e("MineFragment");
        return R.layout.fragment_mine;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        View view_comic = View.inflate(getActivity(), R.layout.view_comic, null);
        view_comic.findViewById(R.id.rl_comic_subscribe).setOnClickListener(this);
        view_comic.findViewById(R.id.rl_browse_history).setOnClickListener(this);
        view_comic.findViewById(R.id.rl_comic_download).setOnClickListener(this);
        view_comic.findViewById(R.id.rl_comic_review).setOnClickListener(this);

        View view_novel = View.inflate(getActivity(), R.layout.view_novel, null);
        view_novel.findViewById(R.id.rl_novel_subscribe).setOnClickListener(this);
        view_novel.findViewById(R.id.rl_browse_history).setOnClickListener(this);
        view_novel.findViewById(R.id.rl_novel_download).setOnClickListener(this);
        view_novel.findViewById(R.id.rl_novel_review).setOnClickListener(this);

        View view_news = View.inflate(getApplicationContext(), R.layout.view_news, null);
        view_news.findViewById(R.id.rl_news_collection).setOnClickListener(this);
        view_news.findViewById(R.id.rl_news_review).setOnClickListener(this);

        mViewArrayList.add(view_comic);
        mViewArrayList.add(view_novel);
        mViewArrayList.add(view_news);
    }

    @Override
    public void configViews() {
        GlideApp.with(this)
                .load(R.drawable.img_def_head)
                .apply(GlideUtil.getCircleCornerRequestOptions())
                .into(iv_cover);

        mViewPager.setAdapter(new MyAdapter());
        LogUtils.e("测试");
        mTabLayout.setupWithViewPager(mViewPager);
        LogUtils.e("测试");
    }

    @Override
    public void onClick(View v) {
        if(ReaderApplication.sLogin!=null){

        }else{
            LoginActivity.startActivity(getActivity());
        }
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            LogUtils.e("isViewFromObject");
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LogUtils.e("instantiateItem");
            View view = mViewArrayList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LogUtils.e("destroyItem");
            container.removeView((View) object);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("onActivityResult-resultCode:"+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Constant.RETURN_DATA){
            if(LoginUtil.isLogin()){
                GlideUrl cookie = new GlideUrl(ReaderApplication.sLogin.data.photo, new LazyHeaders.Builder()
                        .addHeader("Referer", Constant.IMG_BASE_URL)
                        .addHeader("Accept-Encoding","gzip").build());
                GlideApp.with(mContext).load(cookie)
                        .apply(GlideUtil.getRoundCornerRequestOptions())
                        .into(iv_cover);
                tv_user_name.setText(ReaderApplication.sLogin.data.nickname);
                tv_login_description.setText("");
            }else{
                ToastUtils.showToast("请重新登录");
            }
        }
    }
}
