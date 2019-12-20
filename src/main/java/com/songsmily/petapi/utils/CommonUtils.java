package com.songsmily.petapi.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CommonUtils {
    public static String setPassword(String password){
        return new Md5Hash(password,null,3).toString();
    }
}
