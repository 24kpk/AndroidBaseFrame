package com.baseframe.core.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        SharedPreferencesUtil spUtil = new SharedPreferencesUtil(mActivity, SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initData();
        initView(parentView, savedInstanceState);
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

    @Override public void onFirst() { }
    @Override public void initData() { }
    @Override public void initView(View parentView, Bundle savedInstanceState) { }
    @Override public void register() { }
    @Override public void unRegister() { }
    @Override public void onFragmentShow() { }
    @Override public void onFragmentHide() { }
    @Override public void showProgress() { }
    @Override public void hideProgress() { }
    @Override public void viewClick(View v) { }
    @Override public void onPermissionsGranted(int requestCode, List<String> perms) { }
    @Override public void onPermissionsDenied(int requestCode, List<String> perms) { }

    @Override public void onClick(View v) {
        viewClick(v);
    }
}
