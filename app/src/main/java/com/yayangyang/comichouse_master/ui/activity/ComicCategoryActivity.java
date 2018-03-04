package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.yayangyang.comichouse_master.Bean.ComicCategorySelectionEvent;
import com.yayangyang.comichouse_master.Bean.NovelRankSelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseComicCategoryActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryDetailFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class ComicCategoryActivity extends BaseComicCategoryActivity {

    private String tagId="0";

    protected String tagIdZ[]={Constant.ThemeType.ALL,Constant.AgeType.ALL,
            Constant.ScheduleType.ALL,Constant.RegionType.ALL};

    @BindView(R.id.rg_group)
    RadioGroup rg_group;

    public static void startActivity(Context context,String tag_id){
        Intent intent = new Intent(context, ComicCategoryActivity.class);
        intent.putExtra(Constant.TAG_ID,tag_id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_category;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    protected List<List<String>> getTabList() {
        return list2;
    }

    @Override
    protected List<String> getTitleList() {
        mTitleList=titleList2;

        int index=0;
        tagId = getIntent().getStringExtra(Constant.TAG_ID);
        if(Constant.themeTypeList.contains(tagId)){
            tagIdZ[0]=tagId;
            index=Constant.themeTypeList.indexOf(tagId);
            mTitleList.set(0,list.get(0).get(index));
        }
        if(Constant.ageTypeList.contains(tagId)){
            tagIdZ[1]=tagId;
            index=Constant.ageTypeList.indexOf(tagId);
            mTitleList.set(1,list.get(1).get(index));
        }
        if(Constant.scheduleTypeList.contains(tagId)){
            tagIdZ[2]=tagId;
            index=Constant.scheduleTypeList.indexOf(tagId);
            mTitleList.set(2,list.get(2).get(index));
        }
        if(Constant.regionTypeList.contains(tagId)){
            tagIdZ[3]=tagId;
            index=Constant.regionTypeList.indexOf(tagId);
            mTitleList.set(3,list.get(3).get(index));
        }

        return titleList2;
    }

    @Override
    public void configViews() {
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_popular){
                    type="0";
                }else if(checkedId==R.id.rb_update){
                    type="1";
                }
                postType();
            }
        });

        ComicCategoryDetailFragment fragment = new ComicCategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_ID,tagId);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_content,fragment).commit();
//        EventBus.getDefault().post(new ComicCategorySelectionEvent(tag_id));
        LogUtils.e("configViews++++++++");
    }

    @Override
    public void onSelect(int index, int position, String title) {
        if(index==0){
            tagIdZ[index]=Constant.themeTypeList.get(position);
        }else if(index==1){
            tagIdZ[index]=Constant.ageTypeList.get(position);
        }else if(index==2){
            tagIdZ[index]=Constant.scheduleTypeList.get(position);
        }else if(index==3){
            tagIdZ[index]=Constant.regionTypeList.get(position);
        }

        postType();
    }

    protected void postType() {
        String tagId="";
        for(int i=0;i<list.size();i++){
            if(!tagId.equals("")&&!tagIdZ[i].equals("0")){
                tagId+="-"+tagIdZ[i];
            }else if(!tagIdZ[i].equals("0")){
                tagId+=tagIdZ[i];
            }
        }

        LogUtils.e("onSelect"+tagId);
        EventBus.getDefault().post(new ComicCategorySelectionEvent(tagId.equals("")?"0":tagId,type));
    }

}
