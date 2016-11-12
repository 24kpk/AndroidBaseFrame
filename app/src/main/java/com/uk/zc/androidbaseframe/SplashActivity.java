package com.uk.zc.androidbaseframe;

import com.baseframe.core.activity.BaseSplashActivity;

import java.util.List;

/**
 * author: C_CHEUNG
 * created on: 2016/11/12
 * description: SplashActivity
 */
public class SplashActivity extends BaseSplashActivity{
    @Override
    protected void setSplashResources(List<SplashImgResource> resources) {
        resources.add(new SplashImgResource(R.mipmap.spsh,4000,0,true));
    }

    @Override
    protected boolean isAutoStartNextActivity() {
        return true;
    }

    @Override
    protected Class<?> nextActivity() {
        return MainActivity.class;
    }
}
