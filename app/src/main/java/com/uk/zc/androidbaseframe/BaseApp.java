package com.uk.zc.androidbaseframe;

import android.app.Application;

import com.baseframe.core.BasicConfig;
import com.orhanobut.logger.Logger;

import net.HttpUtil;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description:
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(this);
        BasicConfig.getInstance(this).initDir("BASE_APP").initExceptionHandler().initLog(true);
    }
}
