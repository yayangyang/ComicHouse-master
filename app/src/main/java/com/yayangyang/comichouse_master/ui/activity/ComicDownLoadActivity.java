package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicDownLoadCompleteFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicDownLoadingFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRecommendFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicUpdateFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/9.
 */

public class ComicDownLoadActivity extends BaseActivity implements View.OnClickListener{

    private ArrayList<Fragment> mFragments=new ArrayList<>();

    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.tv_manage)
    TextView tv_manage;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.tv_select_size)
    TextView tv_select_size;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_select)
    TextView tv_select;
    @BindView(R.id.tv_complete)
    TextView tv_complete;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ComicDownLoadActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_download;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        LogUtils.e("ComicDownLoadActivity-initToolBar");
        Intent intent = getIntent();

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        LogUtils.e("ComicDownLoadActivity-initDatas");
//        mFragments.clear();
        mFragments.add(new ComicDownLoadCompleteFragment());
        mFragments.add(new ComicDownLoadingFragment());
    }

    @Override
    public void configViews() {
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_download_complete){
                    mViewPager.setCurrentItem(0);
                }else if(checkedId==R.id.rb_downloading){
                    mViewPager.setCurrentItem(1);
                }
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                LogUtils.e("ComicDownLoadActivity-getItem"+position);
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                LogUtils.e("position:"+position);
                return null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_manage){
            rl_bar.setVisibility(View.VISIBLE);
            mCommonToolbar.setVisibility(View.GONE);
        }else if(v.getId()==R.id.tv_delete){

        }else if(v.getId()==R.id.tv_select){

        }else if(v.getId()==R.id.tv_complete){
            rl_bar.setVisibility(View.GONE);
            mCommonToolbar.setVisibility(View.VISIBLE);
        }
    }
}
