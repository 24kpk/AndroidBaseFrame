package net.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.baseframe.core.fragment.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import net.HttpUtil;
import net.request.HttpUIListener;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description:
 */
public abstract class BaseNetFragment extends BaseFragment implements HttpUIListener{
    private ProgressDialog mProgressDialog;

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("请求网络中...");
    }

    @Override
    public void onBefore(BaseRequest request) {
        //网络请求前显示对话框
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        Logger.e(HttpUtil.TAG,"-------------------REQUEST ERROR-------------------");
        Logger.e(HttpUtil.TAG,e);
        Logger.e(HttpUtil.TAG,"-------------------REQUEST ERROR-------------------");
    }

    @Override
    public void parseError(Call call, Exception e) {
        Logger.e(HttpUtil.TAG,e);
    }

    @Override
    public <T> void onAfter(T t, Exception e) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onFragmentHide() {
        super.onFragmentHide();
        OkGo.getInstance().cancelAll();
    }

    @Override
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }
}
