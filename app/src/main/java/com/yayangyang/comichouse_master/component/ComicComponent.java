package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRankDetailFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicRecommendFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicSpecialTopicFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicUpdateFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface ComicComponent {
    ComicRecommendFragment inject(ComicRecommendFragment fragment);

    ComicUpdateFragment inject(ComicUpdateFragment fragment);

    ComicCategoryFragment inject(ComicCategoryFragment fragment);

    ComicRankDetailFragment inject(ComicRankDetailFragment fragment);

    ComicSpecialTopicFragment inject(ComicSpecialTopicFragment fragment);
}