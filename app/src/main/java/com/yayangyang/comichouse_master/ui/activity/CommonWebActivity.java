package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.open.SocialConstants;
import com.yayangyang.comichouse_master.Bean.ComicSpecialTopic;
import com.yayangyang.comichouse_master.Bean.user.TencentLoginResult;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.BaseLoginActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.LoginUtil;
import com.yayangyang.comichouse_master.utils.PopWindowUtil;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.yayangyang.comichouse_master.view.CustomPopWindow;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/8.
 */

public class CommonWebActivity extends BaseLoginActivity implements View.OnClickListener{

    private ComicSpecialTopic comicSpecialTopic;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.webView)
    WebView mWebView;

    public static void startActivity(Context context, ComicSpecialTopic comicSpecialTopic){
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra(Constant.COMIC_SPECIAL_TOPIC,comicSpecialTopic);
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
        popWindow= PopWindowUtil.createPopupWindow(this,contentView,Constant.NORMAL_LIGHT);
    }

    @Override
    protected void loginComicHouse(TencentLoginResult result) {
        LogUtils.e("result:"+result);
        ToastUtils.showToast("分享成功");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        comicSpecialTopic= (ComicSpecialTopic) getIntent().getSerializableExtra(Constant.COMIC_SPECIAL_TOPIC);
        LogUtils.e("pageUrl:"+comicSpecialTopic.page_url);

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        tv_title.setText(comicSpecialTopic.title);
        iv_share.setOnClickListener(this);

        mWebView.loadUrl(comicSpecialTopic.page_url);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js功能,不加显示不了图片
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

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

}
