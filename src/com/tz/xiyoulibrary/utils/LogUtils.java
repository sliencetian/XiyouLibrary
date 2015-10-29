package com.tz.xiyoulibrary.utils;

import android.util.Log;

public class LogUtils {

	private static boolean IS_DEBUG = true;

	public static void d(String Tag, String msg) {
		if (IS_DEBUG) {
			Log.d(Tag, msg);
		}
	}

	public static void i(String Tag, String msg) {
		if (IS_DEBUG) {
			Log.i(Tag, msg);
		}
	}

	public static void e(String Tag, String msg) {
		if (IS_DEBUG) {
			Log.e(Tag, msg);
		}
	}

	public static void w(String Tag, String msg) {
		if (IS_DEBUG) {
			Log.w(Tag, msg);
		}
	}

	public static void v(String Tag, String msg) {
		if (IS_DEBUG) {
			Log.v(Tag, msg);
		}
	}
}
