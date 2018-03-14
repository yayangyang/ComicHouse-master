package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ComicChapterAdapter extends BaseQuickAdapter<ComicDetailHeader.ChaptersBean.DataBean,BaseViewHolder> {

    private String currentChapterId,comicId;

    public ComicChapterAdapter(int layoutResId, @Nullable List<ComicDetailHeader.ChaptersBean.DataBean> data,String comicId) {
        super(layoutResId, data);
        this.comicId=comicId;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicDetailHeader.ChaptersBean.DataBean item) {
        LogUtils.e("ComicChapterAdapter-convert"+helper.getLayoutPosition());
        LogUtils.e("getItemCount():"+getItemCount());
        LogUtils.e("mData.size():"+mData.size());
        helper.setText(R.id.tv_chapter,item.chapter_title);
        if(!TextUtils.isEmpty(item.chapter_id)&&item.chapter_id.equals(currentChapterId)){
            helper.setTextColor(R.id.tv_chapter,mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.frameLayout).setSelected(true);
        }else{
            helper.setTextColor(R.id.tv_chapter,mContext.getResources().getColor(R.color.black));
            helper.getView(R.id.frameLayout).setSelected(false);
        }

        helper.addOnClickListener(R.id.frameLayout);
    }

    public void getCurrentChapterName(){
        String readProgress = SettingManager.getInstance().getReadProgress(comicId);
        String[] split = readProgress.split("-");
        currentChapterId=split[0];
    }

}
