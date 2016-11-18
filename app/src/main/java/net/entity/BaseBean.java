package net.entity;

import java.io.Serializable;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description: JSON BASE BEAN
 */
public class BaseBean<T> implements Serializable {
    public int code;
    public String msg;
    public T result;
    //{"code":"1","msg":"\u8bf7\u6c42\u6210\u529f","result":{}}
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
