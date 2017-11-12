package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.activity.MainActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainComponent {
    MainActivity inject(MainActivity activity);
}