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

2).默认缓存模式,遵循304头 CacheMode.DEFAULT
网络请求成功,服务端返回非304 onBefore -> convertSuccess -> onSuccess -> onAfter
网络请求成功服务端返回304 onBefore -> onCacheSuccess -> onAfter
网络请求失败 onBefore -> parseError -> onError -> onAfter

3).请求网络失败后读取缓存 CacheMode.REQUEST_FAILED_READ_CACHE
网络请求成功,不读取缓存 onBefore -> convertSuccess -> onSuccess -> onAfter
网络请求失败,读取缓存成功 onBefore -> parseError -> onError -> onCacheSuccess -> onAfter
网络请求失败,读取缓存失败 onBefore -> parseError -> onError -> onCacheError -> onAfter

4).如果缓存不存在才请求网络，否则使用缓存 CacheMode.IF_NONE_CACHE_REQUEST
已经有缓存,不请求网络 onBefore -> onCacheSuccess -> onAfter
没有缓存请求网络成功 onBefore -> onCacheError -> convertSuccess -> onSuccess -> onAfter
没有缓存请求网络失败 onBefore -> onCacheError -> parseError -> onError -> onAfter

5).先使用缓存，不管是否存在，仍然请求网络 CacheMode.FIRST_CACHE_THEN_REQUEST
无缓存时,网络请求成功 onBefore -> onCacheError -> convertSuccess -> onSuccess -> onAfter
无缓存时,网络请求失败 onBefore -> onCacheError -> parseError -> onError -> onAfter
有缓存时,网络请求成功 onBefore -> onCacheSuccess -> convertSuccess -> onSuccess -> onAfter
有缓存时,网络请求失败 onBefore -> onCacheSuccess -> parseError -> onError -> onAfter
*/


    /** 请求网络开始前，UI线程 */
    public void onBefore(BaseRequest request) ;

    /** 对返回数据进行操作的回调， UI线程 */
    public abstract<T> void onSuccess(T t, Call call, Response response);

    /** 缓存成功的回调,UI线程 */
    //若请求配置缓存则放开此处注释
    //public <T> void onCacheSuccess(T t, Call call) ;

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    public  void onError(Call call, Response response, Exception e) ;

    /** 缓存失败的回调,UI线程 */
    //若请求配置缓存则放开此处注释
    //public void onCacheError(Call call, Exception e) ;

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
