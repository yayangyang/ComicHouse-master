package com.yayangyang.comichouse_master.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.decoration.GridItemDecoration;
import com.yayangyang.comichouse_master.decoration.SpaceItemDecoration;
import com.yayangyang.comichouse_master.ui.adapter.ComicRankPopupWindowAdapter;
import com.yayangyang.comichouse_master.ui.adapter.SelAdapter;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectionLayout extends LinearLayout {

    private Context mContext;
    private LinearLayout parent;

    private OnSelectListener listener;

    private List<String> mTitleList;

    public SelectionLayout(Context context) {
        this(context, null);
    }

    public SelectionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parent = this;
        this.mContext = context;
    }

    public void setData(List<String> titleList,List<String>... data) {
        LogUtils.e("SelectionLayout-setData");
        mTitleList=titleList;
        if (data != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                List<String> list = data[i];
                ChildView childView = new ChildView(mContext);
                LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                childView.setLayoutParams(params);
                childView.setData(list,i);
                childView.setTag(i);
                addView(childView);
            }
        }
    }

    private void closeAll() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ChildView childView = (ChildView) getChildAt(i);
            childView.closePopWindow();
        }
    }

    class ChildView extends LinearLayout implements OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

        private LinearLayout layout;

        private ImageView ivArrow;
        private TextView tvTitle;

        private boolean isOpen = false;
        private int initialSelection=0;

        private List<String> data = new ArrayList<>();
        private CustomPopWindow popWindow;
        private SelAdapter mAdapter;

        Animation operatingAnim1 = AnimationUtils.loadAnimation(mContext, R.anim.roate_0_180);
        Animation operatingAnim2 = AnimationUtils.loadAnimation(mContext, R.anim.roate_180_360);
        LinearInterpolator lin1 = new LinearInterpolator();
        LinearInterpolator lin2 = new LinearInterpolator();

        public ChildView(Context context) {
            this(context, null);
        }

        public ChildView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ChildView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.view_selection, this);

            initView();
        }

        private void initView() {
            ivArrow = (ImageView) findViewById(R.id.ivSelArrow);
            ivArrow.setScaleType(ImageView.ScaleType.MATRIX);   //required
            tvTitle = (TextView) findViewById(R.id.tvSelTitle);
            setOnClickListener(this);
            operatingAnim1.setInterpolator(lin1);
            operatingAnim1.setFillAfter(true);
            operatingAnim2.setInterpolator(lin2);
            operatingAnim2.setFillAfter(true);
        }

        private void setData(List<String> list,int i) {
            if (list != null && !list.isEmpty()) {
                data.addAll(list);
                if(mTitleList==null||mTitleList.size()<getChildCount()){
                    tvTitle.setText(list.get(0));
                }else{
                    tvTitle.setText(mTitleList.get(i));
                    if(list.contains(mTitleList.get(i))){
                        initialSelection=list.indexOf(mTitleList.get(i));
                    }else{
                        initialSelection=-1;
                    }
                }
            }
        }

        public void openPopupWindow() {
            tvTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            if (popWindow == null) {
                createPopupWindow();
            }
            mAdapter.notifyDataSetChanged();
            popWindow.showAsDropDown(parent.getChildAt(0));
        }

        private void createPopupWindow() {
            View contentView = View.inflate(mContext, R.layout.view_comic_category_popupwindow, null);
            RecyclerView recyclerView = contentView.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
            mAdapter=new SelAdapter(R.layout.item_selection_popupwindow,data);
            mAdapter.setSelPosition(initialSelection);//初始选中项
            mAdapter.setOnItemChildClickListener(this);
            recyclerView.setAdapter(mAdapter);

            popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                    .setView(contentView)//显示的布局，还可以通过设置一个View
                    .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) //设置显示的大小，不设置就默认包裹内容
                    .setFocusable(true)//是否获取焦点，默认为ture
                    .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                    .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            tvTitle.setTextColor(getResources().getColor(R.color.black));
                            ivArrow.startAnimation(operatingAnim2);
                            isOpen = false;
                        }
                    })
                    .create();//创建PopupWindow
        }

        public void closePopWindow() {
            if (popWindow != null) {
                popWindow.dissmiss();
            }
        }

        @Override
        public void onClick(View v) {
            if (isOpen) {
                ivArrow.startAnimation(operatingAnim2);
                closePopWindow();
                isOpen = false;
            } else {
                ivArrow.startAnimation(operatingAnim1);
                openPopupWindow();
                isOpen = true;
            }
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            mAdapter.setSelPosition(position);
            tvTitle.setText(data.get(position));
            if (listener != null) {
                listener.onSelect((Integer) this.getTag(), position, data.get(position));
            }
            popWindow.dissmiss();
        }
    }

    public interface OnSelectListener {
        void onSelect(int index, int position, String title);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

}
