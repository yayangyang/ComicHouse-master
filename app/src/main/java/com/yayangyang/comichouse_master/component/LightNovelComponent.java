package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.activity.NewestNovelActivity;
import com.yayangyang.comichouse_master.ui.activity.NovelCategoryActivity;
import com.yayangyang.comichouse_master.ui.activity.NovelCategoryDetailActivity;
import com.yayangyang.comichouse_master.ui.activity.NovelRankActivity;
import com.yayangyang.comichouse_master.ui.fragment.LightNovelFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NovelCategoryDetailFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NovelRankFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface LightNovelComponent {
    LightNovelFragment inject(LightNovelFragment fragment);

    NewestNovelActivity inject(NewestNovelActivity activity);

    NovelCategoryActivity inject(NovelCategoryActivity activity);

    NovelRankActivity inject(NovelRankActivity activity);

    NovelRankFragment inject(NovelRankFragment fragment);

    NovelCategoryDetailFragment inject(NovelCategoryDetailFragment fragment);
}
