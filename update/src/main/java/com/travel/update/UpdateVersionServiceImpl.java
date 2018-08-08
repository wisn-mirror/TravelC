package com.travel.update;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.bean.update.UpdateVersionRequest;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.constants.Constants;
import com.travel.library.commons.constants.SPConstants;
import com.travel.library.commons.service.UpdateVersionService;
import com.travel.library.net.RetrofitManager;
import com.travel.library.net.rx.NetWorkCodeException;
import com.travel.library.net.rx.RxSubscriber;
import com.travel.library.utils.PreferencesUtils;
import com.travel.library.utils.ToastUtils;
import com.travel.library.utils.Utils;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Wisn on 2018/6/15 下午1:25.
 */
@Route(path = ARoutePath.update.UpdateVersionServiceImpl)
public class UpdateVersionServiceImpl implements UpdateVersionService {

    @Override
    public void checkUpdateVersion(UpdateVersionRequest updateVersionRequest, final UpdateCallback updateCallback) {
        RetrofitManager.getApiService(UpdateApi.class)
                .update(updateVersionRequest.appVersionCode,
                        updateVersionRequest.companyId,
                        updateVersionRequest.platformId,
                        updateVersionRequest.uniqueCode,
//                        updateVersionRequest.appChannel,
                        updateVersionRequest.versionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<BaseResponse<NewAppVersionResponse>>() {
                    @Override
                    public void _onNext(BaseResponse<NewAppVersionResponse> newAppVersionResponse) {
                        if (newAppVersionResponse != null
                                && newAppVersionResponse.code == 0
                                && newAppVersionResponse.data != null && newAppVersionResponse.data.updateFlag == 1) {
                            if (newAppVersionResponse.data.updateType == 1) {
                                //提示升级
                                if (newAppVersionResponse.data.needUpdateHintNum > 0) {
                                    //有提示次数的提示
                                    PreferencesUtils.putInt(newAppVersionResponse.data.versionCode + SPConstants.update.needUpdateHintNum
                                            , newAppVersionResponse.data.needUpdateHintNum);
                                    int tipCount = PreferencesUtils.getInt(newAppVersionResponse.data.versionCode + SPConstants.update.tipUpdateHintNum);
                                    if (tipCount >= newAppVersionResponse.data.needUpdateHintNum) {
                                        //已经到达了最大提醒次数，优化用户体验，不在提示这一版本升级
                                        return;
                                    } else {
                                        tipCount++;
                                        PreferencesUtils.putInt(newAppVersionResponse.data.versionCode + SPConstants.update.tipUpdateHintNum, tipCount);
                                    }
                                }
                                updateCallback.newVersionCallBack(false, newAppVersionResponse.data.obtainUrl, newAppVersionResponse.data.versionCode, newAppVersionResponse.data.describe);

                            } else if (newAppVersionResponse.data.updateType == 2) {
                                //强制升级
                                updateCallback.newVersionCallBack(true, newAppVersionResponse.data.obtainUrl, newAppVersionResponse.data.versionCode, newAppVersionResponse.data.describe);

                            }
                        }
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {

                    }

                    @Override
                    public void _onComplete() {

                    }
                });

    }

    public void addDownloadTask(String downloadUrl, String title, String description) {
        try {
            DownloadManager downloadManager = (DownloadManager) Utils.getApp().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
            String apkName = System.currentTimeMillis() + ".apk";
            File file = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + apkName);
            if (file != null && file.exists()) {
                file.delete();
            }
            request.setDestinationInExternalFilesDir(Utils.getApp(), Environment.DIRECTORY_DOWNLOADS,
                    apkName);
            request.setTitle(title);
            request.setDescription(description);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long downloadId = downloadManager.enqueue(request);
            PreferencesUtils.putLong(Constants.DownloadId, downloadId);
            PreferencesUtils.putString(Constants.DownloadPath, file.getAbsolutePath());
            ToastUtils.show("已添加升级下载任务");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init(Context context) {
    }
}
