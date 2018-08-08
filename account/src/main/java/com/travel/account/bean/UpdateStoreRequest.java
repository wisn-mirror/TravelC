package com.travel.account.bean;

/**
 * Created by Wisn on 2018/5/29 下午4:57.
 */
public class UpdateStoreRequest {

    /**
     * businessState : 1
     * manualReceiveStart : 8:00
     * manualReceiveEnd : 18:00
     */

    public int businessState;
    public long id;
    public long merchantId;
    public String manualReceiveStart;
    public String manualReceiveEnd;

    public UpdateStoreRequest(long id,long merchantId,int businessState, String manualReceiveStart, String manualReceiveEnd) {
        this.id=id;
        this.merchantId=merchantId;
        this.businessState = businessState;
        this.manualReceiveStart = manualReceiveStart;
        this.manualReceiveEnd = manualReceiveEnd;
    }
}
