package com.travel.library.utils;

import java.text.DecimalFormat;

/**
 * Created by Wisn on 2018/6/9 下午10:20.
 */
public class StringUtils {
    /**
     * @param number
     * @param isdouble 是否两位小数
     *
     * @return
     */
    public static String getNumber(double number, boolean isdouble) {
        DecimalFormat df = new DecimalFormat(isdouble ? "#,###.00" : "#,###");
        return df.format(number);
    }

    /**
     * 固定位数的数字，不够时前面加0
     *
     * @param length 长度
     * @param number 目标数字
     *
     * @return
     */
    public static String getFormat(int length, int number) {
        return String.format("%0" + String.valueOf(length) + "d", number);
    }

    /**
     * 格式化字符串，每三位用逗号隔开
     *
     * @param str
     *
     * @return
     */
    public static String addComma(String str) {
        str = new StringBuilder(str).reverse().toString();     //先将字符串颠倒顺序
        if (str.equals("0")) {
            return str;
        }
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * 3 + 3 > str.length()) {
                str2 += str.substring(i * 3, str.length());
                break;
            }
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        //最后再将顺序反转过来
        String temp = new StringBuilder(str2).reverse().toString();
        //将最后的,去掉
        return temp.substring(0, temp.lastIndexOf(",")) + temp.substring(temp.lastIndexOf(",") + 1, temp.length());
    }
}
