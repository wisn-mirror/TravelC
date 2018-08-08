package com.travel.library.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.utils.Utils;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Wisn on 2018/5/5 下午9:19.
 */

public class BaseApplication extends MultiDexApplication {

    private BaseApplication instance;

    public BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
//        ARouter.debuggable();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();     // 打印日志
        ARouter.init(this);
        initX5();
    }

    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) { // TODO Auto-generated method stub
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
