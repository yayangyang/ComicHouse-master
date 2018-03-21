package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadCompleteEvent;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadEvent;
import com.yayangyang.comichouse_master.Bean.RefreshDownLoadingEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicDownLoadCompleteFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicDownLoadingFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload3.RxDownload;

/**
 * Created by Administrator on 2018/3/9.
 */

public class ComicDownLoadActivity extends BaseActivity implements View.OnClickListener{

    private String title,cover,comicId;

    private ArrayList<ComicDetailHeader.ChaptersBean.DataBean> downLoadList,list;
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
    @BindView(R.id.bt_dir)
    Button bt_dir;
    @BindView(R.id.bt_append)
    Button bt_append;
    @BindView(R.id.bt_all_pause)
    Button bt_all_pause;
    @BindView(R.id.bt_all_start)
    Button bt_all_start;


    public static void startActivity(Context context,String title,String cover,String comicId,
                                     List<ComicDetailHeader.ChaptersBean.DataBean> downLoadList,
                                     List<ComicDetailHeader.ChaptersBean.DataBean> list) {
        Intent intent = new Intent(context, ComicDownLoadActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.COVER,cover);
        intent.putExtra(Constant.COMIC_ID,comicId);
        intent.putExtra(Constant.DOWNLOAD_BEANS,(ArrayList)downLoadList);
        intent.putExtra(Constant.DATA_BEANS,(ArrayList)list);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_manage){
            rl_bar.setVisibility(View.VISIBLE);
            mCommonToolbar.setVisibility(View.GONE);
            EventBus.getDefault().post(new RefreshDownLoadCompleteEvent(Constant.CAN_SELECT));
        }else if(v.getId()==R.id.tv_delete){
            EventBus.getDefault().post(new RefreshDownLoadEvent(Constant.DELETE));
        }else if(v.getId()==R.id.tv_select){
            EventBus.getDefault().post(new RefreshDownLoadCompleteEvent(tv_select.getText().toString()));
            if(tv_select.getText().toString().equals("全选")){
                tv_select.setText("反选");
            }else{
                tv_select.setText("全选");
            }
        }else if(v.getId()==R.id.tv_complete){
            rl_bar.setVisibility(View.GONE);
            mCommonToolbar.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new RefreshDownLoadCompleteEvent(Constant.CLEAR_SELECT));
        }else if(v.getId()==R.id.bt_dir){
            ComicDetailActivity.startActivity(this,comicId,title,false);
        }else if(v.getId()==R.id.bt_append){
            ComicSelectDownLoadActivity.startActivity(this,title,cover,comicId,list);
        }else if(v.getId()==R.id.bt_all_pause){
            RxDownload.INSTANCE.stopAll().subscribe();
        }else if(v.getId()==R.id.bt_all_start){
            RxDownload.INSTANCE.startAll().subscribe();
        }
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
        title=intent.getStringExtra(Constant.TITLE);
        cover=intent.getStringExtra(Constant.COVER);
        comicId=intent.getStringExtra(Constant.COMIC_ID);
        downLoadList= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>) intent.getSerializableExtra(Constant.DOWNLOAD_BEANS);
        list= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>) intent.getSerializableExtra(Constant.DATA_BEANS);

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        LogUtils.e("ComicDownLoadActivity-initDatas");

        ComicDownLoadCompleteFragment comicDownLoadCompleteFragment = new ComicDownLoadCompleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.COMIC_ID,comicId);
        comicDownLoadCompleteFragment.setArguments(bundle);
        mFragments.add(comicDownLoadCompleteFragment);

        ComicDownLoadingFragment fragment = new ComicDownLoadingFragment();
        bundle = new Bundle();
        bundle.putString(Constant.COVER,cover);
        bundle.putString(Constant.COMIC_ID,comicId);
        bundle.putSerializable(Constant.DATA_BEANS,getDownloadInfoList());
        fragment.setArguments(bundle);
        mFragments.add(fragment);

        EventBus.getDefault().register(this);
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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    RadioButton radioButton = (RadioButton) rg_group.getChildAt(0);
                    radioButton.setChecked(true);

                    EventBus.getDefault().post(new RefreshDownLoadCompleteEvent(Constant.NEW_DATA));
                }else{
                    RadioButton radioButton = (RadioButton) rg_group.getChildAt(1);
                    radioButton.setChecked(true);
                }
                rl_bar.setVisibility(View.GONE);
                mCommonToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_manage.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        bt_dir.setOnClickListener(this);
        bt_append.setOnClickListener(this);
        bt_all_pause.setOnClickListener(this);
        bt_all_start.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.e("ComicDownLoadActivity-onNewIntent");
        super.onNewIntent(intent);
        title=intent.getStringExtra(Constant.TITLE);
        comicId=intent.getStringExtra(Constant.COMIC_ID);
        downLoadList= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>) intent.getSerializableExtra(Constant.DOWNLOAD_BEANS);
        list= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>) intent.getSerializableExtra(Constant.DATA_BEANS);

        ArrayList<ComicChapterDownLoadInfo> urlList = getDownloadInfoList();
        LogUtils.e("发了11");
        EventBus.getDefault().post(new RefreshDownLoadingEvent(comicId,urlList));
        LogUtils.e("发了22");
    }

    private ArrayList<ComicChapterDownLoadInfo> getDownloadInfoList() {
        ArrayList<ComicChapterDownLoadInfo> infoList = new ArrayList<>();
        if(downLoadList!=null&&!downLoadList.isEmpty()){
            char c=title.charAt(0);
            LogUtils.e("原字符:"+c);
            if(Pinyin.isChinese(c)){
                c=Pinyin.toPinyin(c).toLowerCase().charAt(0);
            }
            LogUtils.e("转化后字符:"+c);
            for(int i=0;i<downLoadList.size();i++){
                String url=Constant.DownLoad_BASE_URL+"/"+c+"/"+comicId+"/"
                        +downLoadList.get(i).chapter_id+".zip";
                ComicChapterDownLoadInfo info = new ComicChapterDownLoadInfo();
                info.setUrl(url);
                info.setCover(cover);
                info.setTitle(title);
                info.setChapterTitle(downLoadList.get(i).chapter_title);
                info.setFilesize(downLoadList.get(i).filesize);
                infoList.add(info);
            }
        }

        return infoList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selection(RefreshDownLoadEvent event) {
        LogUtils.e("ComicDownLoadActivity-收到");
        tv_select_size.setText("已选择"+event.selectSize+"项");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
