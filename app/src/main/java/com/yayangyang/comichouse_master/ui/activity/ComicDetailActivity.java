package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.Announcement;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.IsHelpful;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.GlideApp;
import com.yayangyang.comichouse_master.base.BaseRVActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.decoration.CommonSpaceItemDecoration;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicChapterAdapter;
import com.yayangyang.comichouse_master.ui.adapter.ComicDetailAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicDetailContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicDetailActivityPresenter;
import com.yayangyang.comichouse_master.utils.FormatUtils;
import com.yayangyang.comichouse_master.utils.GlideUtil;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.LoginUtil;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicDetailActivity extends BaseRVActivity<ComicDetailBody,BaseViewHolder>
        implements ComicDetailContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener{

    private int myPage=1;

    private String comicId;
    private String title;
    private String shortDescription,description;

    private HeaderViewHolder headerViewHolder;
    private AnnouncementHeaderViewHolder aHeaderViewHolder;

    private ComicChapterAdapter comicChapterAdapter;

    private List list=null;

    private RadioButton rb_like_amount;
//    private ImageView iv_like;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.myll)
    LinearLayout myll;
    @BindView(R.id.edit_publish)
    EditText edit_publish;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.tv_message)
    TextView tv_message;

    @Inject
    ComicDetailActivityPresenter mPresenter;

    public static void startActivity(Context context, String comic_id,String title) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(Constant.COMIC_ID,comic_id);
        intent.putExtra(Constant.TITLE,title);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("position:"+position);
        if(view.getId()==R.id.rb_like_amount){
            if(NetworkUtils.isAvailable(this)){
                ComicDetailBody comicDetailBody = (ComicDetailBody) adapter.getData().get(position);
                float aFloat = Float.parseFloat(comicDetailBody.obj_id);
                String obj_id=(int)aFloat+"";
                aFloat = Float.parseFloat(comicDetailBody.id);
                String id=(int)aFloat+"";
                if(!SettingManager.getInstance().getIsHelpful(obj_id+","+id)){
                    rb_like_amount = (RadioButton) adapter
                            .getViewByPosition(position+adapter.getHeaderLayoutCount(),R.id.rb_like_amount);
//                    iv_like = (ImageView) adapter
//                            .getViewByPosition(position+adapter.getHeaderLayoutCount(),R.id.iv_like);
//                    iv_like.setEnabled(false);
                    rb_like_amount.setEnabled(false);

                    mPresenter.getIsHelpful(obj_id,id,0+"");
                }else{
                    ToastUtils.showToast("已经点过赞喽!");
                }
            }else{
                ToastUtils.showToast("没有网络");
            }
        }else if(view.getId()==R.id.iv_cover){

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_download) {
            LogUtils.e("点击了下载");
        }else if (v.getId() == R.id.tv_subscribe_choice) {

        }else if (v.getId() == R.id.tv_read_state) {

        }else if (v.getId() == R.id.iv_switch) {
            if (headerViewHolder.tv_description.getText().length() <= 30) {
                headerViewHolder.tv_description.setText(description);
                headerViewHolder.iv_switch.setImageDrawable(getResources().getDrawable(R.drawable.img_close_btn));
            } else {
                headerViewHolder.tv_description.setText(shortDescription+"...");
                headerViewHolder.iv_switch.setImageDrawable(getResources().getDrawable(R.drawable.img_open_btn));
            }
        }else if (v.getId() == R.id.tv_relevant_content) {

        }else if (v.getId() == R.id.tv_share) {

        }else if (v.getId() == R.id.tv_switch) {
            if(aHeaderViewHolder.tv_content.getMaxLines()==4){
                aHeaderViewHolder.tv_content.setMaxLines(10);
                aHeaderViewHolder.tv_switch.setText("收起");
            }else{
                aHeaderViewHolder.tv_content.setMaxLines(4);
                aHeaderViewHolder.tv_switch.setText("显示全部");
            }
        }else if (v.getId() == R.id.edit_publish) {
            LogUtils.e("点击了editText");
            if(!LoginUtil.isLogin()){
                LoginActivity.startActivity(this);
            }else{
                PublishReviewActivity.startActivity(this,comicId);
            }

        }else if (v.getId() == R.id.iv_message) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        LogUtils.e("ComicDetailActivity-initToolBar");
        comicId=getIntent().getStringExtra(Constant.COMIC_ID);
        title=getIntent().getStringExtra(Constant.TITLE);
        LogUtils.e("initToolBar-comicId:"+comicId);

        tv_title.setText(title);
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        myPage=1;
        mPresenter.getComicDetailHeader(comicId);
