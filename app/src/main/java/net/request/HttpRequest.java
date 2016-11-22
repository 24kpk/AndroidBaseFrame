package net.request;

import android.content.Context;

import com.baseframe.core.permissions.AfterPermissionGranted;
import com.baseframe.core.permissions.EasyPermissions;
import com.baseframe.core.utils.NetworkUtil;
import com.baseframe.core.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;

import net.PermissionUtils;
import net.entity.BaseBean;

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
     * POST请求 返回实体BEAN
     * @param context context
     * @param url url
     * @param params params
     * @param tag tag
     * @param listener listener
     */
    public static void submitPostResponseBean(Context context, String url, Map<String,String> params,Class<?> cls, String tag, final HttpUIListener listener){
        if (!hasNetPerssion(context)){
            return;
        }
        if (NetworkUtil.getAPNType(context) == 0){
            ToastUtil.showToast(context,"当前没有网络，请检查网络后重试");
            return ;
        }
        PostRequest postRequest = OkGo.post(url).params(params).tag(tag);
        postRequest.execute(new JsonCallback<BaseBean>(cls){

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
            public void onSuccess(BaseBean baseBean, Call call, Response response) {
                try {
                    listener.onSuccess(baseBean,call,response);
                }catch (Exception e){
                    listener.parseError(call, e);
                }
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
            public void onAfter(BaseBean baseBean, Exception e) {
                super.onAfter(baseBean, e);
                listener.onAfter(baseBean,e);
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
