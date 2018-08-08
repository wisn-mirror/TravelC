package com.travel.library.commons.constants;

/**
 * Created by Wisn on 2018/5/7 下午12:36.
 */

public class ARoutePath {

    public static class App {
        public static final String WebViewActivity = "/app/WebViewActivity";
        public static final String MainActivity = "/app/MainActivity";
        public static final String SchemeFilterActivity = "/app/SchemeFilterActivity";
        public static final String IMChat = "/app/IMChat";
    }

    public static class account {
        public static final String ChangePasswordActivity = "/account/ChangePasswordActivity";
        public static final String LoginActivity = "/account/LoginActivity";
        public static final String SystemSettingActivity = "/account/SystemSettingActivity";
        public static final String MineFragment = "/account/MineFragment";

    }

    public static class update {
        public static final String UpdateVersionServiceImpl = "/update/UpdateVersionServiceImpl";
    }

    public static class homepager {
        public static final String HomeFragment = "/homepager/HomeFragment";
    }


    public static class mediaselector {
        public static final String MediaInfoSelectorActivity = "/mediaselector/MediaInfoSelectorActivity";
    }


    public static class zxing {
        public static final String CaptureActivity = "/zxing/CaptureActivity";
    }
}
