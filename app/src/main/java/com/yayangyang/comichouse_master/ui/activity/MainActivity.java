package com.yayangyang.comichouse_master.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.fragment.ComicFragment;
import com.yayangyang.comichouse_master.ui.fragment.LightNovelFragment;
import com.yayangyang.comichouse_master.ui.fragment.MineFragment;
import com.yayangyang.comichouse_master.ui.fragment.NewsFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import butterknife.BindView;
import zlc.season.rxdownload3.RxDownload;

public class MainActivity extends BaseActivity {

    private Fragment[] mFragments=new Fragment[4];

    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        changeFragment(0);
    }

    @Override
    public void configViews() {

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.comic){
                    ToastUtils.showToast("comic");
                    changeFragment(0);
                }else if(checkedId==R.id.news){
                    ToastUtils.showToast("news");
                    changeFragment(1);
                }else if(checkedId==R.id.light_novel){
                    ToastUtils.showToast("light_novel");
                    changeFragment(2);
                }else if(checkedId==R.id.mine){
                    ToastUtils.showToast("mine");
                    changeFragment(3);
                }
            }
        });
    }

    public void changeFragment(int num){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mFragments[num]==null){
            if(num==0){
                mFragments[num]=new ComicFragment();
            }else if(num==1){
                mFragments[num]=new NewsFragment();
            }else if(num==2){
                mFragments[num]=new LightNovelFragment();
            }else if(num==3){
                mFragments[num]=new MineFragment();
            }
            transaction.add(R.id.fl_content,mFragments[num]);
        }
        for(int i=0;i<mFragments.length;i++){
            if(mFragments[i]!=null&&i!=num){
                //若不隐藏其他fragment时后显示的fragment在上面(才可交互),
                //白色部分(不是view的部分)变透明可看到之前的fragment动画在执行
                transaction.hide(mFragments[i]);
            }else if(i==num){
                transaction.show(mFragments[i]);
            }
        }
        transaction.commit();
    }

}
