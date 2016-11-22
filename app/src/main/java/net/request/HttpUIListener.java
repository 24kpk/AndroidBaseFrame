package net.request;

import com.lzy.okgo.request.BaseRequest;

import net.entity.BaseBean;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description: Http请求UI的回调
 */
public interface HttpUIListener {
    /*
方法回调顺序
1).无缓存模式 CacheMode.NO_CACHE
网络请求成功 onBefore -> convertSuccess -> onSuccess -> onAfter
网络请求失败 onBefore -> parseError -> onError -> onAfter

*/

    /** 请求网络开始前，UI线程 */
    public void onBefore(BaseRequest request) ;

    /** 对返回数据进行操作的回调， UI线程 */
    public abstract void onSuccess(BaseBean baseBean, Call call, Response response);

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    public  void onError(Call call, Response response, Exception e) ;

    /** 网络失败结束之前的回调 */
    public void parseError(Call call, Exception e) ;

    /** 请求网络结束后，UI线程 */
    public <T> void onAfter(T t, Exception e) ;

    /**
     * Post执行上传过程中的进度回调，get请求不回调，UI线程
     *
     * @param currentSize  当前上传的字节数
     * @param totalSize    总共需要上传的字节数
     * @param progress     当前上传的进度
     * @param networkSpeed 当前上传的速度 字节/秒
     */
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) ;

    /**
     * 执行下载过程中的进度回调，UI线程
     *
     * @param currentSize  当前下载的字节数
     * @param totalSize    总共需要下载的字节数
     * @param progress     当前下载的进度
     * @param networkSpeed 当前下载的速度 字节/秒
     */
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) ;
}
