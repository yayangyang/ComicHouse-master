package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.decoration.CommonSpaceItemDecoration;
import com.yayangyang.comichouse_master.ui.adapter.ComicChapterAdapter;
import com.yayangyang.comichouse_master.ui.adapter.ComicDetailAdapter;
import com.yayangyang.comichouse_master.ui.adapter.SelectChapterAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/6.
 */

public class SelectChapterActivity extends BaseActivity
        implements BaseQuickAdapter.OnItemChildClickListener{

    private String comicId;
    private ComicDetailHeader.ChaptersBean chaptersBean=null;

    private SelectChapterAdapter mAdapter;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static void startActivity(Context context,String comicId,ComicDetailHeader.ChaptersBean chaptersBean) {
        Intent intent = new Intent(context, SelectChapterActivity.class);
        intent.putExtra(Constant.COMIC_ID,comicId);
        intent.putExtra(Constant.CHAPTERS_BEAN,chaptersBean);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicDetailHeader.ChaptersBean.DataBean dataBean =
                (ComicDetailHeader.ChaptersBean.DataBean) adapter.getData().get(position);
        ComicReadActivity.startActivity(this,comicId,dataBean.chapter_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_chapter;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        LogUtils.e("SelectChapterActivity-initToolBar");
        comicId=getIntent().getStringExtra(Constant.COMIC_ID);
        chaptersBean= (ComicDetailHeader.ChaptersBean) getIntent().getSerializableExtra(Constant.CHAPTERS_BEAN);

        tv_title.setText(chaptersBean.title);
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        LogUtils.e("initDatas");
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        mAdapter=new SelectChapterAdapter(R.layout.item_comic_chapter,chaptersBean.data,comicId);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerview.addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(3)));
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void onResume() {
        LogUtils.e("ComicDetailActivity-onResume");
        super.onResume();
        if(mAdapter!=null){
            mAdapter.getCurrentChapterName();
            mAdapter.notifyDataSetChanged();
        }
    }

}
