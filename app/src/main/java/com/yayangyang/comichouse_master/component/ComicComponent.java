package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.activity.AuthorIntroduceActivity;
import com.yayangyang.comichouse_master.ui.activity.ComicCategoryActivity;
import com.yayangyang.comichouse_master.ui.activity.ComicDetailActivity;
import com.yayangyang.comichouse_master.ui.activity.NewComicWeeklyActivity;
import com.yayangyang.comichouse_master.ui.activity.PublishReviewActivity;
import com.yayangyang.comichouse_master.ui.fragment.detail.ComicCategoryDetailFragment;
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

    ComicCategoryDetailFragment inject(ComicCategoryDetailFragment fragment);

    ComicCategoryActivity inject(ComicCategoryActivity activity);

    NewComicWeeklyActivity inject(NewComicWeeklyActivity activity);

    AuthorIntroduceActivity inject(AuthorIntroduceActivity activity);

    ComicDetailActivity inject(ComicDetailActivity activity);

    PublishReviewActivity inject(PublishReviewActivity activity);
}