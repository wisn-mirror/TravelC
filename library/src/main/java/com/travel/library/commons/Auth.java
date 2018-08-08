package com.travel.library.commons;

import com.travel.library.commons.bean.UserInfo;
import com.travel.library.commons.constants.SPConstants;
import com.travel.library.utils.AESUtil;
import com.travel.library.utils.PreferencesUtils;

/**
 * Created by Wisn on 2018/5/30 上午11:10.
 */
public class Auth {

    public static String getToken() {
        return PreferencesUtils.getString(SPConstants.account.Token);
    }

    public static void putToken(String token) {
        PreferencesUtils.putString(SPConstants.account.Token, token);
    }

    public static void putInfo(UserInfo userInfo) {
        PreferencesUtils.putLong(SPConstants.account.UserInfo.accountId, userInfo.id);
        PreferencesUtils.putString(SPConstants.account.UserInfo.mobile, userInfo.mobile);
        PreferencesUtils.putString(SPConstants.account.UserInfo.imageUrl, userInfo.headPicUrl);
        PreferencesUtils.putString(SPConstants.account.UserInfo.nickName, userInfo.nickname == null ? "伊客" : userInfo.nickname);
        PreferencesUtils.putString(SPConstants.account.UserInfo.sex, userInfo.sex);
    }

    public static UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.id = PreferencesUtils.getLong(SPConstants.account.UserInfo.accountId);
        userInfo.mobile = AESUtil.decrypt(PreferencesUtils.getString(SPConstants.account.UserInfo.mobile));
        userInfo.headPicUrl = PreferencesUtils.getString(SPConstants.account.UserInfo.imageUrl);
        userInfo.nickname = PreferencesUtils.getString(SPConstants.account.UserInfo.nickName);
        userInfo.sex = PreferencesUtils.getString(SPConstants.account.UserInfo.sex);
        return userInfo;
    }


}
