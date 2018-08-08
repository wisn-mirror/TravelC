package com.travel.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.travel.library.commons.constants.Constants;
import com.travel.library.utils.LogUtilsLib;
import com.travel.library.utils.PreferencesUtils;

import java.io.File;

/**
 * Created by Wisn on 2018/5/16 下午1:11.
 */
public class DownloadReceiver extends BroadcastReceiver {
    private static final String TAG = "DownApkReceiver";
    DownloadManager mManager;
    Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            long downloadid = PreferencesUtils.getLong(Constants.DownloadId);
            if (downloadid != 0 && downloadid == downloadApkId) {
                checkDownloadStatus(context, downloadApkId);
            }
        }
    }

    private void checkDownloadStatus(Context context, long downloadId) {
        mManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = mManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    installApk(context);
                    break;
                case DownloadManager.STATUS_FAILED:
                    LogUtilsLib.d(TAG, "下载失败.....");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    LogUtilsLib.d(TAG, "正在下载.....");
                    break;
                default:
                    break;
            }
        }
    }

    private void installApk(Context context) {
        String path = PreferencesUtils.getString(Constants.DownloadPath);
        if (path != null) {
            File file = new File(path);
            if (file != null && file.exists()) {
                Intent install = new Intent("android.intent.action.VIEW");
                Uri downloadFileUri = Uri.fromFile(file);
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            } else {
                LogUtilsLib.d(TAG, "下载失败");
            }
        } else {
            LogUtilsLib.d(TAG, "apkName 为 null");
        }
    }
}
