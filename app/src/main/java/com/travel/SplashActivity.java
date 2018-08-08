package com.travel;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.commons.Auth;
import com.travel.library.commons.common.CommonActivity;
import com.travel.library.commons.constants.ARoutePath;


/**
 * Created by Wisn on 2018/5/11 上午9:10.
 */
public class SplashActivity extends CommonActivity {

    @Override
    public int bindLayout() {
        return R.layout.app_activity_spalsh;
    }

    @Override
    public void initView(Activity activity) {

    }

    @Override
    public void initData(Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = Auth.getToken();
              /*  if (TextUtils.isEmpty(token) && storeInfo != null && storeInfo.merchantId != 0) {
                    ARouter.getInstance().build(ARoutePath.account.LoginActivity).navigation();
                } else {
                    ARouter.getInstance().build(ARoutePath.App.MainActivity).navigation();
                }*/
                ARouter.getInstance().build(ARoutePath.App.MainActivity).navigation();

                finish();
            }
        }, 1000);
    }

}