//        mPresenter.getAnnouncement(comicId);
//        mPresenter.getComicDetailBody(comicId,myPage);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComicDetailBody(comicId,myPage);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);

        View headerView = View.inflate(this, R.layout.header_comic_detail, null);
        headerViewHolder = new HeaderViewHolder(headerView);
        comicChapterAdapter = new ComicChapterAdapter(R.layout.item_comic_chapter, null);
        comicChapterAdapter.setOnItemChildClickListener(this);
        headerViewHolder.rv_chapter.setLayoutManager(new GridLayoutManager(this,4));
        headerViewHolder.rv_chapter.addItemDecoration(new CommonSpaceItemDecoration(ScreenUtils.dpToPxInt(5)));
        headerViewHolder.rv_chapter.setAdapter(comicChapterAdapter);

        headerViewHolder.iv_switch.setOnClickListener(this);
        headerViewHolder.rg_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(list.size()<=11){
                    comicChapterAdapter.setNewData(list);
                }else{
                    List<ComicDetailHeader.ChaptersBean.DataBean> dataBeans = null;
                    if(checkedId==R.id.rb_positive){
                        dataBeans=list.subList(0, 11);
                    }else{
                        dataBeans=list.subList(list.size()-11,list.size());
                    }
                    ComicDetailHeader.ChaptersBean.DataBean dataBean = new ComicDetailHeader.ChaptersBean.DataBean();
                    dataBean.chapter_title="...";
                    dataBeans.add(dataBean);
                    comicChapterAdapter.setNewData(dataBeans);
                }
            }
        });

        View announcementHeaderView = View.inflate(this, R.layout.header_comic_detail_announcement, null);
        aHeaderViewHolder = new AnnouncementHeaderViewHolder(announcementHeaderView);
        aHeaderViewHolder.tv_switch.setOnClickListener(this);
    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicDetailAdapter.class,
                R.layout.item_comic_detail,null,true,true);
        mAdapter.setOnItemChildClickListener(this);
        onRefresh();
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
    public void showComicDetailHeader(ComicDetailHeader comicDetail) {
        LogUtils.e("ComicDetailActivity-showComicDetailHeader");
//        mAdapter.setEmptyView(inflate);

        GlideUrl cookie = new GlideUrl(comicDetail.cover, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getRoundCornerRequestOptions())
                .into(headerViewHolder.iv_cover);

        String authors="";
        for(int i=0;i<comicDetail.authors.size();i++){
            authors+=comicDetail.authors.get(i).tag_name+" ";
        }
        String types="";
        for(int i=0;i<comicDetail.types.size();i++){
            types+=comicDetail.types.get(i).tag_name+" ";
        }
        String status="";
        for(int i=0;i<comicDetail.status.size();i++){
            status+=comicDetail.status.get(i).tag_name+" ";
        }
        String dateString = FormatUtils.getStringByTimeStamp("MM月dd日", comicDetail.last_updatetime);
        headerViewHolder.tv_author.setText(authors);
        headerViewHolder.tv_type.setText(types);
        headerViewHolder.tv_subscribe.setText("订阅 "+comicDetail.subscribe_num);
        headerViewHolder.tv_status.setText("人气 "+comicDetail.hot_num);
        headerViewHolder.tv_date.setText(dateString+" "+status);
        headerViewHolder.tv_subscribe_choice.setOnClickListener(this);
        headerViewHolder.tv_read_state.setOnClickListener(this);
        if(comicDetail.description.length()>30){
            shortDescription=comicDetail.description.substring(0,30);
        }else{
            shortDescription=comicDetail.description;
        }
        description=comicDetail.description;
        headerViewHolder.tv_description.setText(shortDescription+"...");
        headerViewHolder.tv_relevant_content.setOnClickListener(this);
        headerViewHolder.tv_share.setOnClickListener(this);

        list=comicDetail.chapters.get(0).data;
        LogUtils.e("data.size():"+comicDetail.chapters.get(0).data.size());
        if(comicDetail.chapters.get(0).data.size()<=11){
            comicChapterAdapter.setNewData(comicDetail.chapters.get(0).data);
        }else{
            List<ComicDetailHeader.ChaptersBean.DataBean> dataBeans = comicDetail.chapters.get(0).data.subList(0, 11);
            ComicDetailHeader.ChaptersBean.DataBean dataBean = new ComicDetailHeader.ChaptersBean.DataBean();
            dataBean.chapter_title="...";
            dataBeans.add(dataBean);
            comicChapterAdapter.setNewData(dataBeans);
        }
        if(mAdapter.getHeaderLayout()==null){
            mAdapter.setHeaderView(headerViewHolder.view,0);
            myll.setVisibility(View.VISIBLE);
            edit_publish.setOnClickListener(this);
            iv_message.setOnClickListener(this);
            tv_message.setText(comicDetail.comment.comment_count);

            mPresenter.getAnnouncement(comicId);
        }
        mPresenter.getComicDetailBody(comicId,myPage);
    }

    @Override
    public void showComicDetailBody(List<ComicDetailBody> list, int page) {
        LogUtils.e("showComicDetailBody");
        boolean isRefresh = page == 1;
        if(isRefresh){
            LogUtils.e("刷新");
            myPage=1;
            mAdapter.getData().clear();
            mAdapter.setEmptyView(inflate);
//            mRecyclerView.scrollToPosition(0);
            mAdapter.setNewData(list);
            myPage++;
        }else if(list==null||list.isEmpty()){
            LogUtils.e("loadMoreEnd");
            mAdapter.loadMoreEnd();
            LogUtils.e("loadMoreEnd");
        }else{
            LogUtils.e("loadMoreComplete"+list.size());
            mAdapter.loadMoreComplete();
            mAdapter.addData(list);
            myPage++;
        }
    }

    @Override
    public void showAnnouncement(Announcement announcement) {
        GlideUrl cookie = new GlideUrl(announcement.avatar_url, new LazyHeaders.Builder()
                .addHeader("Referer", Constant.IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
        GlideApp.with(mContext).load(cookie)
                .apply(GlideUtil.getCircleCornerRequestOptions())
                .into(aHeaderViewHolder.iv_cover);

        aHeaderViewHolder.tv_nickname.setText(announcement.nickname);
        if("1".equals(announcement.sex)){
            aHeaderViewHolder.iv_sender.setImageDrawable(
                    mContext.getResources().getDrawable(R.drawable.img_newcomment_boy));
        }else{
            aHeaderViewHolder.iv_sender.setImageDrawable(
                    mContext.getResources().getDrawable(R.drawable.img_newcomment_girl));
        }
        aHeaderViewHolder.tv_content.setText(announcement.content);
        String dateString = FormatUtils.getStringByTimeStamp("yyyy-MM-dd", announcement.create_time);
        aHeaderViewHolder.tv_date.setText(dateString);

        mAdapter.addHeaderView(aHeaderViewHolder.view);
    }

    @Override
    public void showIsHelpful(IsHelpful helpful, String obj_id, String comment_id) {
        if(helpful.code.equals("0")){
            rb_like_amount.setChecked(true);
            rb_like_amount.setText(Integer.parseInt(rb_like_amount.getText().toString())+1+"");

            SettingManager.getInstance().saveHelpful(obj_id+","+comment_id,true);
        }
        rb_like_amount.setEnabled(true);
//        iv_like.setEnabled(true);
        ToastUtils.showToast(helpful.msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Constant.RETURN_DATA){
            ComicDetailBody comicDetailBody = (ComicDetailBody) data.getSerializableExtra(Constant.COMIC_DETAIL_BODY);
            mAdapter.addData(0,comicDetailBody);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    static class AnnouncementHeaderViewHolder{
        @BindView(R.id.iv_cover)
        ImageView iv_cover;
        @BindView(R.id.tv_nickname)
        TextView tv_nickname;
        @BindView(R.id.iv_sender)
        ImageView iv_sender;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_switch)
        TextView tv_switch;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.iv_announcement)
        ImageView iv_announcement;

        public View view;

        public AnnouncementHeaderViewHolder(View view) {
            this.view=view;
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderViewHolder {
        @BindView(R.id.iv_cover)
        ImageView iv_cover;
        @BindView(R.id.tv_author)
        TextView tv_author;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_subscribe)
        TextView tv_subscribe;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_subscribe_choice)
        TextView tv_subscribe_choice;
        @BindView(R.id.tv_read_state)
        TextView tv_read_state;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_relevant_content)
        TextView tv_relevant_content;
        @BindView(R.id.tv_share)
        TextView tv_share;
        @BindView(R.id.iv_switch)
        ImageView iv_switch;
        @BindView(R.id.rg_group)
        RadioGroup rg_group;
        @BindView(R.id.rb_works_introduce)
        RadioButton rb_works_introduce;
        @BindView(R.id.rb_works_announcement)
        RadioButton rb_works_announcement;
        @BindView(R.id.rb_author_announcement)
        RadioButton rb_author_announcement;
        @BindView(R.id.rg_order)
        RadioGroup rg_order;
        @BindView(R.id.rb_positive)
        RadioButton rb_positive;
        @BindView(R.id.rb_reverse)
        RadioButton rb_reverse;
        @BindView(R.id.rv_chapter)
        RecyclerView rv_chapter;

        public View view;

        public HeaderViewHolder(View view) {
            this.view=view;
            ButterKnife.bind(this, view);
        }
    }

}
