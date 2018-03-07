package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.open.SocialConstants;
import com.yayangyang.comichouse_master.Bean.Fabulous;
import com.yayangyang.comichouse_master.Bean.NewsBean;
import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.Bean.user.TencentLoginResult;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseLoginActivity;
import com.yayangyang.comichouse_master.base.BaseLoginRvActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerNewsComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.contract.NewsContract;
import com.yayangyang.comichouse_master.ui.presenter.NewsActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.LoginUtil;
import com.yayangyang.comichouse_master.utils.PopWindowUtil;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.yayangyang.comichouse_master.view.CustomPopWindow;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/6.
 */

public class NewsActivity extends BaseLoginActivity implements NewsContract.View,View.OnClickListener{

    private String objectId,moodAmount,commentAmount;

    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.edit_publish)
    EditText edit_publish;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.iv_oraise)
    ImageView iv_oraise;
    @BindView(R.id.tv_oraise)
    TextView tv_oraise;
    @BindView(R.id.iv_save)
    ImageView iv_save;

    @Inject
    NewsActivityPresenter mPresenter;

    public static void startActivity(Context context,String objectId,String commentAmount,String moodAmount){
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(Constant.OBJECT_ID,objectId);
        intent.putExtra(Constant.COMMENT_AMOUNT,commentAmount);
        intent.putExtra(Constant.MOOD_AMOUNT,moodAmount);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_share){
            if(popWindow==null){
                createPopupWindow();
            }
            //设置遮罩
            ScreenUtils.setScreenBrightness(SettingManager.getInstance().getReadLightProgress()-20,this);

            popWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
        }else if (v.getId() == R.id.edit_publish) {
            LogUtils.e("点击了editText");
            if(!LoginUtil.isLogin()){
                LoginActivity.startActivity(this);
            }else{
                PublishReviewActivity.startActivity(this,objectId);
            }
        }else if(v.getId() == R.id.iv_comment){

        }else if(v.getId() == R.id.iv_oraise){
            if(!SettingManager.getInstance().getIsAlreadyFabulous(objectId)){
                mPresenter.fabulous(objectId);
            }else{
                ToastUtils.showToast("不能重复点赞喔!");
            }
        }else if(v.getId() == R.id.iv_save){

        }else if(v.getId()==R.id.tv_sina){

        }else if(v.getId()==R.id.tv_qq){
            share();
        }else if(v.getId()==R.id.tv_friend_circle){

        }else if(v.getId()==R.id.tv_qq_zone){

        }else if(v.getId()==R.id.tv_wetchat){

        }else if(v.getId()==R.id.tv_copy_url){

        }
    }

    private CustomPopWindow popWindow;
    private View contentView;

    private void createPopupWindow() {
        View contentView = View.inflate(mContext, R.layout.view_news_bottom_share_popupwindow, null);
        contentView.findViewById(R.id.tv_sina).setOnClickListener(this);
        contentView.findViewById(R.id.tv_qq).setOnClickListener(this);
        contentView.findViewById(R.id.tv_friend_circle).setOnClickListener(this);
        contentView.findViewById(R.id.tv_qq_zone).setOnClickListener(this);
        contentView.findViewById(R.id.tv_wetchat).setOnClickListener(this);
        contentView.findViewById(R.id.tv_copy_url).setOnClickListener(this);
        popWindow=PopWindowUtil.createPopupWindow(this,contentView);
    }

    @Override
    protected void loginComicHouse(TencentLoginResult result) {
        LogUtils.e("result:"+result);
        ToastUtils.showToast("分享成功");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        objectId=getIntent().getStringExtra(Constant.OBJECT_ID);
        commentAmount=getIntent().getStringExtra(Constant.COMMENT_AMOUNT);
        moodAmount=getIntent().getStringExtra(Constant.MOOD_AMOUNT);
        LogUtils.e("objectId:"+objectId);
        LogUtils.e("commentAmount:"+commentAmount);
        LogUtils.e("moodAmount:"+moodAmount);

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);
    }

    @Override
    public void configViews() {
        tv_comment.setText(commentAmount);
        tv_oraise.setText(moodAmount);
        if(SettingManager.getInstance().getIsAlreadyFabulous(objectId)){
            iv_oraise.setImageResource(R.drawable.icon_news_details_oraise_first);
        }else{
            iv_oraise.setImageResource(R.drawable.icon_news_details_oraise_two);
        }

        iv_share.setOnClickListener(this);
        edit_publish.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        iv_oraise.setOnClickListener(this);
        iv_save.setOnClickListener(this);

        showDialog();
        mPresenter.getNews(objectId);
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
    public void showNews(NewsBean newsBean) {
        LogUtils.e("showNews");
        mWebView.loadUrl(Constant.API_BASE_URL+"/v3/article/show/"+objectId+".html");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js功能,不加显示不了图片
    }

    @Override
    public void showFabulousResult(Fabulous fabulous) {
        iv_oraise.setImageResource(R.drawable.icon_news_details_oraise_first);
        SettingManager.getInstance().saveFabulous(objectId,true);

        ToastUtils.showCustomToast(R.layout.view_fabulous);
    }

    public void share() {
        LogUtils.e("share");
        if(loginListener==null){
            loginListener=new BaseUIListener();
        }
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(SocialConstants.PARAM_TARGET_URL, "http://connect.qq.com/");
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(SocialConstants.PARAM_TITLE, "我在测试");
        //分享的图片URL
        bundle.putString(SocialConstants.PARAM_IMAGE_URL,
                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
        //分享的消息摘要，最长50个字
        bundle.putString(SocialConstants.PARAM_SUMMARY, "测试");
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(SocialConstants.PARAM_APPNAME, "??我在测试");
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString(SocialConstants.PARAM_APP_SOURCE, "星期几" + "222222");

        ComicApplication.mTencent.shareToQQ(this, bundle , loginListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
