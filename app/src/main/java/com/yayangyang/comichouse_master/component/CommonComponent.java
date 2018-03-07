package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.activity.SearchActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface CommonComponent {
    SearchActivity inject(SearchActivity searchActivity);
}