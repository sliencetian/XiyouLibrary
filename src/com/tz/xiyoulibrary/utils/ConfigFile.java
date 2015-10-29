package com.tz.xiyoulibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author tianzhao ≈‰÷√Œƒº˛¿‡
 */
public class ConfigFile {

	private static SharedPreferences sp;

	private static final String CONFIG_FILE_NAME = "XiYou_Library";
	private static final String UserName = "username";
	private static final String PassWord = "password";
	private static final String IsSavePassWord = "isSavePass";

	public static void saveUsername(Context context, String username) {
		initSp(context);
		sp.edit().putString(UserName, username).commit();
	}

	public static void savePassword(Context context, String password) {
		initSp(context);
		sp.edit().putString(PassWord, password).commit();
	}

	public static String getUsername(Context context) {
		initSp(context);
		return sp.getString(UserName, "");
	}

	public static String getPassword(Context context) {
		initSp(context);
		return sp.getString(PassWord, "");
	}

	public static void saveIsSavePass(Context context, boolean isSavePass) {
		initSp(context);
		sp.edit().putBoolean(IsSavePassWord, isSavePass).commit();
	}

	public static boolean getIsSavePass(Context context) {
		initSp(context);
		return sp.getBoolean(IsSavePassWord, false);
	}

	private static void initSp(Context context) {
		sp = context.getSharedPreferences(CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
	}
}
