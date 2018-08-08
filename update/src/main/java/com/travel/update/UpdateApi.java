package com.travel.update;

import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.constants.ApiUrl;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Wisn on 2018/4/7 下午3:26.
 */

public interface UpdateApi {

    @GET(ApiUrl.update.upgrade)
    Observable<BaseResponse<NewAppVersionResponse>> update(
            @Query("appVersionCode") String appVersionCode
            , @Query("companyId") long companyId
            , @Query("platformId") int platformId
            , @Query("uniqueCode") String uniqueCode
//            , @Query("appChannel") String appChannel
            , @Query("versionCode") String versionCode);
}
