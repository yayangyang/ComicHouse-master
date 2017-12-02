package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.yayangyang.comichouse_master.Bean.ComicRankComicTypeBean;
import com.yayangyang.comichouse_master.Bean.ComicRankDateTypeBean;
import com.yayangyang.comichouse_master.Bean.ComicRankItemBean;
import com.yayangyang.comichouse_master.Bean.support.SelectionEvent;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.ui.adapter.ComicRankPopupWindowAdapter;
import com.yayangyang.comichouse_master.ui.fragment.MineFragment;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ComicRankFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener{

    private CustomPopWindow popWindow;

    private Fragment[] mFragments=new Fragment[3];
    private ArrayList<ComicRankItemBean> categoryArrayList=new ArrayList<ComicRankItemBean>();
    private ArrayList<ComicRankItemBean> dateArrayList=new ArrayList<ComicRankItemBean>();

    private int index=0;

    private int currentComicType[]= new int[]{Constant.ComicType.ALL,Constant.ComicType.ALL,Constant.ComicType.ALL};
    private int currentDate[]= new int[]{Constant.DateType.DAY,Constant.DateType.DAY,Constant.DateType.DAY};
    private int currentRankType[]= new int[]{Constant.POPULAR_RANK,Constant.REVIEW_RANK,Constant.SUSCRIBE_RANK};

    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_comic_type)
    TextView tv_comic_type;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ComicRankItemBean bean = ((ComicRankPopupWindowAdapter) adapter).getData().get(position);
        if(!bean.isDate){
            LogUtils.e("currentComicType",bean.type);
            currentComicType[index]=bean.type;
            tv_comic_type.setText(Constant.comicTypeMap.get(currentComicType[index]));
        }else{
            LogUtils.e("currentDate",bean.type);
            currentDate[index]=bean.type;
        }
        tv_date.setText(Constant.dateTypeMap.get(currentDate[index]));
        EventBus.getDefault().post(new SelectionEvent(index,currentComicType[index],currentDate[index],currentRankType[index]));
        popWindow.dissmiss();
        LogUtils.e("onItemClick");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        LogUtils.e("configViews");

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.tv_rank_popular){
                    ToastUtils.showToast("rank_popular");
                    changeFragment(0);
                }else if(checkedId==R.id.tv_rank_review){
                    ToastUtils.showToast("rank_review");
                    changeFragment(1);
                }else if(checkedId==R.id.tv_rank_subscribe){
                    ToastUtils.showToast("rank_subscribe");
                    changeFragment(2);
                }
            }
        });
        changeFragment(0);
    }

    @OnClick(R.id.tv_category)
    public void tv_category(View view){
        if(popWindow==null){
            LogUtils.e("为空");
            getData();
            showPopupwindow(rl_bar);
        }else{
            LogUtils.e("不为空");
            popWindow.showAsDropDown(rl_bar);
            initPopUpWindow();
        }
        tv_category.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private ComicRankPopupWindowAdapter adapter;
    private ContentViewHolder contentViewHolder;

    public void showPopupwindow(View view){
        View contentView = View.inflate(getActivity(), R.layout.view_comic_rank_popupwindow, null);
        contentViewHolder = new ContentViewHolder(contentView);
        contentViewHolder.rg_group=contentView.findViewById(R.id.rg_group);
        contentViewHolder.rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_select_category){
                    ToastUtils.showToast("rb_select_category");
                    adapter.setNewData(categoryArrayList);
                }else if(checkedId==R.id.rb_select_date){
                    ToastUtils.showToast("rb_select_date");
                    adapter.setNewData(dateArrayList);
                }
            }
        });
        contentViewHolder.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new ComicRankPopupWindowAdapter(R.layout.item_comic_rank_popupwindow,categoryArrayList);
        adapter.setArgument(index,currentComicType,currentDate);
        adapter.setOnItemClickListener(this);
        contentViewHolder.recyclerview.setAdapter(adapter);

        popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_category.setTextColor(getResources().getColor(R.color.black));
                    }
                })
                .create()//创建PopupWindow
                .showAsDropDown(view);
    }

    public void getData(){
        for(int i=0;i<Constant.comicTypeMap.size();i++){
            Integer integer = Constant.comicTypeList.get(i);
            categoryArrayList.add(new ComicRankComicTypeBean(Constant.comicTypeMap.get(integer),integer,false));
        }

        for(int i=0;i<Constant.dateTypeMap.size();i++){
            Integer integer = Constant.dateTypeList.get(i);
            dateArrayList.add(new ComicRankDateTypeBean(Constant.dateTypeMap.get(integer),integer,true));
        }
    }

    private void initPopUpWindow() {
        adapter.setArgument(index,currentComicType,currentDate);
        if(index==0){
            tv_comic_type.setVisibility(View.VISIBLE);
            contentViewHolder.rg_group.setVisibility(View.VISIBLE);
            if(adapter.getData()!=categoryArrayList){
                contentViewHolder.rb_select_category.setChecked(true);
            }else{
                adapter.notifyDataSetChanged();
            }
        }else{
            tv_comic_type.setVisibility(View.GONE);
            contentViewHolder.rg_group.setVisibility(View.GONE);
            if(adapter.getData()==categoryArrayList){
                LogUtils.e("data为categoryArrayList");
                contentViewHolder.rb_select_date.setChecked(true);
            }else{
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void changeFragment(int num){
        LogUtils.e("num:"+num);
        index=num;
        if(index==0){
            tv_comic_type.setVisibility(View.VISIBLE);
        }else{
            tv_comic_type.setVisibility(View.GONE);
        }
        tv_date.setText(Constant.dateTypeMap.get(currentDate[index]));

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if(mFragments[num]==null){
            if(num==0){
                mFragments[num]=new ComicRankDetailFragment();
            }else if(num==1){
                mFragments[num]=new ComicRankDetailFragment();
            }else if(num==2){
                mFragments[num]=new ComicRankDetailFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putInt(Constant.MY_INDEX,num);
            bundle.putInt(Constant.CURRENT_COMIC_TYPE,currentComicType[index]);
            bundle.putInt(Constant.CURRENT_DATE,currentDate[index]);
            bundle.putInt(Constant.CURRENT_RANK_TYPE,currentRankType[index]);
            mFragments[num].setArguments(bundle);

            transaction.add(R.id.fl_content,mFragments[num]);
        }
        for(int i=0;i<mFragments.length;i++){
            if(mFragments[i]!=null&&i!=num){
                //若不隐藏其他fragment时后显示的fragment在上面(才可交互),
                //白色部分(不是view的部分)变透明可看到之前的fragment动画在执行
                transaction.hide(mFragments[i]);
            }else if(i==num){
                transaction.show(mFragments[i]);
            }
        }
        transaction.commit();
    }

    static class ContentViewHolder {
        @BindView(R.id.rg_group)
        RadioGroup rg_group;
        @BindView(R.id.rb_select_category)
        RadioButton rb_select_category;
        @BindView(R.id.rb_select_date)
        RadioButton rb_select_date;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;

        public ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
