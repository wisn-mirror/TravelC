package com.travel.account.login;


import android.text.TextUtils;

import com.travel.account.bean.LoginRequest;
import com.travel.account.bean.LoginResponse;
import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.Auth;
import com.travel.library.net.rx.RxObservableListener;
import com.travel.library.utils.JsonConvertor;

import okhttp3.RequestBody;

/**
 * Created by Wisn on 2018/5/29 下午2:35.
 */
public class LoginP extends Login.Presenter {
    @Override
    public void Login(String userName, String password) {
        RequestBody requestBody = JsonConvertor.getRequestBody(new LoginRequest(userName, password, 1));
        getRxManager().addObserver(mModel.login(requestBody)
                , new RxObservableListener<LoginResponse>(mView) {

                    @Override
                    public void onNext(LoginResponse result) {
                        String msg = "登录失败";
                        if (result != null) {
                            if (result.code == 0) {
                                Auth.putToken(result.ut);
                                if (result.data != null) {
                                    if (!TextUtils.isEmpty(result.data.mobile)) {
                                        Auth.putInfo(result.data);
                                         return;
                                    } else {
                                        msg = "手机号为空，请联系系统管理人员";
                                    }
                                } else {
                                    msg = "缺少必要信息";
                                }
                            } else if (!TextUtils.isEmpty(result.message)) {
                                msg = result.message;
                            }
                        }
                        mView.loginFailed(msg);
                    }
                });
    }


}
