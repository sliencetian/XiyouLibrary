package com.tz.xiyoulibrary.activity.login.presenter;

import com.tz.xiyoulibrary.activity.login.model.ILoginModel;
import com.tz.xiyoulibrary.activity.login.model.LoginModel;
import com.tz.xiyoulibrary.activity.login.view.ILoginView;

public class LoginPresenter {

	private ILoginView mLoginView;
	private ILoginModel mLoginModel;

	public LoginPresenter(ILoginView view) {
		this.mLoginView = view;
		mLoginModel = new LoginModel();
	}

	public void setUsername() {
		mLoginView.setUsername(mLoginModel.getUsername());
	}

	public void setPassword() {
		mLoginView.setPassword(mLoginModel.getPassword());
	}
}
