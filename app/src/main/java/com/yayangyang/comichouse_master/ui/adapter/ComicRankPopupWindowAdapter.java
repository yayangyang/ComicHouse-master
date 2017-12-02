package com.yayangyang.comichouse_master.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yayangyang.comichouse_master.Bean.ComicRank;
import com.yayangyang.comichouse_master.Bean.ComicRankItemBean;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.transform.GlideRoundTransform;
import com.yayangyang.comichouse_master.utils.LogUtils;

import java.util.List;

public class ComicRankPopupWindowAdapter extends BaseQuickAdapter<ComicRankItemBean,BaseViewHolder> {

    private int index;
    private int[] currentComicType,currentDate;

    public ComicRankPopupWindowAdapter(int id, List<ComicRankItemBean> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicRankItemBean item) {
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
