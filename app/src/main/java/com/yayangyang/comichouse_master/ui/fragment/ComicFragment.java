package com.yayangyang.comichouse_master.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRankDetailFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRankFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRecommendFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicSpecialTopicFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicUpdateFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/12.
 */

public class ComicFragment extends BaseFragment{

    private String[] mTitles={"推荐","更新","分类","排行","专题"};
    private ArrayList<Fragment> mFragments=new ArrayList<>();

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        LogUtils.e("initDataswwwwwwwwwwwwwwwwwwwwww");
//        mFragments.clear();
        mFragments.add(new ComicRecommendFragment());
        mFragments.add(new ComicUpdateFragment());
        mFragments.add(new ComicCategoryFragment());
        mFragments.add(new ComicRankFragment());
        mFragments.add(new ComicSpecialTopicFragment());
    }

    @Override
    public void configViews() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                LogUtils.e("ComicFragment-getItem"+position);
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                LogUtils.e("position:"+position);
                return mTitles[position];
            }
        });
        LogUtils.e("测试");
        mTabLayout.setupWithViewPager(mViewPager);
        LogUtils.e("测试");
    }

}
