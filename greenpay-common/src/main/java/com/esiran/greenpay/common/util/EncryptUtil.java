package com.esiran.greenpay.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String md5(String str){
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (byte aHash : hash) {
                if ((0xff & aHash) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & aHash)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & aHash));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }
    public static String[] encode(String str){
        String md5Str = md5(str);
        String[] out = new String[4];
        for (int i=0;i<out.length;i++){
            String s = md5Str.substring(i*8,(i*8)+8);
            long l = Long.parseLong(s,16);
            long fixL = 0x3FFFFFFF&l;
            StringBuilder sb = new StringBuilder();
            for (int j=0;j<6;j++){
                int n = (int) (fixL%62);
                sb.append(CHARS.charAt(n));
                fixL = fixL >> 5;
            }
            out[i] = sb.toString();
        }
        return out;
    }
}
