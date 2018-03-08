package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.open.SocialConstants;
import com.yayangyang.comichouse_master.Bean.ChapterReadBean;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicRead;
import com.yayangyang.comichouse_master.Bean.ComicReadHotView;
import com.yayangyang.comichouse_master.Bean.ComicReadViewPoint;
import com.yayangyang.comichouse_master.Bean.user.TencentLoginResult;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.Receiver.NetworkChangeReceiver;
import com.yayangyang.comichouse_master.Receiver.TimeChangeReceiver;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BaseLoginRvActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicReadAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicReadContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicReadActivityPresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.MyNetWorkUtil;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.PopWindowUtil;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.StringUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.yayangyang.comichouse_master.view.CustomPopWindow;
import com.yayangyang.comichouse_master.view.myView.MyLoadMoreView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ComicReadActivity extends BaseLoginRvActivity<ChapterReadBean,BaseViewHolder>
        implements ComicReadContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener,
        View.OnTouchListener,NetworkChangeReceiver.OnNetWorkChangeListener,TimeChangeReceiver.OnTimeChangeListener,
        SeekBar.OnSeekBarChangeListener,RadioGroup.OnCheckedChangeListener{

    private int x=0;

    private int topPage=0,bottomPage=0,currentLoadPage=0,oldPageIndex=0;

    private boolean isEnter=true;
    private boolean canScrollToTop=false,canScrollToBottom=false,isCallScroll=false;

    private String comicId,currentChapterId,appoint_chapter_name;
    private String chapterTitle,chapterProgress,stateInfo;

    private List<ComicDetailHeader.ChaptersBean.DataBean> data_beans;

    private Map<String,List<ComicReadHotView>> mHotViewListMap=new HashMap<>();
    private Map<String,List<ComicReadViewPoint>> mViewPointListMap=new HashMap<>();
    private Map<String,Integer> checkMap=new HashMap<>();

    private MyLoadMoreView header,footer;

    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_drag_read_progress)
    TextView tv_drag_read_progress;
    @BindView(R.id.tv_current_info)
    TextView tv_current_info;
    @BindView(R.id.tv_read_progress)
    TextView tv_read_progress;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.fl)
    FrameLayout fl;
    @BindView(R.id.seekBar_change_light)
    SeekBar seekBar_change_light;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.rg_clip)
    RadioGroup rg_clip;
    @BindView(R.id.rg_talk_option)
    RadioGroup rg_talk_option;
    @BindView(R.id.rg_screen_orientation)
    RadioGroup rg_screen_orientation;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.tv_read_options)
    TextView tv_read_options;
    @BindView(R.id.tv_close)
    TextView tv_close;
    @BindView(R.id.tv_read_share)
    TextView tv_read_share;
    @BindView(R.id.tv_read_comment)
    TextView tv_read_comment;
    @BindView(R.id.tv_detail)
    TextView tv_detail;

    @Inject
    ComicReadActivityPresenter mPresenter;

    private Handler mHandler=new Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==Constant.LOAD_MORE){
                if(isCallScroll){
                    Bundle data = msg.getData();
                    ComicRead comicRead = (ComicRead) data.getSerializable(Constant.COMIC_READ);
                    boolean is_load_top = data.getBoolean(Constant.IS_LOAD_TOP);
                    if(comicRead!=null){
                        comicChapterDealWith(comicRead,is_load_top);
                    }
                }else{
                    mHandler.sendMessage(msg);
                }
            }
            return false;
        }
    });

    public static void startActivity(Context context, String comic_id,
                                     String appoint_chapter_name) {
        Intent intent = new Intent(context, ComicReadActivity.class);
        intent.putExtra(Constant.COMIC_ID,comic_id);
        intent.putExtra(Constant.APPOINT_CHAPTER_NAME,appoint_chapter_name);
//        intent.putExtra(Constant.DATA_BEANS,dataBeans);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_sina){

        }else if(v.getId()==R.id.tv_qq){
            share();
        }else if(v.getId()==R.id.tv_friend_circle){

        }else if(v.getId()==R.id.tv_qq_zone){

        }else if(v.getId()==R.id.tv_wetchat){

        }else if(v.getId()==R.id.tv_save_album){

        }else if(v.getId()==R.id.tv_copy_url){

        }
    }

    @OnClick(R.id.tv_read_options)
    public void tv_read_options(){
        if(tv_read_options.getVisibility()==View.VISIBLE){
            tv_read_options.setVisibility(View.GONE);
            tv_read_share.setVisibility(View.INVISIBLE);
            tv_read_comment.setVisibility(View.GONE);

            tv_close.setVisibility(View.VISIBLE);
            tv_detail.setVisibility(View.VISIBLE);
            fl.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_read_share)
    public void tv_read_share(){
        if(popWindow==null){
            createPopupWindow();
            contentView.findViewById(R.id.tv_save_album).setVisibility(View.VISIBLE);
        }
        //设置遮罩
        ScreenUtils.setScreenBrightness(SettingManager.getInstance().getReadLightProgress()-20,this);

        popWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
    }

    @OnClick(R.id.tv_read_comment)
    public void tv_read_comment(){
        if(SettingManager.getInstance().getIsViewPointPattern()){
            PublishViewPointActivity.startActivity(this,comicId,currentChapterId);
        }else{

        }
    }

    @OnClick(R.id.tv_close)
    public void tv_close(){
        initBottom();
        fl.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        LogUtils.e("onTouch");
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            LogUtils.e("按下");
            x=0;
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            LogUtils.e("move");
            x++;
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            LogUtils.e("抬起"+x);
            if(x>=5){
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                LogUtils.e("firstVisibleItemPosition:"+firstVisibleItemPosition);
                ChapterReadBean bean=null;
                int myIndex =firstVisibleItemPosition  - mAdapter.getHeaderLayoutCount();
                if(myIndex>0&&myIndex<mAdapter.getData().size()){
                    bean=mAdapter.getItem(myIndex);
                }
                if(bean!=null&&!bean.page_url.equals("评论item")){
                    init(bean,bean.page_url);
                }
            }else{
                LogUtils.e("wwwwwwwwwww1"+isEnter);
                boolean b = ll_top.getVisibility() == View.GONE;
                LogUtils.e("wwwwwwwwwww2"+b);
                boolean c = ll_top.getVisibility() == View.GONE;
                LogUtils.e("wwwwwwwwwww3"+c);
                if(!isEnter&&(ll_top.getVisibility()==View.GONE
                        ||ll_bottom.getVisibility()==View.GONE)){
                    ll_top.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.VISIBLE);

                    initBottom();
                }else{
                    ll_top.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.GONE);
                    fl.setVisibility(View.GONE);

                    initBottom();
                }
            }
        }
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId()==R.id.tv_subscribe){

        }else if(view.getId()==R.id.tv_share){
            if(popWindow==null){
                createPopupWindow();
                contentView.findViewById(R.id.tv_save_album).setVisibility(View.INVISIBLE);
            }

            ScreenUtils.setScreenBrightness(SettingManager.getInstance().getReadLightProgress()-20,this);
            popWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
        }else if(view.getId()==R.id.tv_close_review){

        }else if(view.getId()==R.id.flowLayout){

        }else if(view.getId()==R.id.tv_more_review){
            PublishViewPointActivity.startActivity(this,comicId,currentChapterId);
        }else if(view.getId()==R.id.tv_publish){

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("item点击事件响应了"+position);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId()==R.id.seekBar){
            LogUtils.e("onProgressChanged-fromUser"+fromUser);
            chapterProgress=(seekBar.getProgress()+1)+"/" +(seekBar.getMax()+1);
            tv_read_progress.setText(chapterProgress+"页");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            tv_current_info.setText(chapterTitle+" "+chapterProgress+
                    " "+stateInfo+" "+simpleDateFormat.format(new Date()));

            tv_drag_read_progress.setText(chapterProgress);
        }else if(seekBar.getId()==R.id.seekBar_change_light){
            LogUtils.e("seekBar_change_light-fromUser"+fromUser);
            if(fromUser){
                checkbox.setChecked(false);
                ScreenUtils.setScreenBrightness(progress,this);
                SettingManager.getInstance().saveReadLightProgress(progress);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtils.e("onStartTrackingTouch");
        if(seekBar.getId()==R.id.seekBar){
            tv_drag_read_progress.setVisibility(View.VISIBLE);
            tv_drag_read_progress.setText(chapterProgress);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtils.e("onStopTrackingTouch");
        if(seekBar.getId()==R.id.seekBar){
            isCallScroll=true;
            List<ChapterReadBean> data = mAdapter.getData();
            int a=0;
            for(int i=0;i<data.size();i++){
                if(data.get(i).chapter_id.equals(currentChapterId)){
                    a=i;
                    break;
                }
            }
            moveToPosition(a+seekBar.getProgress()+1);
            chapterProgress=(seekBar.getProgress()+1)+"/" +(seekBar.getMax()+1);
            tv_read_progress.setText(chapterProgress+"页");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            tv_current_info.setText(chapterTitle+" "+chapterProgress+
                    " "+stateInfo+" "+simpleDateFormat.format(new Date()));

            tv_drag_read_progress.setVisibility(View.GONE);
            tv_drag_read_progress.setText(chapterProgress);
        }else if(seekBar.getId()==R.id.seekBar_change_light){
            ScreenUtils.setScreenBrightness(seekBar.getProgress(),this);
            SettingManager.getInstance().saveReadLightProgress(seekBar.getProgress());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.rb_auto_clip){

        }else if(checkedId==R.id.rb_close_clip){

        }else if(checkedId==R.id.rb_viewpoint_pattern){

        }else if(checkedId==R.id.rb_barrage_pattern){

        }else if(checkedId==R.id.rb_land_read){

        }else if(checkedId==R.id.rb_portrait_read){

        }
    }

    @Override
    public void onNetWorkStateChange(String stateInfo) {
        this.stateInfo=stateInfo;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        tv_current_info.setText(chapterTitle+" "+chapterProgress+
                " "+stateInfo+" "+simpleDateFormat.format(new Date()));
    }

    @Override
    public void onTimeChange() {
        LogUtils.e("时间改变了");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        tv_current_info.setText(chapterTitle+" "+chapterProgress+
                " "+stateInfo+" "+simpleDateFormat.format(new Date()));
    }

    @Override
    protected void loginComicHouse(TencentLoginResult result) {
        LogUtils.e("result:"+result);
        ToastUtils.showToast("分享成功");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        hideStatusBar();

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);

        comicId=getIntent().getStringExtra(Constant.COMIC_ID);
        appoint_chapter_name=getIntent().getStringExtra(Constant.APPOINT_CHAPTER_NAME);
//        data_beans= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>)
//                getIntent().getSerializableExtra(Constant.DATA_BEANS);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEmptyView(loddingView);
        mPresenter.getComicChapter(comicId);
    }

    @Override
    public void initDatas() {
        LogUtils.e("ComicReadActivity-initToolBar");
        mPresenter.attachView(this);

        stateInfo= MyNetWorkUtil.getNetInfo(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicReadAdapter.class,
                R.layout.item_comic_read,null,false,false);
        // 取消item点击事件,RecyclerView的onclick仍无效(滚动view本身可能无法响应onclick事件),改为根据onTouch判断
        ComicReadAdapter adapter = (ComicReadAdapter) mAdapter;
        adapter.setOnMyTagClickListener(new OnMyTagClickListener() {
            @Override
            public void tagClick(String hotViewId) {
                LogUtils.e("点赞了");
            }
        });
        mAdapter.setOnItemClickListener(null);
        mAdapter.setOnItemChildClickListener(this);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setOnTouchListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LogUtils.e("onScrollStateChanged-newState:"+newState);
                if(newState==0){
                    //自动滚动停止或没有自动滚动拖动抬起时newState==0
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    LogUtils.e("firstVisibleItemPosition:"+firstVisibleItemPosition);
                    ChapterReadBean bean=null;
                    int myIndex = firstVisibleItemPosition - mAdapter.getHeaderLayoutCount();
                    if(myIndex>0&&myIndex<mAdapter.getData().size()){
                        bean=mAdapter.getItem(myIndex);
                    }
                    if(bean!=null&&!bean.page_url.equals("评论item")){
                        init(bean,bean.page_url);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LogUtils.e("onScrolled");
                if(dy!=0&&(ll_top.getVisibility()==View.VISIBLE
                        ||ll_bottom.getVisibility()==View.VISIBLE)){
                    ll_top.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.GONE);
                    fl.setVisibility(View.GONE);
                }
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
//                tv_current_info.setText(chapterTitle+" "+chapterProgress+
//                        " "+stateInfo+" "+simpleDateFormat.format(new Date()));

                isCallScroll=false;
                LogUtils.e("onScrolled-dx:"+dx);
                LogUtils.e("onScrolled-dy:"+dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                LogUtils.e("findFirstVisibleItemPosition:"+layoutManager.findFirstVisibleItemPosition());
//                LogUtils.e("canScrollToTop:"+canScrollToTop);

                //在这里进行第二次滚动（最后的100米！）
                if (move){
                    Log.e("move",move+"");
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - layoutManager.findFirstVisibleItemPosition();
                    Log.e("n",n+"");
                    if ( 0 <= n && n < mRecyclerView.getChildCount()){
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = mRecyclerView.getChildAt(n).getTop();
                        //最后的移动
                        mRecyclerView.scrollBy(0, top);
                    }

                    LogUtils.e("eeeeeeeeeeeeeeeeee");
                    if(ll_top.getVisibility()==View.GONE
                            ||ll_bottom.getVisibility()==View.GONE){
                        ll_top.setVisibility(View.VISIBLE);
                        ll_bottom.setVisibility(View.VISIBLE);
                        if(tv_close.getVisibility()==View.VISIBLE){
                            fl.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if(layoutManager.findFirstVisibleItemPosition()==0&&canScrollToTop){
                    //滚动到header
                    canScrollToTop=false;
                    if(topPage+1<data_beans.size()){
                        LogUtils.e("触发加载上面更多");
                        topPage++;
                        currentLoadPage=topPage;
//                        mPresenter.getComicChapterDetail(comicId,data_beans.get(topPage).chapter_id,true);
                        mPresenter.getComicReadHotView(comicId,data_beans.get(currentLoadPage).chapter_id,true);
                        mPresenter.getComicReadViewPoint(comicId,data_beans.get(currentLoadPage).chapter_id,true);
                    }else{
                        LogUtils.e("上面没有更多了");
                        canScrollToTop=false;
                        header.loadMoreEnd();
                    }
                }else if(layoutManager.findLastVisibleItemPosition()==mAdapter.getData().size()+1&&canScrollToBottom){
                    //滚动到footer
                    LogUtils.e("findLastVisibleItemPosition:"+layoutManager.findLastVisibleItemPosition());
                    LogUtils.e(mAdapter.getData().size()+1+"ee");
                    canScrollToBottom=false;
                    if(bottomPage-1>=0){
                        ToastUtils.showToast("触发加载下面更多");
                        bottomPage--;
                        currentLoadPage=bottomPage;
//                        mPresenter.getComicChapterDetail(comicId,data_beans.get(bottomPage).chapter_id,false);
                        mPresenter.getComicReadHotView(comicId,data_beans.get(currentLoadPage).chapter_id,false);
                        mPresenter.getComicReadViewPoint(comicId,data_beans.get(currentLoadPage).chapter_id,false);
                    }else{
                        ToastUtils.showToast("下面没有更多了");
                        canScrollToBottom=false;
                        footer.loadMoreEnd();
                    }
                }
            }
        });

        hha();

        seekBar.setOnSeekBarChangeListener(this);
        seekBar_change_light.setOnSeekBarChangeListener(this);

        int readLightProgress = SettingManager.getInstance().getReadLightProgress();
        seekBar_change_light.setProgress(readLightProgress);
        ScreenUtils.setScreenBrightness(readLightProgress,this);

        checkbox.setChecked(SettingManager.getInstance().getIsSystemLight());
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.e("checkbox被调用了");
                if(isChecked){
                    SettingManager.getInstance().saveIsSystemLight(true);
                    seekBar_change_light.setProgress(seekBar_change_light.getMax());
                    ScreenUtils.setScreenBrightness(seekBar_change_light.getMax(),ComicReadActivity.this);
                    SettingManager.getInstance().saveReadLightProgress(seekBar_change_light.getMax());
                }else{
                    SettingManager.getInstance().saveIsSystemLight(false);
                }
            }
        });

        //设置网络改变监听
        NetworkChangeReceiver.getInstance().setOnNetWorkChangeListener(this);
        //设置时间改变监听
        TimeChangeReceiver.getInstance().setOnTimeChangeListener(this);

        onRefresh();
    }

    private void hha() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //ComicReadActivity实现不了OnAgainLoadListener(可能类实现不了自己的内部接口)
        OnAgainLoadListener onAgainLoadListener = new OnAgainLoadListener() {
            @Override
            public void loadAgain(View view) {
//                mPresenter.getComicChapterDetail(comicId,data_beans.get(currentLoadPage).chapter_id,false);
                header.loadMoreComplete();
                checkMap.remove(data_beans.get(currentLoadPage).chapter_id);
                if(view==header){
                    currentLoadPage=topPage;
                    mPresenter.getComicReadHotView(comicId,data_beans.get(currentLoadPage).chapter_id,true);
                    mPresenter.getComicReadViewPoint(comicId,data_beans.get(currentLoadPage).chapter_id,true);
                }else{
                    currentLoadPage=bottomPage;
                    mPresenter.getComicReadHotView(comicId,data_beans.get(currentLoadPage).chapter_id,false);
                    mPresenter.getComicReadViewPoint(comicId,data_beans.get(currentLoadPage).chapter_id,false);
                }

            }
        };
        header = new MyLoadMoreView(this);
        header.setOnAgainLoadListener(onAgainLoadListener);
        header.setLayoutParams(params);

        footer = new MyLoadMoreView(this);
        footer.setOnAgainLoadListener(onAgainLoadListener);
        footer.setLayoutParams(params);

        mAdapter.setHeaderView(header);
        mAdapter.setFooterView(footer);
        //这里如果隐藏的是header和footer这两个View之后显示不出(不知原因)
        mAdapter.getHeaderLayout().setVisibility(View.GONE);
        mAdapter.getFooterLayout().setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void showError(boolean isLoadTop) {
        if(!NetworkUtils.isAvailable(this)){
            if(isLoadTop){
                canScrollToTop=false;
                header.loadMoreFail();
            }else{
                canScrollToBottom=false;
                footer.loadMoreFail();
            }
        }
    }

    @Override
    public void complete() {

    }

    @Override
    public void showComicChapter(ArrayList<ComicDetailHeader.ChaptersBean.DataBean> list) {
        LogUtils.e("showComicChapter");
        if(list!=null&&!list.isEmpty()){
            data_beans=list;

            String readProgress = SettingManager.getInstance().getReadProgress(comicId);
            int chapterIndex=0;
            LogUtils.e("readProgress:"+readProgress);
            LogUtils.e("appoint_chapter_name:"+appoint_chapter_name);
            //阅读记录不为空
            if(!TextUtils.isEmpty(readProgress)){
                String[] split = readProgress.split("-");
                //指定阅读章节与阅读记录是相同章节或没指定阅读章节,读取阅读记录
                if(TextUtils.isEmpty(appoint_chapter_name)
                        ||split[0].equals(appoint_chapter_name)){
                    for(int i=0;i<data_beans.size();i++){
                        LogUtils.e("chapter_title:"+data_beans.get(i).chapter_title);
                        if(data_beans.get(i).chapter_title.equals(split[0])){
                            chapterIndex=i;
                            break;
                        }
                    }
                    oldPageIndex=Integer.parseInt(split[1]);//页面索引记录
                }else{
                    //指定阅读章节与阅读记录不是相同章节
                    for(int i=0;i<data_beans.size();i++){
                        if(data_beans.get(i).chapter_title.equals(appoint_chapter_name)){
                            chapterIndex=i;
                            break;
                        }
                    }
                }
            }else if(!TextUtils.isEmpty(appoint_chapter_name)){
                //无阅读记录且有指定阅读章节
                for(int i=0;i<data_beans.size();i++){
                    if(data_beans.get(i).chapter_title.equals(appoint_chapter_name)){
                        chapterIndex=i;
                        break;
                    }
                }
            }
            currentLoadPage=chapterIndex;//章节记录
            LogUtils.e("currentLoadPage:"+currentLoadPage);
            LogUtils.e("oldPageIndex:"+oldPageIndex);
            topPage=currentLoadPage;
            bottomPage=currentLoadPage;

            //以防网络错误一个刷新完成另一个未完成导致下次请求完成一个就请求章节详情
            checkMap.remove(data_beans.get(currentLoadPage).chapter_id);
            mPresenter.getComicReadHotView(comicId,data_beans.get(currentLoadPage).chapter_id,false);
            mPresenter.getComicReadViewPoint(comicId,data_beans.get(currentLoadPage).chapter_id,false);
        }else{
            ToastUtils.showToast("没有章节可阅读");
        }
    }

    @Override
    public void showComicChapterDetail(ComicRead comicRead,boolean isLoadTop) {
        LogUtils.e("showComicChapterDetail");

        if(!isCallScroll){
            comicChapterDealWith(comicRead,isLoadTop);
        }else{
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.COMIC_READ,comicRead);
            bundle.putBoolean(Constant.IS_LOAD_TOP,isLoadTop);
            message.setData(bundle);
            message.what=Constant.LOAD_MORE;
            mHandler.sendMessage(message);
        }
    }

    private void comicChapterDealWith(ComicRead comicRead,boolean isLoadTop) {
        LogUtils.e("comicChapterDealWith");
        ArrayList<ChapterReadBean> beans = new ArrayList<>();
        if(comicRead.page_url!=null){
            LogUtils.e("comicRead.page_url:"+comicRead.page_url);
            LogUtils.e("currentLoadPage:"+currentLoadPage);
            for(int i=0;i<=comicRead.page_url.size();i++){
                ChapterReadBean bean = new ChapterReadBean();
                if(i!=comicRead.page_url.size()){
                    bean.title=comicRead.title;
                    bean.picnum=comicRead.picnum;
                    bean.page_url=comicRead.page_url.get(i);
                    bean.chapter_id=comicRead.chapter_id;
                }else{
                    bean.title=comicRead.title;
                    bean.picnum=comicRead.picnum;
                    bean.page_url="评论item";
                    bean.chapter_id=comicRead.chapter_id;
                    bean.mHotViews=mHotViewListMap.get(comicRead.chapter_id);
                    LogUtils.e("comicRead-chapter_id:"+comicRead.chapter_id);
                    LogUtils.e("comicChapterDealWith-mHotViews:"+bean.mHotViews);
                }
                beans.add(bean);
            }
        }

        if(isEnter){
            LogUtils.e("刷新界面"+beans.size());
            if(!beans.isEmpty()){
                isEnter=false;

                ChapterReadBean bean = beans.get(oldPageIndex);
                init(bean,bean.page_url);

//                hha();
            }else{
                ToastUtils.showToast("刷新数据为空");
            }
            canScrollToTop=true;
            canScrollToBottom=true;
            LogUtils.e("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
            mAdapter.setEmptyView(inflate);
            mAdapter.setNewData(beans);
            //滚动通知内部数据已改变然后requestLayout,不是平滑滚动而是直接到达指定item
            if(oldPageIndex==0){
//                if(mRecyclerView.getChildAt(0)==mAdapter.getEmptyView()){
//                    LogUtils.e("emptyView");//最开始在RecyclerView上的是emptyView
//                }else if(mRecyclerView.getChildAt(0)==header){
//                    LogUtils.e("header");
//                }
                mRecyclerView.scrollBy(0,mRecyclerView.getChildAt(1).getTop());//即时调用滚动监听
                LogUtils.e("qqqqqqqqqqqqq");
            }else{
                moveToPosition(oldPageIndex+1);
            }
        }else if(beans.isEmpty()){
            ToastUtils.showToast("下拉数据为空");
        }else{
            ChapterReadBean bean = beans.get(0);
            if(isLoadTop){
                LogUtils.e("top");
                mIndex=beans.size()+1;
                mRecyclerView.scrollToPosition(beans.size()+1);
                mAdapter.addData(0,beans);
                canScrollToTop=true;
                header.loadMoreComplete();
                init(bean,comicRead.page_url.get(comicRead.page_url.size()));
            }else{
                LogUtils.e("bottom");
                mAdapter.addData(beans);
                canScrollToBottom=true;
                footer.loadMoreComplete();
                init(bean,bean.page_url);
            }
        }

        mAdapter.getHeaderLayout().setVisibility(View.VISIBLE);
        mAdapter.getFooterLayout().setVisibility(View.VISIBLE);
    }

    private void init(ChapterReadBean bean,String page_url) {
        tv_title.setText(bean.title);
        String number = StringUtils.getNumberByIndex(page_url, page_url.lastIndexOf("/"));
        seekBar.setMax(Integer.parseInt(bean.picnum));
        seekBar.setProgress(Integer.parseInt(number));
        chapterProgress=(Integer.parseInt(number)+1)+"/"
                +(Integer.parseInt(bean.picnum) + 1);
        tv_read_progress.setText(chapterProgress+"页");
        currentChapterId=bean.chapter_id;

        chapterTitle=bean.title;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        tv_current_info.setText(chapterTitle+" "+chapterProgress+
                " "+stateInfo+" "+simpleDateFormat.format(new Date()));

        List<ComicReadViewPoint> comicReadViewPoints = mViewPointListMap.get(currentChapterId);
        if(comicReadViewPoints!=null&&!comicReadViewPoints.isEmpty()){
            int commentCount=0;
            for(int i=0;i<comicReadViewPoints.size();i++){
                commentCount+=Integer.parseInt(comicReadViewPoints.get(i).num);
            }
            tv_read_comment.setText("吐槽("+commentCount+")");
        }else{
            tv_read_comment.setText("吐槽("+0+")");
        }
    }

    private void initBottom() {
        tv_read_options.setVisibility(View.VISIBLE);
        tv_read_share.setVisibility(View.VISIBLE);
        tv_read_comment.setVisibility(View.VISIBLE);

        tv_close.setVisibility(View.GONE);
        tv_detail.setVisibility(View.GONE);
    }

    @Override
    public void showComicReadHotView(List<ComicReadHotView> list,String chapterId,boolean isLoadTop) {
        LogUtils.e("showComicReadHotView-list:"+list);
        if(list!=null&&!list.isEmpty()){
            mHotViewListMap.put(chapterId,list);
        }
        if(checkMap.get(chapterId)==null){
            checkMap.put(chapterId,0);
        }else if(checkMap.get(chapterId)==0){
            checkMap.put(chapterId,1);
            mPresenter.getComicChapterDetail(comicId,data_beans.get(currentLoadPage).chapter_id,isLoadTop);
        }
    }

    @Override
    public void showComicReadViewPoint(List<ComicReadViewPoint> list, String chapterId, boolean isLoadTop) {
        LogUtils.e("showComicReadViewPoint-list:"+list);
        if(list!=null&&!list.isEmpty()){
            mViewPointListMap.put(chapterId,list);
        }
        if(checkMap.get(chapterId)==null){
            checkMap.put(chapterId,0);
        }else if(checkMap.get(chapterId)==0){
            checkMap.put(chapterId,1);
            mPresenter.getComicChapterDetail(comicId,data_beans.get(currentLoadPage).chapter_id,isLoadTop);
        }
    }

    private boolean move=false;
    private int mIndex=0;

    private void moveToPosition(int n) {
        mIndex=n;
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            Log.e("搜索","vvvv");
            move = true;
        }
    }

    private CustomPopWindow popWindow;
    private View contentView;

    private void createPopupWindow() {
        contentView = View.inflate(mContext, R.layout.view_read_bottom_share_popupwindow, null);
        contentView.findViewById(R.id.tv_sina).setOnClickListener(this);
        contentView.findViewById(R.id.tv_qq).setOnClickListener(this);
        contentView.findViewById(R.id.tv_friend_circle).setOnClickListener(this);
        contentView.findViewById(R.id.tv_qq_zone).setOnClickListener(this);
        contentView.findViewById(R.id.tv_wetchat).setOnClickListener(this);
        contentView.findViewById(R.id.tv_save_album).setOnClickListener(this);
        contentView.findViewById(R.id.tv_copy_url).setOnClickListener(this);

        popWindow= PopWindowUtil.createPopupWindow(this,contentView,Constant.COMIC_READ_LIGHT);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constant.RETURN_DATA){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!isEnter){
            //保存阅读进度
            String substring = chapterTitle.substring(1, chapterTitle.length());
            char[] chars = substring.toCharArray();
            if("0".equals(chars[0]+"")&&Character.isDigit(chars[1])){
                substring=substring.substring(1,substring.length());
            }
            String readProgress=substring+"-"+seekBar.getProgress();

            LogUtils.e("chapterTitle:"+chapterTitle);
            LogUtils.e("onPause-readProgress:"+readProgress);
            SettingManager.getInstance().saveReadProgress(comicId,readProgress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mHandler.removeCallbacksAndMessages(null);
        NetworkChangeReceiver.getInstance().delOnNetWorkChange(this);
        TimeChangeReceiver.getInstance().delOnNetTimeChangeListener(this);
    }

    public interface OnAgainLoadListener{
        void loadAgain(View view);
    }

    public interface OnMyTagClickListener{
        void tagClick(String hotViewId);
    }

}
