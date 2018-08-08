package com.travel.library.commons.constants;

/**
 * Created by Wisn on 2018/5/22 下午2:20.
 */
public class ApiUrl {
    public static class App {
    }


    public static class account {

        //登录
        public static final String Login = "/ouser-web/mobileLogin/login.do";
        //修改密码
        public static final String updatePassWord = "/ouser-web/user/updatePassWord.do";
        //登出
        public static final String loginOut = "/ouser-web/mobileLogin/exit.do";
    }

    public static class update {
        public static final String upgrade = "http://api.lyf.edu.laiyifen.com/api/app/upgrade";
    }


}
