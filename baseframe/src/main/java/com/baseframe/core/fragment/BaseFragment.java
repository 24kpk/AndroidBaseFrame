package com.baseframe.core.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseframe.core.R;
import com.baseframe.core.interfaces.IFragment;
import com.baseframe.core.interfaces.IRegister;
import com.baseframe.core.permissions.EasyPermissions;
import com.baseframe.core.utils.SharedPreferencesUtil;
import java.util.List;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: Fragment父类
 */
public abstract class BaseFragment extends Fragment
        implements IFragment, IRegister, View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final String SP_NAME         = "firstConfig";
    private static final String STATE_IS_HIDDEN = "isHidden";

    protected Activity mActivity;
    protected Dialog mProgressDialog;
    private TextView progressDialogMessage;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutResId(), container, false);

        mProgressDialog = new Dialog(mActivity, R.style.xProgress_dialog);
        mProgressDialog.setContentView(R.layout.com_progress_dialog_layout);
        mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialogMessage = (TextView) mProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        progressDialogMessage.setText("请求网络中...");
        mProgressDialog.setCancelable(false);

        SharedPreferencesUtil spUtil = new SharedPreferencesUtil(mActivity, SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initUI(parentView, savedInstanceState);
        initData();
        addListener();
        return parentView;
    }


    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if(isHidden){
                transaction.hide(this);
                onFragmentHide();
            } else {
                transaction.show(this);
                onFragmentShow();
            }
            transaction.commit();
        }
        register();
    }


    @Override public void onDestroyView() {
        unRegister();
        super.onDestroyView();
    }

    @Override public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //以下为可选方法，根据需要进行重载.
    //方法执行顺序：
    //initUI(View parentView, Bundle savedInstanceState) --> initData() --> addListener() -->  register()

    //只有第一次才会执行，这里可以做一些界面功能引导
    @Override public void onFirst() { }
    @Override public void initData() { }
    @Override public void initUI(View parentView, Bundle savedInstanceState) { }
    //这里可以注册一些广播、服务
    @Override public void register() { }
    //注销广播、服务, 在onDestroyView()内部执行
    @Override public void unRegister() { }
    //Fragment被切换到前台时调用
    @Override public void onFragmentShow() { }
    //Fragment被切换到后台时调用
    @Override public void onFragmentHide() { }
    //注册监听器
    @Override public void addListener() {}

    @Override public void showProgress() { }
    @Override public void hideProgress() { }
    //view点击事件统一处理
    @Override public void viewClick(View v) { }
    @Override public void onPermissionsGranted(int requestCode, List<String> perms) { }
    @Override public void onPermissionsDenied(int requestCode, List<String> perms) { }

    @Override public void onClick(View v) {
        viewClick(v);
    }
}
