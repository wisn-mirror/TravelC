package com.travel.account;

import com.travel.account.bean.LoginResponse;
import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.constants.ApiUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HEAD;
import retrofit2.http.POST;

/**
 * Created by Wisn on 2018/5/29 下午2:09.
 */
public interface AccountApi {

    @POST(ApiUrl.account.Login)
    @HEAD
    Observable<LoginResponse> login(@Body RequestBody loginRequest);

    @POST(ApiUrl.account.updatePassWord)
    Observable<BaseResponse<Integer>> changePassword(@Body RequestBody loginRequest);

    @POST(ApiUrl.account.updatePassWord)
    Observable<BaseResponse> updateMerchantStore(@Body RequestBody loginRequest);

    @POST(ApiUrl.account.loginOut)
    Observable<BaseResponse> loginOut(@Body RequestBody loginRequest);


}
