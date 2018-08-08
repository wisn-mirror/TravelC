package com.travel.library.beans;

/**
 * Created by Wisn on 2018/5/29 下午2:13.
 */
public class BaseResponse<T> {

    /**
     * message : 登录成功
     * code : 0
     */

    public String message;
    public String errMsg;
    public int code;
    public T data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
