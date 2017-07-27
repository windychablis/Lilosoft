package com.chablis.repair.utils;

import android.content.SharedPreferences;

import com.chablis.repair.base.AppContext;

public class PrefUtils {

    private static final String PREF_NAME_CACHE = "com.chablis.lilosoft.cache";
    private static final String PREF_NAME_VARS = "com.chablis.lilosoft.vars";

    public static SharedPreferences cachePrefs() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0);
    }

    public static SharedPreferences varsPrefs() {
        return AppContext.get().getSharedPreferences(PREF_NAME_VARS, 0);
    }

    //保存登录状态缓存
    public static void putCacheLoginState(boolean value) {
        SharedPreferences.Editor editor = AppContext.get()
                .getSharedPreferences(PREF_NAME_CACHE, 0).edit();
        editor.putBoolean("loginstate", value).commit();
    }

    //获取登录状态缓存
    public static boolean getCacheLoginState() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
                .getBoolean("loginstate", false);
    }

    //保存用户信息缓存
    public static void saveUserInfo(String info) {
        SharedPreferences.Editor editor = AppContext.get()
                .getSharedPreferences(PREF_NAME_CACHE, 0).edit();
        editor.putString("userinfo", info).commit();
    }

    public static String getUserInfo() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
                .getString("userinfo", "");
    }


    //保存用户名
    public static void saveUserName(String username) {
        SharedPreferences.Editor editor = AppContext.get()
                .getSharedPreferences(PREF_NAME_CACHE, 0).edit();
        editor.putString("username", username).commit();
    }

    public static String getUserName() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
                .getString("username", "");
    }


    //保存用户名
    public static void saveUserPass(String password) {
        SharedPreferences.Editor editor = AppContext.get()
                .getSharedPreferences(PREF_NAME_CACHE, 0).edit();
        editor.putString("password", password).commit();
    }

    public static String getUserPass() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
                .getString("password", "");
    }

    //保存用户名
    public static void saveUrl(String url) {
        SharedPreferences.Editor editor = AppContext.get()
                .getSharedPreferences(PREF_NAME_CACHE, 0).edit();
        editor.putString("url", url).commit();
    }

    public static String getUrl() {
        return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
                .getString("url", "");
    }

    //注销用户缓存
    public static void logout(){
        putCacheLoginState(false);
        saveUserName("");
        saveUserPass("");
    }
}
