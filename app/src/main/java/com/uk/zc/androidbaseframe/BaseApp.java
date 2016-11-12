package com.uk.zc.androidbaseframe;

import android.app.Application;

import com.baseframe.core.BasicConfig;
import com.orhanobut.logger.Logger;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description:
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.getInstance(this).initDir("BASE_APP").initExceptionHandler().initLog(true);
    }
}
