package com.travel.library.debug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.travel.library.commons.constants.ActionName;
import com.travel.library.commons.constants.Constants;
import com.travel.library.utils.PreferencesUtils;
import com.travel.library.utils.ToastUtils;
import com.travel.library.utils.Utils;

/**
 * Created by Wisn on 2018/8/1 上午10:33.
 */
public class TokenDebug {

    public static void getToken() {
        //注册token 结果回调监听
        TokenResponseReceiverd tokenResponseReceiverd = new TokenResponseReceiverd();
        IntentFilter intentFilter = new IntentFilter(ActionName.INFO_RESPONSE_ACTION);
        Utils.getApp().registerReceiver(tokenResponseReceiverd, intentFilter);
        //发送获取token请求
        Intent responseIntent = new Intent(ActionName.INFO_REQUEST_ACTION);
        Utils.getApp().sendBroadcast(responseIntent);
    }

    public static class TokenResponseReceiverd extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActionName.INFO_RESPONSE_ACTION.equals(intent.getAction())) {
                String token = intent.getStringExtra(Constants.TOKEN);
                //todo 存储token
                PreferencesUtils.putString(Constants.TOKEN,token);
                ToastUtils.show(token);
            }
        }
    }
}
