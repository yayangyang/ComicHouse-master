package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhouwei.library.CustomPopWindow;
import com.yayangyang.comichouse_master.Bean.ComicRecommend;
import com.yayangyang.comichouse_master.Bean.user.ComicUpdate;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseRVFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.decoration.SpaceItemDecoration;
import com.yayangyang.comichouse_master.ui.activity.ComicDetailActivity;
import com.yayangyang.comichouse_master.ui.adapter.ComicUpdateAdapter;
import com.yayangyang.comichouse_master.ui.contract.ComicUpdateContract;
import com.yayangyang.comichouse_master.ui.presenter.ComicUpdatePresenter;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/28.
 */

public class ComicUpdateFragment extends BaseRVFragment<ComicUpdatePresenter,ComicUpdate,BaseViewHolder>
        implements ComicUpdateContract.View,BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener {

    private CustomPopWindow popWindow;
    private Button bt_all_comic,bt_original_comic,bt_dubbing_comic;

    private int currentType= Constant.ALL_COMIC;

    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.tv_all_comic)
    TextView tv_all_comic;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ComicUpdate comicUpdate = (ComicUpdate) adapter.getData().get(position);
        ComicDetailActivity.startActivity(getActivity(),comicUpdate.id,comicUpdate.title);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onClick(View v) {
        bt_all_comic.setBackgroundColor(getResources().getColor(R.color.common_bg));
        bt_original_comic.setBackgroundColor(getResources().getColor(R.color.common_bg));
        bt_dubbing_comic.setBackgroundColor(getResources().getColor(R.color.common_bg));

        if(v.getId()==R.id.bt_all_comic){
            bt_all_comic.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            currentType=Constant.ALL_COMIC;
        }else if(v.getId()==R.id.bt_original_comic){
            bt_original_comic.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            currentType=Constant.ORIGINAL_COMIC;
        }else if(v.getId()==R.id.bt_dubbing_comic){
            bt_dubbing_comic.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            currentType=Constant.DUBBING_COMIC;
        }
        popWindow.dissmiss();
        mPresenter.getComicUpdateList(currentType,0);
    }

    @Override
    public int getLayoutResId() {
        LogUtils.e("fragemnet-getLayoutResId");
        return R.layout.fragment_comic_update;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getComicUpdateList(currentType,page);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComicUpdateList(currentType,page);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");
        initAdapter(ComicUpdateAdapter.class,
                R.layout.item_comic_update_linear,null,true,true);
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
    public void showComicUpdateList(List<ComicUpdate> list,int page) {
        LogUtils.e("showComicUpdateList");
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

    @OnClick(R.id.tv_all_comic)
    public void tv_all_comic(View view){
        view.setSelected(true);
        if(popWindow==null){
            LogUtils.e("为空");
            showPopupwindow(rl_bar);
        }else{
            LogUtils.e("不为空");
            popWindow.showAsDropDown(rl_bar);
        }
    }

    @OnClick(R.id.iv_change_layout)
    public void iv_change_layout(ImageView view){
        if(mRecyclerView.getLayoutManager() instanceof GridLayoutManager){
            LogUtils.e("GridLayoutManager-LinearLayoutManager");
            view.setImageResource(R.drawable.img_list_style_list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            LogUtils.e("LinearLayoutManager-GridLayoutManager");
            view.setImageResource(R.drawable.img_list_style_grid);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }
    }

    public void showPopupwindow(View view){
        View contentView = View.inflate(getActivity(), R.layout.view_comic_update_popupwindow, null);
        bt_all_comic = contentView.findViewById(R.id.bt_all_comic);
        bt_all_comic.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bt_original_comic = contentView.findViewById(R.id.bt_original_comic);
        bt_dubbing_comic = contentView.findViewById(R.id.bt_dubbing_comic);
        bt_all_comic.setOnClickListener(this);
        bt_original_comic.setOnClickListener(this);
        bt_dubbing_comic.setOnClickListener(this);
        popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_all_comic.setSelected(false);
                    }
                })
                .create()//创建PopupWindow
                .showAsDropDown(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
