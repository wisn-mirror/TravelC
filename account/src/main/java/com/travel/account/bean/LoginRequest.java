package com.travel.account.bean;

/**
 * Created by Wisn on 2018/5/29 下午2:46.
 */
public class LoginRequest {
    public String  username;
    public String  password;
    public int  type;

    public LoginRequest(String username, String password, int type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
}
