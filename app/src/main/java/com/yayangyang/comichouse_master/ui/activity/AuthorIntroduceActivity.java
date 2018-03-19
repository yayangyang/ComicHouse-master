package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.AuthorIntroduce;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.decoration.CommonSpaceItemDecoration;
import com.yayangyang.comichouse_master.ui.adapter.AuthorIntroduceAdapter;
import com.yayangyang.comichouse_master.ui.contract.AuthorIntroduceContract;
import com.yayangyang.comichouse_master.ui.presenter.AuthorIntroduceActivityPresenter;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class AuthorIntroduceActivity extends BaseActivity
        implements AuthorIntroduceContract.View,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener {

    private String object_id;
    private ArrayList mArrayList=new ArrayList<>();

    private AuthorIntroduceAdapter mAdapter;

    @BindView(R.id.iv_cover)
    ImageView iv_cover;
    @BindView(R.id.tv_author_name)
    TextView tv_author_name;
    @BindView(R.id.tv_comic_size)
    TextView tv_comic_size;
    @BindView(R.id.tv_all_subscribe)
    TextView tv_all_subscribe;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    AuthorIntroduceActivityPresenter mPresenter;

    public static void startActivity(Context context, String subject){
        Intent intent = new Intent(context, AuthorIntroduceActivity.class);
        intent.putExtra(Constant.OBJECT_ID,subject);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        AuthorIntroduce.DataBean dataBean = (AuthorIntroduce.DataBean) adapter.getData().get(position);
        ComicDetailActivity.startActivity(this,dataBean.id,dataBean.name,true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getLayoutId() {
        statusBarColor= ContextCompat.getColor(this,R.color.colorAccent);
        return R.layout.activity_author_introduce;
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
        mAdapter = new AuthorIntroduceAdapter(R.layout.item_author_introduce, mArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(10)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemChildClickListener(this);

        mPresenter.getAuthorIntroduce(object_id);
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
    public void showAuthorIntroduce(AuthorIntroduce authorIntroduce) {
        GlideUrl cookie = new GlideUrl(authorIntroduce.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(this).load(cookie)
                .apply(GlideUtil.getCircleCornerRequestOptions())
                .into(iv_cover);

        tv_author_name.setText(authorIntroduce.nickname);
        tv_comic_size.setText("他的漫画作品   ("+authorIntroduce.data.size()+")");
        tv_all_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mAdapter.setNewData(authorIntroduce.data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
