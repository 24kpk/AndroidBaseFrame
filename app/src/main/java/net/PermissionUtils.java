package net;

import android.Manifest;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description:
 */
public class PermissionUtils {

    public static String[] ACCESS_INTERNET = new String[]{Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.INTERNET};


    public static String ACCESS_INTERNET_INFO = "本功能需要网络权限";
}
