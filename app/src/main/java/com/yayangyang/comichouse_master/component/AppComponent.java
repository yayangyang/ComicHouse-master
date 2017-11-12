package com.yayangyang.comichouse_master.component;

import android.content.Context;

import com.yayangyang.comichouse_master.api.BookApi;
import com.yayangyang.comichouse_master.module.AppModule;
import com.yayangyang.comichouse_master.module.BookApiModule;

import dagger.Component;

@Component(modules = {AppModule.class, BookApiModule.class})
public interface AppComponent {

    Context getContext();

    BookApi getReaderApi();

}