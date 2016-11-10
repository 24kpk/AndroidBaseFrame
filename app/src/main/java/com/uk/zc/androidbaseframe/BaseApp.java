package com.uk.zc.androidbaseframe;

import android.app.Application;

import com.baseframe.core.BasicConfig;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description:
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.getInstance(this).init();
    }
}
