package com.gwdtz.springboot.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * @program: springboot
 * @Date: 2021-2-25 16:55
 * @Author: Miss.Chenmf
 * @Description:
 */
public class EncodeUtils {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";



    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * Md5 encode (本系统的md5加密)
     *
     * @param rawPass 密码明文
     *            the password
     * @return the string 加密后的密文
     */
    @SuppressWarnings("deprecation")
    public static String md5Encode(String rawPass){
        MessageDigest md5=null;
        try {
            md5=MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray=rawPass.toCharArray();
        byte[] byteArray=new byte[charArray.length];
        for(int i=0;i<charArray.length;i++){
            byteArray[i]=(byte)charArray[i];
        }
        byte[] md5Bytes=md5.digest(byteArray);
        StringBuffer hexValue=new StringBuffer();
        for(int i=0;i<md5Bytes.length;i++){
            int val=((int)md5Bytes[i])&0xff;
            if(val<16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 必须是数字和字母组合
     * @param pwd
     * @return
     */
    public static Result checkPwdFormat(String pwd){
        if(null == pwd || "".equals(pwd)){
            return new Result(CODE.ERROR,null,"密码不能为空！");
        }
        if(pwd.length() < 6){
            return new Result(CODE.ERROR,null,"密码长度不能少于6位！");
        }
        boolean isSuccess = (!Pattern.compile("[^a-z]{1,}").matcher(pwd).matches()
                || !Pattern.compile("[^A-Z]{1,}").matcher(pwd).matches())
                && !Pattern.compile("[^0-9]{1,}").matcher(pwd).matches();
        if(isSuccess){
            return new Result(CODE.ERROR,null,"验证通过！");
        }else{

            return new Result(CODE.ERROR,null,"密码必须是由数字和字母组合！");
        }

    }
}
