package com.travel.account.login;


import com.travel.account.AccountApi;
import com.travel.account.bean.LoginResponse;
import com.travel.library.beans.BaseResponse;
import com.travel.library.net.RetrofitManager;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Wisn on 2018/5/29 下午2:35.
 */
public class LoginM implements Login.LoginM {

    @Override
    public Observable<LoginResponse> login(RequestBody requestBody) {
        return RetrofitManager.getApiService(AccountApi.class).login(requestBody);
    }


}
