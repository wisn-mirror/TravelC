package com.travel.account.bean;

import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.bean.UserInfo;

/**
 * Created by Wisn on 2018/5/29 下午2:17.
 */
public class LoginResponse extends BaseResponse<UserInfo> {

    /**
     * message : 登录成功
     * code : 0
     * ut : 2d0e6cbe45f641e89030422ba5d567fe
     */

    public String ut;


    @Override
    public String toString() {
        return "LoginResponse{" +
                "ut='" + ut + '\'' +
                ", message='" + message + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
