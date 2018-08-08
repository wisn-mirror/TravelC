package com.travel.library.commons.api;

/**
 * Created by Wisn on 2018/4/7 下午3:26.
 */

public interface PublicApi {
    /**
     * 接单
     * @param sysSource
     * @param orderCode
     * @return
     *//*
    @GET(ApiUrl.order.acceptOrder)
    Observable<BaseResponse<Boolean>> acceptOrder(@Query("sysSource") String sysSource, @Query("orderCode") String orderCode);

    *//**
     * 拒单
     * @param requestBody
     * @return
     *//*
    @POST(ApiUrl.order.refuseOrder)
    Observable<BaseResponse<Boolean>> refuseOrder(@Body RequestBody requestBody);

    *//**
     * 订单列表
     * @param requestBody
     * @return
     *//*
    @POST(ApiUrl.order.orderList)
    Observable<BaseResponse<OrderListBean>> orderList(@Body RequestBody requestBody);

*/
}
