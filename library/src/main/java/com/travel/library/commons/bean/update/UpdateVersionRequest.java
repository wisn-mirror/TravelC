package com.travel.library.commons.bean.update;

/**
 * Created by Wisn on 2018/6/15 下午1:01.
 */
public class UpdateVersionRequest {
    public String areaCode;
    public String appVersionCode;
    public long companyId;
    public int platformId;
    public String uniqueCode;
    public String appChannel;
    public String versionCode;

    public UpdateVersionRequest( String uniqueCode, String versionName) {
        this.platformId = 0;//0 android 1 ios
        this.uniqueCode = uniqueCode;
        this.versionCode = versionName;
        this.appChannel = "";
        this.areaCode = "";
        this.companyId = 30;
    }
}
