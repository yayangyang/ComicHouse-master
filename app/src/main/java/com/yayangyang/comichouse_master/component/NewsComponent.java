package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.fragment.NewsFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAnimatedInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsRecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface NewsComponent {
    NewsRecommendFragment inject(NewsRecommendFragment fragment);

    NewsAnimatedInformationFragment inject(NewsAnimatedInformationFragment fragment);
}
