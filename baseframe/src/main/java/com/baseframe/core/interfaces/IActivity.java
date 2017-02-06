package com.baseframe.core.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: 规范Activity接口协议
 */
public interface IActivity {
    int DESTROY = 0x00;
    int STOP    = 0x01;
    int PAUSE   = 0x02;
    int RESUME  = 0x03;

    /**
     * 获取布局文件
     */
    int getLayoutResId();

    /** 第一次启动会执行此方法 */
    void onFirst();

    /**
     * 此方法会在setContentView之前调用
     */
    void initPre();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化控件
     */
    void initUI(Bundle savedInstanceState);

    /**
     * 注册监听器
     */
    void addListener();

    /**
     * 点击事件回调方法
     */
    void viewClick(View v);

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 跳转指定activity，并关闭当前activity
     */
    void skipActivity(Activity aty, Class<?> cls);

    /**
     * 跳转指定activity，并关闭当前activity
     */
    void skipActivity(Activity aty, Intent it);

    /**
     * 跳转指定activity，并关闭当前activity
     */
    void skipActivity(Activity aty, Class<?> cls, Bundle extras);

    /**
     * 跳转activity
     */
    void startActivity(Activity aty, Class<?> cls);

    /**
     * 跳转activity
     */
    void startActivity(Activity aty, Intent it);

    /**
     * 跳转activity
     */
    void startActivity(Activity aty, Class<?> cls, Bundle extras);
}
