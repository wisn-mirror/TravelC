package com.travel.library.view.webview;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by Wisn on 2018/5/4 下午6:39.
 */

public class X5WebView extends WebView {
    private OnScrollChangeListener mOnScrollChangeListener;

    public X5WebView(Context context) {
        super(context);
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, b);
        init();
    }

    private void init() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.setWebContentsDebuggingEnabled(true);
        addJavascriptInterface(new JavaToH5Api(getContext().hashCode()), "mobileAPI");

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        // 当前webview的高度
        float webnow = getHeight() + getScrollY();
        if (Math.abs(webcontent - webnow) < 1) {
            //处于底端
            if (mOnScrollChangeListener != null)
                mOnScrollChangeListener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            //处于顶端
            if (mOnScrollChangeListener != null)
                mOnScrollChangeListener.onPageTop(l, t, oldl, oldt);
        } else {
            if (mOnScrollChangeListener != null)
                mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {

        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}