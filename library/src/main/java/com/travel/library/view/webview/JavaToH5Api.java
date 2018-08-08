package com.travel.library.view.webview;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Wisn on 2018/5/16 上午9:31.
 */
public class JavaToH5Api {

    private int mTag;

    public JavaToH5Api(int tag) {
        mTag = tag;
    }

    /**
     * 原声方法注入， 供js调用
     *
     * @param params
     */
    @android.webkit.JavascriptInterface
    public void postMessage(String params) {
        if (!TextUtils.isEmpty(params)) {
            try {
                JSONObject json = new JSONObject(params);
                String function = json.optString("function");
                String callback = json.optString("callback");
                String param = json.optString("param");
                Log.e("postMessage","function:"+function+" params:"+params+" callback:"+callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
