package com.tz.xiyoulibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author tianzhao 配置文件类
 */
public class ConfigFile {

	private static SharedPreferences sp;

	private static final String CONFIG_FILE_NAME = "XiYou_Library";
	private static final String UserName = "username";
	private static final String PassWord = "password";
	private static final String IsSavePassWord = "isSavePass";
	private static final String IsAutoLogin = "isAutoLogin";

	// 消息通知
	private static final String SETTING_MESSAGE_NOTIC = "setting_message_notic";
	// 网络图片显示
	private static final String SETTING_NET = "setting_net";

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
	
	public static void saveIsAutoLogin(Context context, boolean isAutoLogin) {
		initSp(context);
		sp.edit().putBoolean(IsAutoLogin, isAutoLogin).commit();
	}

	public static boolean getIsAutoLogin(Context context) {
		initSp(context);
		return sp.getBoolean(IsAutoLogin, false);
	}

	public static boolean getMessageNotic(Context context) {
		initSp(context);
		return sp.getBoolean(SETTING_MESSAGE_NOTIC, true);
	}

	public static void saveMessageNotic(Context context,
			boolean message_notic_status) {
		initSp(context);
		sp.edit().putBoolean(SETTING_MESSAGE_NOTIC, message_notic_status).commit();
	}

	public static boolean getNet(Context context) {
		initSp(context);
		return sp.getBoolean(SETTING_NET, true);
	}

	public static void saveNet(Context context, boolean net_status) {
		initSp(context);
		sp.edit().putBoolean(SETTING_NET, net_status).commit();
	}

	private static void initSp(Context context) {
		sp = context.getSharedPreferences(CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
	}
}
