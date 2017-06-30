package com.chablis.lilosoft.utils;

import android.content.SharedPreferences;

import com.chablis.lilosoft.base.AppContext;


/**
 * SharedPreferences本地数据存储类
 * @author ray
 * @date 2015-03-15
 */
public class PrefUtils {

	private static final String PREF_NAME_CACHE = "com.lilosoft.tdt.cache";
	private static final String PREF_NAME_VARS = "com.outsidescreen.tdt.vars";

	public static SharedPreferences cachePrefs() {
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0);
	}

	public static SharedPreferences varsPrefs() {
		return AppContext.get().getSharedPreferences(PREF_NAME_VARS, 0);
	}

	public static void putCacheLoginState(boolean value) {
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putBoolean("loginstate", value).commit();
	}

	public static boolean getCacheLoginState() {
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getBoolean("loginstate", false);
	}
	public static void saveUserInfo(String info){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("userinfo", info).commit();
	}
	public static String getUserInfo(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("userinfo", "");
	}
	public static void putAreaCode(String info){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("areacode", info).commit();
	}
	public static String getAreaCode(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("areacode", "");
	}
	public static void putIp(String info){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("ip", info).commit();
	}
	public static String getIp(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("ip", "");
	}
	public static void putUpdateIp(String info){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("update_ip", info).commit();
	}
	public static String getUpdateIp(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("update_ip", "");
	}




}
