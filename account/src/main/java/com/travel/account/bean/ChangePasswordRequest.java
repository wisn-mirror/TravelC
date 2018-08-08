package com.travel.account.bean;

/**
 * Created by Wisn on 2018/6/7 下午2:54.
 */
public class ChangePasswordRequest {

    /**
     * password : liuhaijun123456
     * password1 : 123456
     * password2 : 123456
     * ut : 28c3335a499c4d9b860abc66ea239ee2
     */

    public String password;
    public String password1;
    public String password2;
    public String ut;

    public ChangePasswordRequest(String password, String password1, String password2, String ut) {
        this.password = password;
        this.password1 = password1;
        this.password2 = password2;
        this.ut = ut;
    }

    public ChangePasswordRequest(String password, String password1, String password2) {
        this.password = password;
        this.password1 = password1;
        this.password2 = password2;
    }
}
