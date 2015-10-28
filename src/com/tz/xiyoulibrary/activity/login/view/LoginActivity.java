package com.tz.xiyoulibrary.activity.login.view;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.login.presenter.LoginPresenter;
import com.tz.xiyoulibrary.toastview.CustomToast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity implements ILoginView {

	@ViewById(R.id.rl_back_actionbar_tow)
	private RelativeLayout mRelativeLayoutBack;

	@ViewById(R.id.et_username_activity_login)
	private EditText mEditTextUserName;

	@ViewById(R.id.et_password_activity_login)
	private EditText mEditTextPassWord;

	@ViewById(R.id.bt_login_activity_login)
	private Button mButtonLogin;
	
	@ViewById(R.id.cb_savepassword_activity_login)
	private CheckBox mCheckBoxSavePassword;

	private ProgressDialog mProgressDialog;
	
	private LoginPresenter loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		loginPresenter = new LoginPresenter(this);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage("正在登录,请稍候...");
		
		
		init();
		
	}

	private void init() {
		loginPresenter.setUsername();
		loginPresenter.setPassword();
	}

	@Click(R.id.bt_login_activity_login)
	public void login() {

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
		mProgressDialog.show();
	}

	@Override
	public void hideDialog() {
		mProgressDialog.dismiss();
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	@Override
	public void saveUsernameAndPassword() {
		// TODO Auto-generated method stub
		
	}

}
