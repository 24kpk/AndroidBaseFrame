package net.request;

import android.content.Context;

import com.baseframe.core.permissions.AfterPermissionGranted;
import com.baseframe.core.permissions.EasyPermissions;
import com.baseframe.core.utils.NetworkUtil;
import com.baseframe.core.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.uk.zc.androidbaseframe.bean.UserBean;

import net.PermissionUtils;
import net.entity.BaseBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description: 网络请求类 添加线程池
 */
public class HttpRequest {
    private static final int RC_NETWORK = 2001;


    /**
     * 一般的GET请求 返回String
     * @param url url
     * @param tag tag
     * @param listener listener
     */
    public static void submitGet(Context context,String url, String tag, final HttpUIListener listener){
        if (!hasNetPerssion(context)){
            return;
        }

        if (NetworkUtil.getAPNType(context) == 0){
            ToastUtil.showToast(context,"当前没有网络，请检查网络后重试");
            return ;
        }

        GetRequest getRequest = OkGo.get(url).tag(tag);
        getRequest.execute(new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                listener.onSuccess(s,call,response);
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                listener.onBefore(request);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                listener.onError(call, response, e);
            }


            @Override
            public void parseError(Call call, Exception e) {
                super.parseError(call, e);
                listener.parseError(call, e);
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                listener.onAfter(s, e);
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                listener.upProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                listener.downloadProgress(currentSize, totalSize, progress, networkSpeed);
            }
        });
    }

    /**
     * POST请求 返回String
     * @param url url
     * @param params params
     * @param tag tag
     * @param listener listener
     */
    public static void submitPost(Context context,String url, HashMap<String,String> params, String tag, final HttpUIListener listener){
        if (!hasNetPerssion(context)){
            return;
        }
        if (NetworkUtil.getAPNType(context) == 0){
            ToastUtil.showToast(context,"当前没有网络，请检查网络后重试");
            return ;
        }
        PostRequest postRequest = OkGo.post(url).params(params).tag(tag);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                listener.onSuccess(s,call,response);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                listener.downloadProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                listener.onBefore(request);
            }

            @Override
            public void onCacheSuccess(String s, Call call) {
                super.onCacheSuccess(s, call);
//                listener.onCacheSuccess(s, call);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                listener.onError(call, response, e);
            }

            @Override
            public void onCacheError(Call call, Exception e) {
                super.onCacheError(call, e);
//                listener.onCacheError(call, e);
            }

            @Override
            public void parseError(Call call, Exception e) {
                super.parseError(call, e);
                listener.parseError(call, e);
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                listener.onAfter(s, e);
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                listener.upProgress(currentSize, totalSize, progress, networkSpeed);
            }
        });
    }


    /**
     * POST请求 返回实体BEAN
     * @param context context
     * @param url url
     * @param params params
     * @param tag tag
     * @param listener listener
     */
    public static void submitPostResponseJson(Context context, String url, Map<String,String> params, String tag, final HttpUIListener listener){
        if (!hasNetPerssion(context)){
            return;
        }
        if (NetworkUtil.getAPNType(context) == 0){
            ToastUtil.showToast(context,"当前没有网络，请检查网络后重试");
            return ;
        }
        PostRequest postRequest = OkGo.post(url).params(params).tag(tag);
        postRequest.execute(new JsonCallback<BaseBean<UserBean>>(){

            @Override
            public void onSuccess(BaseBean<UserBean> userBeanBaseBean, Call call, Response response) {
                listener.onSuccess(userBeanBaseBean,call,response);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                listener.downloadProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                listener.onBefore(request);
            }


            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                listener.onError(call, response, e);
            }

            @Override
            public void onCacheError(Call call, Exception e) {
                super.onCacheError(call, e);
//                listener.onCacheError(call, e);
            }

            @Override
            public void parseError(Call call, Exception e) {
                super.parseError(call, e);
                listener.parseError(call, e);
            }

            @Override
            public void onAfter(BaseBean<UserBean> userBeanBaseBean, Exception e) {
                super.onAfter(userBeanBaseBean, e);
                listener.onAfter(userBeanBaseBean,e);
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                listener.upProgress(currentSize, totalSize, progress, networkSpeed);
            }
        });
    }

    @AfterPermissionGranted(RC_NETWORK)
    private static boolean hasNetPerssion(Context context){
        if (!EasyPermissions.hasPermissions(context, PermissionUtils.ACCESS_INTERNET)) {
            EasyPermissions.requestPermissions(context, PermissionUtils.ACCESS_INTERNET_INFO,
                    RC_NETWORK, PermissionUtils.ACCESS_INTERNET);
            return false;
        }else {
            return true;
        }
    }
}
