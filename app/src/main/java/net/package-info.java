/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: 网络操作
 *
 *
 * 注：本包下为网络请求的基础框架
 * 若项目中的Activity或Fragment包含网络请求 则请直接集成net.ui.base.BaseNetActivity(或BaseNetFragment)
 * 包含
 * 1：网络请求
 * 2：网络请求的回调
 *  2.1：网络请求返回字符串
 *  2.2：网络请求返回BEAN
 * 3：网络请求分解
 *
 *
 * 集成步骤
 * 1：compile 'com.github.24kpk:AndroidBaseFrame:+'
 * 2：compile 'com.lzy.net:okgo:+'
 *    compile 'com.google.code.gson:gson:+'
 *    在AndroidManifest中添加权限
 *    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *    <uses-permission android:name="android.permission.INTERNET"/>
 * 3：复制net package下的文件到TARGET项目中
 * 4：重定义net.entity.BaseBean 并修改net.request.JsonConvert中关于BaseBean 属性code的解析判断为其它相关逻辑
 *    *注：BaseBean必须实现Serializable接口*
 *
 *
 * 使用
 * 1：在Application中
 * HttpUtil.init(this);
 *
 * 2:在集成了BaseNetActivity(或BaseNetFragment) 中
 *
 */
package net;