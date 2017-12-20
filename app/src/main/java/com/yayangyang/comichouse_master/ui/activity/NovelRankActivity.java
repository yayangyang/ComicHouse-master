package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.yayangyang.comichouse_master.Bean.NovelRankSelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseComicCategoryActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerLightNovelComponent;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryDetailFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NovelRankFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class NovelRankActivity extends BaseComicCategoryActivity {

    private String tagId="0";

    @BindView(R.id.rg_group)
    RadioGroup rg_group;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, NovelRankActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_novel_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLightNovelComponent.builder()
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
        return novelRankList;
    }

    @Override
    protected List<String> getTitleList() {
        return null;
    }

    @Override
    public void configViews() {
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_popular_rank){
                    type="0";
                }else if(checkedId==R.id.rb_suscribe_rank){
                    type="1";
                }
                LogUtils.e("rg_group-type:"+type);
                EventBus.getDefault().post(new NovelRankSelectionEvent(tagId,type));
            }
        });

        NovelRankFragment fragment = new NovelRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_ID,tagId);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_content,fragment).commit();
    }

    @Override
    public void onSelect(int index, int position, String title) {
        EventBus.getDefault().post(new NovelRankSelectionEvent(Constant.novelCommonTypeList.get(position),type));
    }

}
