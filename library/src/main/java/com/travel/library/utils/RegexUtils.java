package com.travel.library.utils;

/**
 * Created by wisn on 2017/10/5.
 */

public class RegexUtils {
    /**
     * 身份证号
     */
    public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    /**
     * "www."可省略不写
     */
    public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";
    /**
     * URL
     */
    public static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";

    /**
     * 支持130——139、150——153、155——159、180、183、185、186、188、189号段
     */
    public static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|5{1}[012356789]{1}|8{1}[035689]{1})\\d{8}$";

    /**
     * ipv4
     */
    public static final String IPV4 = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";


    public static boolean isUrl(String url) {
        return url.matches(URL);
    }

    public static boolean isPhone(String phone) {
        return phone.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean isIpv4(String ip) {
        return ip.matches(IPV4);
    }

    public static boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isIDCard(String idCard) {
        return idCard.matches(ID_CARD);
    }

}
