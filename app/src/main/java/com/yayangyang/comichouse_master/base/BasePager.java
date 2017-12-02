package com.yayangyang.comichouse_master.base;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.ui.activity.MainActivity;

public abstract class BasePager {

	protected AppCompatActivity mActivity;
	public View mRootView;

	public BasePager(AppCompatActivity activity) {
		mActivity = activity;
		mRootView = initView();
	}

	public final View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		return view;
	}

	public abstract void initData();
}
