package com.tz.xiyoulibrary.activity.login.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface ILoginModel {
	static final int LOGIN_ING = 1;//
	static final int LOGIN_SUCCESS = 2;//
	static final int LOGIN_FAILURE = 3;//
	static final int ACCOUNT_ERROR = 4;// ’À∫≈¥ÌŒÛ£¨√‹¬Î¥ÌŒÛªÚ’Àªß≤ª¥Ê‘⁄

	void login(RequestQueue queue, String username, String password,
			CallBack<LoginModel> callBack);

	void getUserInfo(RequestQueue queue, String session,
			CallBack<LoginModel> callBack);

	String getUsername(Context context);

	String getPassword(Context context);

	boolean getIsSavePass(Context context);

	void setIsSavePass(Context context, boolean isSavePass);

	boolean getIsAutoLogin(Context context);

	void setIsAutoLogin(Context context, boolean isAutoLogin);

	void saveUsernameAndPassword(Context context, String username,
			String password);

	boolean checkInput(String username, String password);
}
