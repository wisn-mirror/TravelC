package com.travel.library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

/**
 * Android 操作系统公共类
 */
public class OsUtils {
    private static int mSdkVersion = 0; // SDK版本

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity context){
        try {
            InputMethodManager imeOptions = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imeOptions.isActive()) {
                imeOptions.hideSoftInputFromWindow(context.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Throwable e) {
        }
    }

    /**
     * 获取Android手机操作系统的版本信息
     */
    public static synchronized int getAndroidSDKVersion() {
        if(mSdkVersion > 0){
            return mSdkVersion;
        }

        try {
            mSdkVersion = Build.VERSION.SDK_INT;
        } catch (Throwable e) {
        }

        return mSdkVersion;
    }

    /**
     * Android 2.2 SDK API 8 (FROYO)
     */
    public static boolean isApi8Plus() {
        return getAndroidSDKVersion() >= 8;
    }

    /**
     * Android 2.3.1 API 9 (GINGERBREAD)
     */
    public static boolean isApi9Plus() {
        return getAndroidSDKVersion() >= 9;
    }

    /**
     * Android 3.0 API 11 (HONEYCOMB)
     */
    public static boolean isApi11Plus() {
        return getAndroidSDKVersion() >= 11;
    }

    /**
     * Android 3.1 API 12 (HONEYCOMB_MR1)
     */
    public static boolean isApi12Plus() {
        return getAndroidSDKVersion() >= 12;
    }

    /**
     * Android 4.0, 4.0.1, 4.0.2 API 14 (ICE_CREAM_SANDWICH)
     */
    public static boolean isApi14Plus() {
        return getAndroidSDKVersion() >= 14;
    }

    /**
     * Anroid 4.1 API 16 (JELLY_BEAN)
     */
    public static boolean isApi16Plus() {
        return getAndroidSDKVersion() >= 16;
    }

    /**
     * Anroid 4.2 API 17 (JELLY_BEAN_MR1)
     */
    public static boolean isApi17Plus() {
        return getAndroidSDKVersion() >= 17;
    }

    /**
     * Anroid 4.3 API 18 (JELLY_BEAN_MR2)
     */
    public static boolean isApi18Plus() {
        return getAndroidSDKVersion() >= 18;
    }

    /**
     * Android 4.4 API 19 (KITKAT)
     */
    public static boolean isApi19Plus() {
        return getAndroidSDKVersion() >= 19;
    }

    /**
     * Android 2.2 SDK API 8
     */
    public static boolean isFroyoPlus() {
        return getAndroidSDKVersion() >= 8;
    }

    /**
     * Android 2.3.1 API 9
     */
    public static boolean isGingerbreadPlus() {
        return getAndroidSDKVersion() >= 9;
    }

    /**
     * Android 3.0 API 11
     */
    public static boolean isHoneycombPlus() {
        return getAndroidSDKVersion() >= 11;
    }

    /**
     * Android 3.1 API 12
     */
    public static boolean isHoneycombMR1Plus() {
        return getAndroidSDKVersion() >= 12;
    }

    /**
     *  Anroid 4.1 API 16
     */
    public static boolean isJellyBeanPlus() {
        return getAndroidSDKVersion() >= 16;
    }

    /**
     * Anroid 4.2 API 17
     */
    public static boolean isJellyBeanMR1Plus() {
        return getAndroidSDKVersion() >= 17;
    }

    /**
     *  Anroid 4.3 API 18
     */
    public static boolean isJellyBeanMR2Plus() {
        return getAndroidSDKVersion() >= 18;
    }

    /**
     *  Android 4.4 API 19
     */
    public static boolean isKitKatPlus() {
        return getAndroidSDKVersion() >= 19;
    }

    /**
     * 当前操作系统版本是否为 Android 4.2 或者更高版本
     */
    public static boolean isAndroid4_2_or_plus() {
        return getAndroidSDKVersion() >= 17;
    }

    //========================================================================================
    //    Platform Version	API Level	VERSION_CODE	Notes
    //    Android 4.2, 4.2.2	17	JELLY_BEAN_MR1	Platform Highlights
    //    Android 4.1, 4.1.1	16	JELLY_BEAN	Platform Highlights
    //    Android 4.0.3, 4.0.4	15	ICE_CREAM_SANDWICH_MR1	Platform Highlights
    //    Android 4.0, 4.0.1, 4.0.2	14	ICE_CREAM_SANDWICH
    //    Android 3.2	13	HONEYCOMB_MR2
    //    Android 3.1.x	12	HONEYCOMB_MR1	Platform Highlights
    //    Android 3.0.x	11	HONEYCOMB	Platform Highlights
    //    Android 2.3.4
    //    Android 2.3.3	10	GINGERBREAD_MR1	Platform Highlights
    //    Android 2.3.2
    //    Android 2.3.1
    //    Android 2.3	9	GINGERBREAD
    //    Android 2.2.x	8	FROYO	Platform Highlights
    //    Android 2.1.x	7	ECLAIR_MR1	Platform Highlights
    //    Android 2.0.1	6	ECLAIR_0_1
    //    Android 2.0	5	ECLAIR
    //    Android 1.6	4	DONUT	Platform Highlights
    //    Android 1.5	3	CUPCAKE	Platform Highlights
    //    Android 1.1	2	BASE_1_1
    //    Android 1.0	1	BASE
    //========================================================================================
}
