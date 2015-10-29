package com.tz.xiyoulibrary.activity.login.view;

public interface ILoginView {
	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);
	
	boolean getIsSavePass();
	
	void setIsSavePass(boolean isSavePass);

	void showDialog();

	void hideDialog();

	void showMsg(String msg);

	void pushMainActivity();
}
