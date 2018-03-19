package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicChapterDownLoadInfo;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.Bean.ComicDownLoadMission;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class ComicDownLoadCompleteAdapter extends BaseQuickAdapter<ComicDownLoadMission,BaseViewHolder> {

    public ComicDownLoadCompleteAdapter(int layoutResId, @Nullable List<ComicDownLoadMission> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicDownLoadMission item) {
        LogUtils.e("ComicDownLoadCompleteAdapter-convert");
        helper.setText(R.id.tv_chapter,item.getChapterTitle());
        helper.addOnClickListener(R.id.tv_chapter);

        if(item.isSelected()){
            helper.getView(R.id.tv_chapter).setSelected(true);
        }else{
            helper.getView(R.id.tv_chapter).setSelected(false);
        }
    }

}
