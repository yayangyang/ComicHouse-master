package com.yayangyang.comichouse_master.ui.adapter;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicDetailHeader;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.manager.SettingManager;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ComicSelectDownLoadAdapter extends BaseQuickAdapter<ComicDetailHeader.ChaptersBean.DataBean,BaseViewHolder> {

    private String comicId;

    public ComicSelectDownLoadAdapter(int layoutResId, @Nullable List<ComicDetailHeader.ChaptersBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicDetailHeader.ChaptersBean.DataBean item) {
        LogUtils.e("ComicSelectDownLoadAdapter-convert");
        helper.setText(R.id.tv_chapter,item.chapter_title);
        helper.addOnClickListener(R.id.tv_chapter);

        List<String> comicChapterDownLoadInfo = SettingManager.getInstance().getComicChapterDownLoadInfo(comicId);
        if(comicChapterDownLoadInfo!=null){
            boolean clickAble=true;
            for(int i=0;i<comicChapterDownLoadInfo.size();i++){
                if(comicChapterDownLoadInfo.get(i).equals(item.chapter_id)){
                    //helper.getView(R.id.tv_chapter).setEnabled(false);//setEnabled设置值为false,View仍然会消费事件
                    clickAble=false;
                    TextView textView = helper.getView(R.id.tv_chapter);
                    textView.setTextColor(mContext.getResources().getColor(R.color.gray));
                    textView.setClickable(false);
                    break;
                }
            }
            if(clickAble){
                TextView textView = helper.getView(R.id.tv_chapter);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                textView.setClickable(true);
            }
        }

        LogUtils.e("isClickable:"+helper.getView(R.id.tv_chapter).isClickable());
        LogUtils.e("isSelected:"+item.isSelected());
        if(helper.getView(R.id.tv_chapter).isClickable()&&item.isSelected()){
            LogUtils.e("wwwwwwww1");
            helper.getView(R.id.tv_chapter).setSelected(true);
        }else{
            LogUtils.e("wwwwwwww2");
            helper.getView(R.id.tv_chapter).setSelected(false);
        }
    }

    public void setComicId(String comicId){
        this.comicId=comicId;
    }

}
