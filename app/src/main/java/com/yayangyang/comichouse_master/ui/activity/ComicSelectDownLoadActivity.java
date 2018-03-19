package com.yayangyang.comichouse_master.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.HotSearch;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.ui.adapter.ComicSelectDownLoadAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ComicSelectDownLoadActivity extends BaseActivity
        implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener{

    private String title,cover,comicId;

    private ArrayList<ComicDetailHeader.ChaptersBean.DataBean> list,downLoadList;

    private ComicSelectDownLoadAdapter mAdapter;

    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.tv_chapter_size)
    TextView tv_chapter_size;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static void startActivity(Context context,String title,String cover, String comicId,
                                     List<ComicDetailHeader.ChaptersBean.DataBean> list) {
        Intent intent = new Intent(context, ComicSelectDownLoadActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.COVER,cover);
        intent.putExtra(Constant.COMIC_ID,comicId);
        intent.putExtra(Constant.DATA_BEANS,(ArrayList)list);
        context.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicDetailHeader.ChaptersBean.DataBean dataBean =
                (ComicDetailHeader.ChaptersBean.DataBean) adapter.getData().get(position);
        dataBean.setSelected(!dataBean.isSelected());
        view.setSelected(!view.isSelected());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showToast("点击了item");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_all){
            List<ComicDetailHeader.ChaptersBean.DataBean> dataList = mAdapter.getData();
            if(tv_all.getText().toString().equals("全选")){
                tv_all.setText("反选");
                for(int i=0;i<dataList.size();i++){
                    dataList.get(i).setSelected(true);
                }
            }else{
                tv_all.setText("全选");
                for(int i=0;i<dataList.size();i++){
                    dataList.get(i).setSelected(!dataList.get(i).isSelected());
                }
            }
            mAdapter.notifyDataSetChanged();
        }else if(v.getId()==R.id.tv_download){
            List<ComicDetailHeader.ChaptersBean.DataBean> dataList = mAdapter.getData();
            List<ComicChapterDownLoadInfo> comicChapterDownLoadInfo =
                    SettingManager.getInstance().getComicChapterDownLoadInfo(comicId);
            downLoadList=new ArrayList<>();

            for(int i=0;i<dataList.size();i++){
                boolean isSelected=true,isClickAble=true;
                if(!dataList.get(i).isSelected()){
                    isSelected=false;
                }
                if(comicChapterDownLoadInfo!=null){
                    for(int j=0;j<comicChapterDownLoadInfo.size();j++){
                        if(comicChapterDownLoadInfo.get(j).getUrl().contains(dataList.get(i).chapter_id)){
                            isClickAble=false;
                            break;
                        }
                    }
                }
                if(isSelected&&isClickAble){
                    downLoadList.add(dataList.get(i));
                }
            }

            if(!downLoadList.isEmpty()){
                ToastUtils.showToast("开始下载:"+downLoadList);

                List<String> allComicDownLoadId = SettingManager.getInstance().getAllComicDownLoadId();
                if(allComicDownLoadId==null) allComicDownLoadId=new ArrayList<>();
                if(!allComicDownLoadId.contains(comicId)){
                    allComicDownLoadId.add(comicId);
                    SettingManager.getInstance().saveAllComicDownLoadId(allComicDownLoadId);
                }

                ComicDownLoadActivity.startActivity(this,title,cover,comicId,downLoadList,list);
            }else{
                ToastUtils.showToast("请选择要下载章节");
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_select_download;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        LogUtils.e("ComicSelectDownLoadActivity-initToolBar");
        Intent intent = getIntent();
        title=intent.getStringExtra(Constant.TITLE);
        cover=intent.getStringExtra(Constant.COVER);
        comicId=intent.getStringExtra(Constant.COMIC_ID);
        list= (ArrayList<ComicDetailHeader.ChaptersBean.DataBean>) intent.getSerializableExtra(Constant.DATA_BEANS);

        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        tv_all.setOnClickListener(this);
        tv_download.setOnClickListener(this);
        if(list!=null) tv_chapter_size.setText(list.size()+"个章节");

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mAdapter = new ComicSelectDownLoadAdapter(R.layout.item_comic_chapter_download, list);
        mAdapter.setComicId(comicId);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        LogUtils.e("ComicSelectDownLoadActivity-onResume");
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("ComicSelectDownLoadActivity-onDestroy");
        super.onDestroy();
    }
}
