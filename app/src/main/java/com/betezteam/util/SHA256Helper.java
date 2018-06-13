package com.betezteam.util;

import java.security.MessageDigest;

public class SHA256Helper {
    public static String SHA256(String data){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
        } catch (Exception e){}
        return bytesToHex(md.digest());
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    public static String SHA256WithSalt(String data, String salt){
        return SHA256(data+salt);
    }
}
