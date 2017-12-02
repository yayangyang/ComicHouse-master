package com.yayangyang.comichouse_master.component;

import android.content.Context;

import com.yayangyang.comichouse_master.api.ComicApi;
import com.yayangyang.comichouse_master.module.AppModule;
import com.yayangyang.comichouse_master.module.ComicApiModule;

import dagger.Component;

@Component(modules = {AppModule.class, ComicApiModule.class})
public interface AppComponent {

    Context getContext();

    ComicApi getReaderApi();

}