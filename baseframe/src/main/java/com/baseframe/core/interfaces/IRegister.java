package com.baseframe.core.interfaces;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: 规范注册的接口协议
 */
public interface IRegister {
    /**
     * 注册广播、服务
     */
    void register();

    /**
     * 注销广播、服务
     */
    void unRegister();
}
