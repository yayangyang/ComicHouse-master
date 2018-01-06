package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.contract.PublishViewPointContract;
import com.yayangyang.comichouse_master.ui.presenter.PublishViewPointActivityPresenter;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PublishViewPointActivity extends BaseActivity implements PublishViewPointContract.View{

    private String comicId,chapterId;

    private List<ComicReadViewPoint> mList=new ArrayList<>();

    private TagAdapter<ComicReadViewPoint> tagAdapter;

    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.flowLayout)
    TagFlowLayout mTagFlowLayout;

    @Inject
    PublishViewPointActivityPresenter mPresenter;

    public static void startActivity(Context context,String comicId,String chapterId) {
        Intent intent = new Intent(context, PublishViewPointActivity.class);
        intent.putExtra(Constant.COMIC_ID,comicId);
        intent.putExtra(Constant.CHAPTER_ID,chapterId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_view_poinit;
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

        Intent intent = getIntent();
        comicId=intent.getStringExtra(Constant.COMIC_ID);
        chapterId=intent.getStringExtra(Constant.CHAPTER_ID);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        mPresenter.getComicReadViewPoint(comicId,chapterId);
        showDialog();
    }

    @Override
    public void showError() {
        if(!NetworkUtils.isAvailable(this)){
            ToastUtils.showToast("网络异常");
        }
        dismissDialog();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showComicReadViewPoint(List<ComicReadViewPoint> list) {
        mList=list;
        tagAdapter = new TagAdapter<ComicReadViewPoint>(list) {
            @Override
            public View getView(FlowLayout parent, int position, ComicReadViewPoint item) {
                View view = View.inflate(PublishViewPointActivity.this, R.layout.item_view_point_tag, null);
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(item.content);
                return view;
            }
        };
        mTagFlowLayout.setAdapter(tagAdapter);

        if(list!=null&&!list.isEmpty()){
            int commentCount=0;
            for(int i=0;i<list.size();i++){
                commentCount+=Integer.parseInt(list.get(i).num);
            }
            tv_info.setText("共有"+commentCount+"人发表了观点,请选择您的观点:");
        }else{
            tv_info.setText("共有0人发表了观点,请选择您的观点:");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
