package com.yayangyang.comichouse_master.component;

import com.yayangyang.comichouse_master.ui.activity.LoginActivity;
import com.yayangyang.comichouse_master.ui.activity.MainActivity;
import com.yayangyang.comichouse_master.ui.fragment.MineFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MineComponent {
    LoginActivity inject(LoginActivity loginActivity);
}