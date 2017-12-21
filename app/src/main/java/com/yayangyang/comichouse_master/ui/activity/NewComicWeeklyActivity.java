package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.NewComicWeekly;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.adapter.NewComicWeeklyAdapter;
import com.yayangyang.comichouse_master.ui.contract.NewComicWeeklyContract;
import com.yayangyang.comichouse_master.ui.presenter.NewComicWeeklyActivityPresenter;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/12/5.
 */

public class NewComicWeeklyActivity extends BaseActivity
        implements NewComicWeeklyContract.View,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener {

    private String object_id;
    private ArrayList<NewComicWeekly.DataBean> mArrayList=new ArrayList<>();

    private ContentViewHolder holder;
    private NewComicWeeklyAdapter mAdapter;

    private View contentView;

    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    NewComicWeeklyActivityPresenter mPresenter;

    public static void startActivity(Context context, String object_id){
        Intent intent = new Intent(context, NewComicWeeklyActivity.class);
        intent.putExtra(Constant.OBJECT_ID,object_id);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_comic_weekly;
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
    public void initDatas() {
        object_id=getIntent().getStringExtra(Constant.OBJECT_ID);

        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        contentView = View.inflate(this, R.layout.header_view_new_comic_weekly, null);
        holder = new ContentViewHolder(contentView);

        mAdapter = new NewComicWeeklyAdapter(R.layout.item_new_comic_weekly, mArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

        mPresenter.getNewComicWeekly(object_id);
    }

    @Override
    public void showError() {
        dismissDialog();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showNewComicWeekly(NewComicWeekly newComicWeekly) {
        GlideUrl cookie = new GlideUrl(newComicWeekly.mobile_header_pic, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(holder.iv_cover);

        tv_toolbar_title.setText(newComicWeekly.title.substring(0,newComicWeekly.title.indexOf(" ")));
        holder.tv_title.setText(newComicWeekly.title);
        holder.tv_description.setText(newComicWeekly.description);
        holder.tv_review.setText("评论("+newComicWeekly.comment_amount+")");

        for(int i=0;i<newComicWeekly.comics.length;i++){
            mArrayList.add(newComicWeekly.comics[i]);
        }

        mAdapter.setHeaderView(contentView);
        mAdapter.setNewData(mArrayList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    static class ContentViewHolder {
        @BindView(R.id.iv_cover)
        ImageView iv_cover;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_review)
        TextView tv_review;

        public ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
