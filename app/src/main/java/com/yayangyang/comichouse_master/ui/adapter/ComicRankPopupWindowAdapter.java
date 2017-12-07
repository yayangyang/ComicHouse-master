package com.yayangyang.comichouse_master.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.base.ComicRankPopupWindowBean;
import com.yayangyang.comichouse_master.R;

import java.util.List;

public class ComicRankPopupWindowAdapter extends BaseQuickAdapter<ComicRankPopupWindowBean,BaseViewHolder> {

    private int index;
    private int[] currentComicType,currentDate;

    public ComicRankPopupWindowAdapter(int id, List<ComicRankPopupWindowBean> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicRankPopupWindowBean item) {
        helper.setVisible(R.id.iv_arrow, false);
        if(item.isDate&&item.type==currentDate[index]||
                !item.isDate&&item.type==currentComicType[index]){
            helper.setVisible(R.id.iv_arrow, true);
        }
        helper.setText(R.id.tv_type,item.title);
    }

    public void setArgument(int index,int[] currentComicType,int[] currentDate){
        this.index=index;
        this.currentComicType=currentComicType;
        this.currentDate=currentDate;
    }
}
