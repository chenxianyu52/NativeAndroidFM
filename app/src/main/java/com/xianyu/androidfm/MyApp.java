package com.xianyu.androidfm;

import android.app.Application;

import com.xianyu.common.log.LoggerUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.init();
    }
}
