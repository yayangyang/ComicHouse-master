package com.yayangyang.comichouse_master.base;

import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.view.SelectionLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class BaseComicCategoryActivity extends BaseActivity implements SelectionLayout.OnSelectListener {

    @BindView(R.id.slOverall)
    SelectionLayout slOverall;

    protected String type="0";

    //list与mTitleList的大小应相同
    protected List<List<String>> list;
    protected List<String> mTitleList;

    protected List<List<String>> novelRankList = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add("全部");add("恐怖");add("科幻");add("侦探");add("爱情");add("校园");add("神鬼");
            add("魔法");add("冒险");add("其它");add("搞笑");add("格斗");add("机战");add("仙侠");
            add("都市");add("历史");add("战争");add("治愈");add("励志");add("后宫");add("百合");
            add("耽美");add("异界");add("异能");add("穿越");add("奇幻");
        }});
    }};

    protected List<String> novelCategoryTitleList=new ArrayList<String>(){{
        add("全部");add("连载进度");
    }};

    protected List<List<String>> novelCategoryList = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add("全部");add("恐怖");add("科幻");add("侦探");add("爱情");add("校园");add("神鬼");
            add("魔法");add("冒险");add("其它");add("搞笑");add("格斗");add("机战");add("仙侠");
            add("都市");add("历史");add("战争");add("治愈");add("励志");add("后宫");add("百合");
            add("耽美");add("异界");add("异能");add("穿越");add("奇幻");
        }});
        add(new ArrayList<String>() {{
            add("全部");
            add("连载中");
            add("已完结");
        }});
    }};

    protected List<String> titleList2=new ArrayList<String>(){{
        add("题材");add("读者群");add("进度");add("地域");
    }};

    protected List<List<String>> list2 = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add("全部");add("冒险");add("百合");add("生活");add("四格");add("伪娘");add("悬疑");
            add("历史");add("后宫");add("热血");add("耽美");add("其他");add("恐怖");add("科幻");
            add("格斗");add("欢乐向");add("爱情");add("侦探");add("校园");add("神鬼");add("魔法");
            add("竞技");add("战争");add("萌系");add("奇幻");add("魔幻");add("扶她");add("节操");
            add("轻小说");add("舰娘");add("仙侠");add("搞笑");add("颜艺");add("东方");add("性转换");
            add("武侠");add("励志");add("治愈");add("宅系");add("机战");add("音乐舞蹈");add("高清单行");
            add("西方魔幻");add("职场");add("美食");
        }});
        add(new ArrayList<String>() {{
            add("全部");
            add("少年漫画");
            add("少女漫画");
            add("青年漫画");
            add("女青漫画");
        }});
        add(new ArrayList<String>() {{
            add("全部");
            add("连载中");
            add("已完结");
        }});
        add(new ArrayList<String>() {{
            add("全部");
            add("日本");
            add("韩国");
            add("欧美");
            add("港台");
            add("其他");
            add("内地");
        }});
    }};

    @Override
    public void initDatas() {
        LogUtils.e("BaseComicCategoryActivity-initDatas");
        list = getTabList();
        mTitleList=getTitleList();
        if (slOverall != null) {
            slOverall.setData(mTitleList,list.toArray(new List[list.size()]));
            slOverall.setOnSelectListener(this);
        }
    }

    protected abstract List<List<String>> getTabList();

    protected abstract List<String> getTitleList();

    public abstract void onSelect(int index, int position, String title);
}
