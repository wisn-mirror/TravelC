package com.travel;

import com.travel.library.base.BaseApplication;
import com.travel.library.commons.constants.Config;
//import com.netease.nim.demo.InitIM;
import com.travel.library.debug.TokenDebug;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Wisn on 2018/5/2 上午11:15.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLYID, false);
        initConfig();
        TokenDebug.getToken();
    }

    private void initConfig() {
        Config.BASE_URL = BuildConfig.H5URL;
    }

}
