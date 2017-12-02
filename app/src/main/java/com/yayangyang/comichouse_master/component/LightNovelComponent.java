package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.fragment.LightNovelFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface LightNovelComponent {
    LightNovelFragment inject(LightNovelFragment fragment);
}
