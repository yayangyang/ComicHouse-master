package com.yayangyang.comichouse_master.view.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.ui.activity.ComicReadActivity;

/**
 * Created by Administrator on 2017/12/23.
 */

public class MyLoadMoreView extends LinearLayout implements View.OnClickListener{

    private FrameLayout load_more_load_fail_view,load_more_end_view;

    private LinearLayout layout,load_more_loading_view;

    private ComicReadActivity.OnAgainLoadListener mListener;

    public MyLoadMoreView(Context context) {
        this(context, null);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将生成的view挂载到此ViewGroup(即此ViewGroup添加了生成的view)
        layout = (LinearLayout) View.inflate(context,R.layout.view_load_more, this);

        initView();
    }

    @Override
    public void onClick(View v) {
        mListener.loadAgain(this);
    }

    private void initView() {
        load_more_loading_view=findViewById(R.id.load_more_loading_view);
        load_more_load_fail_view=findViewById(R.id.load_more_load_fail_view);
        load_more_end_view=findViewById(R.id.load_more_end_view);

        load_more_load_fail_view.setOnClickListener(this);
    }

    public void loadMoreComplete(){
        load_more_loading_view.setVisibility(VISIBLE);
        load_more_load_fail_view.setVisibility(GONE);
        load_more_end_view.setVisibility(GONE);
    }

    public void loadMoreFail(){
        load_more_loading_view.setVisibility(GONE);
        load_more_load_fail_view.setVisibility(VISIBLE);
        load_more_end_view.setVisibility(GONE);
    }

    public void loadMoreEnd(){
        load_more_loading_view.setVisibility(GONE);
        load_more_load_fail_view.setVisibility(GONE);
        load_more_end_view.setVisibility(VISIBLE);
    }

    public void setOnAgainLoadListener(ComicReadActivity.OnAgainLoadListener loadListener){
        mListener=loadListener;
    }

}
