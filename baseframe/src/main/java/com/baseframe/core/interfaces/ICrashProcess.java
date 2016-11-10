package com.baseframe.core.interfaces;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: 崩溃日志接口协议
 */
public interface ICrashProcess {

    void onException(Thread thread, Throwable exception) throws Exception;
}
