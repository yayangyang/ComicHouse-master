package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.HotSearch;
import com.yayangyang.comichouse_master.Bean.SearchInfo;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.component.DaggerCommonComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicReadAdapter;
import com.yayangyang.comichouse_master.ui.adapter.SearchAdapter;
import com.yayangyang.comichouse_master.ui.contract.SearchContract;
import com.yayangyang.comichouse_master.ui.presenter.SearchActivityPresenter;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/7.
 */

public class SearchActivity extends BaseRVActivity<SearchInfo,BaseViewHolder>
        implements SearchContract.View,View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener{

    private int readingType=0;

    private int colorZ[]={R.color.search_random_a,R.color.search_random_b,R.color.search_random_c};

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.gl)
    GridLayout gl;
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;

    @Inject
    SearchActivityPresenter mPresenter;

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(Constant.READING_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        SearchInfo searchInfo = (SearchInfo) adapter.getData().get(position);
        ComicDetailActivity.startActivity(this,searchInfo.id,searchInfo.title);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SearchInfo searchInfo = (SearchInfo) adapter.getData().get(position);
        ComicDetailActivity.startActivity(this,searchInfo.id,searchInfo.title);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_cancel){
            finish();
        }else if(v.getId()==R.id.tv_clear){
            SettingManager.getInstance().saveSearchHistory(readingType+"",null);
            gl.removeAllViews();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        readingType=getIntent().getIntExtra(Constant.READING_TYPE,0);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if(!TextUtils.isEmpty(et_search.getText().toString())){
            mPresenter.getSearchInfo(readingType,et_search.getText().toString(),page);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if(!TextUtils.isEmpty(et_search.getText().toString())){
            mPresenter.getSearchInfo(readingType,et_search.getText().toString(),page);
        }
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {LogUtils.e("configViews");
        initAdapter(SearchAdapter.class,
                R.layout.item_search,null,true,true);

        tv_cancel.setOnClickListener(this);
        tv_clear.setOnClickListener(this);

        List<String> searchHistory = SettingManager.getInstance().getSearchHistory(readingType + "");
        if(searchHistory!=null){
            for(int i=0;i<searchHistory.size();i++){
                TextView textView = new TextView(this);
                textView.setText(searchHistory.get(searchHistory.size()-(i+1)));
                //使用Spec定义子控件的位置和比重
                GridLayout.Spec rowSpec = GridLayout.spec(i/2, 1f);
                GridLayout.Spec columnSpec = GridLayout.spec(i%2, 1f);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
                int v = ScreenUtils.dpToPxInt(5);
                layoutParams.setMargins(v,v,v,v);
                textView.setLayoutParams(layoutParams);
                gl.addView(textView);
            }
        }
        mPresenter.getHotSearch(readingType);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showHotSearch(List<HotSearch> list) {
        TagAdapter<HotSearch> tagAdapter = new TagAdapter<HotSearch>(list) {
            @Override
            public View getView(FlowLayout parent, int position, HotSearch item) {
                View view = View.inflate(mContext, R.layout.item_search_tag, null);

                int colorIndex = (int) (Math.random() * 3);
                LogUtils.e("colorIndex:"+colorIndex);
                LogUtils.e("colorZ[colorIndex]:"+colorZ[colorIndex]);
//                GradientDrawable drawable = (GradientDrawable) view.getBackground();
//                drawable.setStroke(ScreenUtils.dpToPxInt(1),mContext.getResources().getColor(colorZ[colorIndex]));

                TextView textView = view.findViewById(R.id.tv_content);
                textView.setText(item.name);
                textView.setBackgroundColor(mContext.getResources().getColor(colorZ[colorIndex]));

                return view;
            }
        };
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                List<String> searchHistory = SettingManager.getInstance().getSearchHistory(readingType + "");
                if(searchHistory==null){
                    searchHistory=new ArrayList<>();
                }
                for(int i=0;i<searchHistory.size();i++){
                    if(searchHistory.get(i).equals(tagAdapter.getItem(position).name)){
                        searchHistory.remove(i);
                        break;
                    }
                }
                if(searchHistory.size()>=10){
                    searchHistory.remove(0);
                }
                searchHistory.add(tagAdapter.getItem(position).name);
                SettingManager.getInstance().saveSearchHistory(readingType+"", searchHistory);
                scrollView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                et_search.setText(tagAdapter.getItem(position).name);
                onRefresh();
                return true;
            }
        });
    }

    @Override
    public void showSearchInfo(List<SearchInfo> list) {
        LogUtils.e("showSearchInfo");
        boolean isRefresh = page == 0;
        if(isRefresh){
            LogUtils.e("刷新");
            this.page=0;
            mAdapter.getData().clear();
            mAdapter.setEmptyView(inflate);
            mRecyclerView.scrollToPosition(0);
            mAdapter.setNewData(list);
            this.page++;
        }else if(list==null||list.isEmpty()){
            LogUtils.e("loadMoreEnd");
            mAdapter.loadMoreEnd();
            LogUtils.e("loadMoreEnd");
        }else{
            LogUtils.e("loadMoreComplete"+list.size());
            mAdapter.loadMoreComplete();
            mAdapter.addData(list);
            this.page++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
