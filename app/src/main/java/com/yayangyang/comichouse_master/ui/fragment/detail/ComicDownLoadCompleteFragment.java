package com.yayangyang.comichouse_master.ui.fragment.detail;

import android.support.v7.widget.RecyclerView;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.BaseFragment;
import com.yayangyang.comichouse_master.component.AppComponent;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/9.
 */

public class ComicDownLoadCompleteFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comic_download_complete;
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

    }
}
