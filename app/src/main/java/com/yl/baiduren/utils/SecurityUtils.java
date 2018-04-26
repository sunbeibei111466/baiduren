package com.yl.baiduren.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jxdyf09 on 15-5-7.
 */
public class SecurityUtils {


    public static final String privateKeyTest = "yl_api_security_md5_signature_8kokszxy22zoq6fagr2";

    /**
     * 获取MD5
     *
     * @param content
     * @return
     */
    public static byte[] getMD5(String content) {

        String ss="1";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取签名
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String md5Signature(String content, String privateKey) {


        byte md5Bytes[]=getMD5(replaceBlank(content) + privateKey);
        StringBuffer hexValue = new StringBuffer();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    /**
     * 替换空格
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }




    /**
     * 校验签名
     *
     * @param content
     * @param privateKey
     * @param signature
     * @return
     */
    public static boolean md5Verify(String content, String privateKey, String signature) {
        String sign = Base64.encodeToString(getMD5(content + privateKey), Base64.DEFAULT);
        return sign.equals(signature);
    }


}
