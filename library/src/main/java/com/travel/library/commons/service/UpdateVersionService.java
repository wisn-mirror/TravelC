package com.travel.library.commons.service;


import com.alibaba.android.arouter.facade.template.IProvider;
import com.travel.library.commons.bean.update.UpdateVersionRequest;

/**
 * Created by Wisn on 2018/6/15 下午12:58.
 */
public interface UpdateVersionService extends IProvider {

    void checkUpdateVersion(UpdateVersionRequest updateVersionRequest,UpdateCallback updateCallback);

    void addDownloadTask(String downloadUrl, String title, String description);


    interface UpdateCallback{
        /**
         *版本升级
         * @param mandatory 是否强制升级
         * @param downloadUrl 下载的url
         * @param versionName
         * @param description  版本描述
         */
        void newVersionCallBack(boolean mandatory,String downloadUrl, String versionName, String description);
    }
}
