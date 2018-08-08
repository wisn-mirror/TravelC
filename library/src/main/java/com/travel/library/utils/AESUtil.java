package com.travel.library.utils;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ody on 2016/12/26.
 */
public class AESUtil {
    //设置16位长度的密钥,保证与sql加密结果一致
//    private static final String stringKey = "1fi;qPa7utddahWy";
    private static final byte[] stringKey = {49,102,105,59,113,80,97,55,117,116,100,100,97,104,87,121};

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return 加密成功返回Base64编码的字符串,加密失败返回null,传入的内容已经加密过则直接返回原文
     */
    public static String encrypt(String content) {
            if(TextUtils.isEmpty(content))return content;
            try {
                if (content.indexOf("@%^*") == 0) {
                    return content;
                }
                SecretKey key = new SecretKeySpec(stringKey,"AES");
                Cipher cipher = Cipher.getInstance("AES");// 创建密码器
                byte[] byteContent = content.getBytes("utf-8");
                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(byteContent);
                return "@%^*" +Base64Utils.encode(result); // 加密(最前面拼接特殊字符串)
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
    }

    /**解密
     * @param content  待解密内容
     * @return 解密成功后返回解密后的明文,无需解密直接返回原文,解密失败返回null
     */
    public static String decrypt(String content) {
        if(TextUtils.isEmpty(content))return content;
        if (content.indexOf("@%^*") == 0) {
            //截取 @%^* 后面的内容作为需要解密的密文
            content = content.substring(4, content.length());
            try {
                SecretKey key = new SecretKeySpec(stringKey,"AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(Base64Utils.decode(content));
                return new String(result,"UTF-8"); // 解密
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }else  if (content.indexOf("%^*") == 0) {
            //截取 @%^* 后面的内容作为需要解密的密文
            content = content.substring(3, content.length());
            try {
                SecretKey key = new SecretKeySpec(stringKey,"AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(Base64Utils.decode(content));
                return new String(result,"UTF-8"); // 解密
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return content;
    }

}
