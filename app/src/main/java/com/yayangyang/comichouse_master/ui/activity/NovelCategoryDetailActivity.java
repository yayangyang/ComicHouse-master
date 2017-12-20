package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.yayangyang.comichouse_master.Bean.NovelCategoryDetailSelectionEvent;
import com.yayangyang.comichouse_master.Bean.NovelRankSelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseComicCategoryActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NovelCategoryDetailFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NovelRankFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class NovelCategoryDetailActivity extends BaseComicCategoryActivity {

    private String tagId="0";
    private String schduleType="0";

    @BindView(R.id.rg_group)
    RadioGroup rg_group;

    public static void startActivity(Context context,String tag_id){
        Intent intent = new Intent(context, NovelCategoryDetailActivity.class);
        intent.putExtra(Constant.TAG_ID,tag_id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_novel_category_detail;
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
    protected List<List<String>> getTabList() {
        return novelCategoryList;
    }

    @Override
    protected List<String> getTitleList() {
        List<String> titleList=novelCategoryTitleList;

        int index=0;
        tagId = getIntent().getStringExtra(Constant.TAG_ID);
        LogUtils.e("NovelCategoryDetailActivity-getTitleList-tagId:"+tagId);
        if(Constant.novelCommonTypeList.contains(tagId)){
            index=Constant.novelCommonTypeList.indexOf(tagId);
            LogUtils.e("index:"+index);
            titleList.set(0,list.get(0).get(index));
        }

        return titleList;
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
                LogUtils.e("rg_group-type:"+type);
                EventBus.getDefault().post(new NovelCategoryDetailSelectionEvent(tagId,schduleType,type));
            }
        });

        tagId = getIntent().getStringExtra(Constant.TAG_ID);

        NovelCategoryDetailFragment fragment = new NovelCategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_ID,tagId);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_content,fragment).commit();
    }

    @Override
    public void onSelect(int index, int position, String title) {
        if(index==0){
            tagId=Constant.novelCommonTypeList.get(position);
            EventBus.getDefault().post(new NovelCategoryDetailSelectionEvent(tagId,schduleType,type));
        }else if(index==1){
            schduleType=position+"";
            EventBus.getDefault().post(new NovelCategoryDetailSelectionEvent(tagId,schduleType,type));
        }
    }

}
