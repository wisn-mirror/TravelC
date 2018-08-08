package com.travel.library.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Created by wisn on 2017/10/16.
 * <tbody>
 * <tr>
 * <td>MD5</td>
 * <td>1+</td>
 * </tr>
 * <tr>
 * <td>SHA-1</td>
 * <td>1+</td>
 * </tr>
 * <tr>
 * <td>SHA-224</td>
 * <td>1&ndash;8,22+</td>
 * </tr>
 * <tr>
 * <td>SHA-256</td>
 * <td>1+</td>
 * </tr>
 * <tr>
 * <td>SHA-384</td>
 * <td>1+</td>
 * </tr>
 * <tr>
 * <td>SHA-512</td>
 * <td>1+</td>
 * </tr>
 */

public class SHAMD5Utils {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
                                         '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static MessageDigest messagedigest = null;

    public static String getMD5(String str, File file) {
        if (str != null) return getSHAStr(str, "MD5");
        return getSHAFile(file, "MD5");
    }

    public static String getSHA1(String str, File file) {
        if (str != null) return getSHAStr(str, "SHA-1");
        return getSHAFile(file, "SHA-1");

    }

    public static String getSHA512(String str, File file) {
        if (str != null) return getSHAStr(str, "SHA-512");
        return getSHAFile(file, "SHA-512");

    }

    public static String getSHA384(String str, File file) {
        if (str != null) return getSHAStr(str, "SHA-384");
        return getSHAFile(file, "SHA-384");

    }

    public static String getSHA256(String str, File file) {
        if (str != null) return getSHAStr(str, "SHA-256");
        return getSHAFile(file, "SHA-256");

    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5File(File file) throws IOException,
            NoSuchAlgorithmException {
        messagedigest = MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                                             file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }


    /**
     * @param file  大文件
     * @param algorithm
     *
     * @return
     */
    private static String getSHAFile(File file, String algorithm) {
        try {
            messagedigest = MessageDigest.getInstance(algorithm);
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                                                 file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("SHAMD5Utils", algorithm + " 编码出错！");
        } catch (UnsupportedEncodingException e) {
            Log.e("SHAMD5Utils", "UTF-8 ,SHA编码操作，不支持字符集！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param in
     * @param algorithm
     *
     * @return
     */
    private static String getSHAStr(String in, String algorithm) {
        String str = null;
        try {
            messagedigest = MessageDigest.getInstance(algorithm);
            messagedigest.update(in.getBytes("UTF-8"));
            str = bufferToHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("SHAMD5Utils", algorithm + " 编码出错！");
        } catch (UnsupportedEncodingException e) {
            Log.e("SHAMD5Utils", "UTF-8 ,SHA编码操作，不支持字符集！");
        }
        return str;
    }

    /**
     * 获取文件CRC32码
     *
     * @return String
     */
    public static String getCRC32(File file) {
        CRC32 crc32 = new CRC32();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }
}
