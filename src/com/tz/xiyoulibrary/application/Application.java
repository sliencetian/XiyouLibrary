package com.tz.xiyoulibrary.application;

import com.tz.xiyoulibrary.bean.UserBean;


public class Application extends android.app.Application{

	public static String SESSION = "";
	public static UserBean user;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
}
