package com.tz.xiyoulibrary.activity.login.model;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface ILoginModel {
	static final int LOGIN_SUCCESS = 1;//
	static final int LOGIN_FAILURE = 2;//
	static final int ACCOUNT_ERROR = 3;// ’À∫≈¥ÌŒÛ£¨√‹¬Î¥ÌŒÛªÚ’Àªß≤ª¥Ê‘⁄

	void Login(RequestQueue queue, String username, String password,
			CallBack<LoginModel> callBack);

	String getUsername();

	String getPassword();
}
