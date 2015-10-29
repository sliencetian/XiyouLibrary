package com.tz.xiyoulibrary.activity.login.presenter;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
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

	public void Login(RequestQueue queue) {
		mLoginModel.login(queue, mLoginView.getUsername(), mLoginView.getPassword(),
				new CallBack<LoginModel>() {

					@Override
					public void getModel(LoginModel model) {
						switch (model.state) {
						case ILoginModel.LOGIN_ING:
							mLoginView.showDialog();
							break;
						case ILoginModel.LOGIN_FAILURE:
						case ILoginModel.ACCOUNT_ERROR:
							mLoginView.hideDialog();
							mLoginView.showMsg(model.msg);
							break;
						case ILoginModel.LOGIN_SUCCESS:
							mLoginView.hideDialog();
							mLoginView.pushMainActivity();
							break;
						}
					}
				});
	}
	
	public void getUserInfo(RequestQueue queue){
		
	}

	public void setUsername(Context context) {
		mLoginView.setUsername(mLoginModel.getUsername(context));
	}

	public void setPassword(Context context) {
		mLoginView.setPassword(mLoginModel.getPassword(context));
	}

	public void setIsSavePass(Context context) {
		mLoginView.setIsSavePass(mLoginModel.getIsSavePass(context));
	}

	public void saveIsSavePass(Context context) {
		mLoginModel.setIsSavePass(context, mLoginView.getIsSavePass());
	}

	public void saveUsernameAndPassword(Context context) {
		mLoginModel.saveUsernameAndPassword(context, mLoginView.getUsername(),
				mLoginView.getPassword());
	}
}
