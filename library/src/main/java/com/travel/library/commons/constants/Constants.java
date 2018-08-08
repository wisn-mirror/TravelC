package com.travel.library.commons.constants;

/**
 * Created by Wisn on 2018/6/14 下午4:04.
 */
public class Constants {
    public static final String  OrderCode="OrderCode";
    public static final String  Index="Index";
    public static final String  DownloadPath="DownloadPath";
    public static final String  DownloadId="DownloadId";
//    public static final String  consumer_hotline="400-8819777";
    public static final String  consumer_hotline="021-67861617";
    //单一评论
    public final static int COMMENT_TYPE_SINGLE = 0;
    //回复评论
    public final static int COMMENT_TYPE_REPLY = 1;
    public static final String TOKEN = "token";

    public static class App {

    }

    public static class address {
    }

    public static class homepager {
    }


    public static class account {

    }

    public static class update {
    }

    public static class login {
    }

    public static class mediaselector {
        //图片选择的结果
        public static final String PHOTO_SELECT_RESULT = "select_result";
        //图片选择request code
        public static final int PHOTO_REQUEST_CODE = 0x00000011;
        //图片选择的结果
        public static final String SELECT_RESULT = "select_result";

        //最大的图片选择数
        public static final String MAX_SELECT_COUNT = "max_select_count";
        //是否单选
        public static final String IS_SINGLE = "is_single";
        //是否使用拍照功能
        public static final String USE_CAMERA = "is_camera";
        //原来已选择的图片
        public static final String SELECTED = "selected";
        //初始位置
        public static final String POSITION = "position";
        //是否包含视频
        public static final String ContainsVideo = "ContainsVideo";
        //是否选择视频后就只能选一个
        public static final String isSingleVideo = "isSingleVideo";

        public static final String IS_CONFIRM = "is_confirm";

        public static final int RESULT_CODE = 0x00000012;

        public static final int REQUEST_CODE = 0x00000011;
    }

    public static class zxing {
        public static final String AutoEnlarged = "autoEnlarged";
        public static final String Result = "result";
        public static final int QR_CODE = 0x00000011;
    }



}
