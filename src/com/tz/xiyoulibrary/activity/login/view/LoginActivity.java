package com.tz.xiyoulibrary.activity.login.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.event.LoginSuccessEvent;
import com.tz.xiyoulibrary.activity.login.presenter.LoginPresenter;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.ypy.eventbus.EventBus;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements ILoginView {

	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.rl_search_actionbar)
	RelativeLayout mRelativeLayoutSearch;

	@ViewById(R.id.et_username_activity_login)
	EditText mEditTextUserName;

	@ViewById(R.id.et_password_activity_login)
	EditText mEditTextPassWord;

	@ViewById(R.id.bt_login_activity_login)
	Button mButtonLogin;

	@ViewById(R.id.cb_savepassword_activity_login)
	CheckBox mCheckBoxSavePassword;
	// ×Ô¶¯µÇÂ¼
	@ViewById(R.id.cb_auto_activity_login)
	CheckBox mCheckBoxAutoLogin;

	private MyProgressBar myProgressBar;

	private LoginPresenter loginPresenter;
	private RequestQueue mQueue;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mQueue = Volley.newRequestQueue(LoginActivity.this);
		loginPresenter = new LoginPresenter(this);
		myProgressBar = new MyProgressBar(this);
	}

	@AfterViews
	public void init() {
		mRelativeLayoutSearch.setVisibility(View.INVISIBLE);
		loginPresenter.setIsSavePass(LoginActivity.this);
		loginPresenter.setIsAutoLogin(LoginActivity.this);
		loginPresenter.setUsername(this);
		if (mCheckBoxSavePassword.isChecked())
			loginPresenter.setPassword(this);
	}

	@Click(R.id.bt_login_activity_login)
	public void login() {
		loginPresenter.Login(mQueue);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Override
	public String getUsername() {
		return mEditTextUserName.getText().toString().trim();
	}

	@Override
	public void setUsername(String username) {
		mEditTextUserName.setText(username);
	}

	@Override
	public String getPassword() {
		return mEditTextPassWord.getText().toString().trim();
	}

	@Override
	public void setPassword(String password) {
		mEditTextPassWord.setText(password);
	}

	@Override
	public void showDialog() {
		myProgressBar.show();
	}

	@Override
	public void hideDialog() {
		myProgressBar.dismiss();
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	@Override
	public void pushMainActivity() {
		loginPresenter.saveIsSavePass(this);
		loginPresenter.saveIsAutoLogin(this);
		loginPresenter.saveUsernameAndPassword(this);
		EventBus.getDefault().post(new LoginSuccessEvent());
		finish();
	}

	@Override
	public boolean getIsSavePass() {
		return mCheckBoxSavePassword.isChecked();
	}

	@Override
	public void setIsSavePass(boolean isSavePass) {
		mCheckBoxSavePassword.setChecked(isSavePass);
	}

	@Override
	public boolean getIsAutoLogin() {
		return mCheckBoxAutoLogin.isChecked();
	}

	@Override
	public void setIsAutoLogin(boolean isAutoLogin) {
		mCheckBoxAutoLogin.setChecked(isAutoLogin);
	}
}
