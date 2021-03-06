package com.baseframe.core.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.baseframe.core.R;
import com.baseframe.core.fragment.BaseFragment;
import com.baseframe.core.interfaces.IActivity;
import com.baseframe.core.interfaces.IRegister;
import com.baseframe.core.permissions.EasyPermissions;
import com.baseframe.core.utils.KeyBoardUtil;
import com.baseframe.core.utils.SharedPreferencesUtil;
import com.baseframe.core.utils.SystemBarTintManager;

import java.util.List;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: Activity父类
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener, IActivity, IRegister, EasyPermissions.PermissionCallbacks {
    private static final String SP_NAME = "firstConfig";

    private   int          mActivityState;
    private   BaseFragment mCurrentFragment;
    protected Activity     mActivity;
    protected Dialog mProgressDialog;
    private TextView progressDialogMessage;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        initPre();
        BaseActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutResId());

        mProgressDialog = new Dialog(mActivity, R.style.xProgress_dialog);
        mProgressDialog.setContentView(R.layout.com_progress_dialog_layout);
        mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialogMessage = (TextView) mProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        progressDialogMessage.setText("请求网络中...");
        mProgressDialog.setCancelable(false);

        SharedPreferencesUtil spUtil = new SharedPreferencesUtil(this, SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initUI(savedInstanceState);
        initData();
        addListener();
        register();
    }



    @Override public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //以下为可选方法，根据需要进行重载.
    //方法执行顺序：
    //initPre()  --> initUI(Bundle savedInstanceState) --> initData()-->  addListener() --> register()

    //只有第一次才会执行，这里可以做一些界面功能引导
    @Override public void onFirst() { }
    //这个方法会在setContentView(...)方法之前执行
    @Override public void initPre() { }
    @Override public void initData() { }
    @Override public void initUI(Bundle savedInstanceState) { }
    @Override public void showProgress() { }
    @Override public void hideProgress() { }
    //这里可以注册一些广播、服务
    @Override public void register() { }
    //注销广播、服务,在onDestroy()内部执行
    @Override public void unRegister() { }
    //注册监听器
    @Override public void addListener() {}
    //view点击事件统一处理
    @Override public void viewClick(View v) { }
    @Override public void onPermissionsGranted(int requestCode, List<String> perms) { }
    @Override public void onPermissionsDenied(int requestCode, List<String> perms) { }

    @Override public void onClick(View v) {
        viewClick(v);
    }


    @Override public void skipActivity(Activity aty, Class<?> cls) {
        startActivity(aty, cls);
        aty.finish();
    }


    @Override public void skipActivity(Activity aty, Intent it) {
        startActivity(aty, it);
        aty.finish();
    }


    @Override public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        startActivity(aty, cls, extras);
        aty.finish();
    }


    @Override public void startActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }


    @Override public void startActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }


    @Override public void startActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * 获取当前Activity状态
     *     {@link IActivity#RESUME},
     *     {@link IActivity#PAUSE},
     *     {@link IActivity#STOP},
     *     {@link IActivity#DESTROY}.
     *
     * @return
     */
    public int getActivityState() {
        return mActivityState;
    }

    /**
     * 获取当前显示的Fragment
     * @return
     */
    public BaseFragment getFragment() {
        return mCurrentFragment;
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView 将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, BaseFragment targetFragment) {
        if (targetFragment.equals(mCurrentFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onFragmentShow();
        }
        if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
            transaction.hide(mCurrentFragment);
            mCurrentFragment.onFragmentHide();
        }
        mCurrentFragment = targetFragment;
        transaction.commit();
    }


    @Override protected void onResume() {
        super.onResume();
        mActivityState = RESUME;
    }


    @Override protected void onPause() {
        super.onPause();
        mActivityState = PAUSE;
    }


    @Override protected void onStop() {
        super.onStop();
        mActivityState = STOP;
    }


    @Override public void finish() {
        KeyBoardUtil.hide(getWindow().getDecorView());
        super.finish();
    }


    @Override protected void onDestroy() {
        unRegister();
        super.onDestroy();
        mActivityState = DESTROY;
        BaseActivityStack.getInstance().finishActivity(this);
    }


    /**
     * 是否使用app统一的Title layout，如果需要自定义，复写该函数
     *
     * @return
     */
    protected boolean useComTitleLayout() {
        return true;
    }

    /**
     * 默认使用R.layout.container_nor这个头布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        if (useComTitleLayout()) {
            super.setContentView(R.layout.container_nor);
            ViewGroup clay = (ViewGroup) findViewById(R.id.parentId);
            View view = LayoutInflater.from(this).inflate(layoutResID, clay, false);
            clay.addView(view);
        } else {
            super.setContentView(layoutResID);
        }
        initSystemBarTint();
    }

    /** 设置状态栏颜色 */
    private void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColorPrimary());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getColorPrimary());
        }
    }

    /** 子类可以重写决定是否使用透明状态栏 */
    protected boolean translucentStatusBar() {
        return false;
    }

    /** 获取主题色 */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
