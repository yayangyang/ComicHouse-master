package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.fragment.NewsFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAkiraInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAnimatedInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsAppreciatePictureFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicDisplayFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsComicPeripheryFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsGameInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsHodgepodgeFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsLightNovelInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsMusicInformationFragment;
import com.yayangyang.comichouse_master.ui.fragment.detail.NewsRecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface NewsComponent {
    NewsRecommendFragment inject(NewsRecommendFragment fragment);

    NewsAnimatedInformationFragment inject(NewsAnimatedInformationFragment fragment);

    NewsComicInformationFragment inject(NewsComicInformationFragment fragment);

    NewsLightNovelInformationFragment inject(NewsLightNovelInformationFragment fragment);

    NewsAppreciatePictureFragment inject(NewsAppreciatePictureFragment fragment);

    NewsGameInformationFragment inject(NewsGameInformationFragment fragment);

    NewsComicPeripheryFragment inject(NewsComicPeripheryFragment fragment);

    NewsAkiraInformationFragment inject(NewsAkiraInformationFragment fragment);

    NewsComicDisplayFragment inject(NewsComicDisplayFragment fragment);

    NewsMusicInformationFragment inject(NewsMusicInformationFragment fragment);

    NewsHodgepodgeFragment inject(NewsHodgepodgeFragment fragment);
}
