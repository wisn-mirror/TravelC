package com.travel.library.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.travel.library.R;
import com.travel.library.commons.common.CommonActivity;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.utils.ToastUtils;
import com.travel.library.view.webview.X5WebView;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Wisn on 2018/5/7 下午1:32.
 */
@Route(path = ARoutePath.App.WebViewActivity)
public class WebViewActivity extends CommonActivity {
    private static final String URL="url";


    private X5WebView webView;
    private TextView tv_title;

    public static void startWeb(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }


    @Override
    public int bindLayout() {
        return R.layout.lib_activity_webview;
    }

    @Override
    public void initView(Activity activity) {
        webView = (X5WebView) findViewById(R.id.webview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {

                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        webView.loadUrl("javascript:callJS()");
                    }
                });
            }
        });
        webView.loadUrl(getIntent().getStringExtra(URL));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(WebView webView, String url, String message, final JsResult jsResult) {
                ToastUtils.show("message"+message);
                return super.onJsConfirm(webView, url, message, jsResult);
               /* AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsResult.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;*/
            }
        });
    }

    @Override
    public void initData(Context context) {

    }
}
