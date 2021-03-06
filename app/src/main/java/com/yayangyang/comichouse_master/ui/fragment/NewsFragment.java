package com.yayangyang.comichouse_master.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAkiraInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAnimatedInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAppreciatePictureFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicDisplayFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicPeripheryFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsGameInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsHodgepodgeFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsLightNovelInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsMusicInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsRecommendFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/12.
 */

public class NewsFragment extends BaseFragment {

    private String[] mTitles={"推荐","动画情报","漫画情报","轻小说情报","美图欣赏","游戏资讯",
                        "动漫周边","声优情报","漫展情报","音乐资讯","大杂烩"};
    private ArrayList<Fragment> mFragments=new ArrayList<>();

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        mFragments.add(new NewsRecommendFragment());
        mFragments.add(new NewsAnimatedInformationFragment());
        mFragments.add(new NewsComicInformationFragment());
        mFragments.add(new NewsLightNovelInformationFragment());
        mFragments.add(new NewsAppreciatePictureFragment());
        mFragments.add(new NewsGameInformationFragment());
        mFragments.add(new NewsComicPeripheryFragment());
        mFragments.add(new NewsAkiraInformationFragment());
        mFragments.add(new NewsComicDisplayFragment());
        mFragments.add(new NewsMusicInformationFragment());
        mFragments.add(new NewsHodgepodgeFragment());
    }

    @Override
    public void configViews() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
