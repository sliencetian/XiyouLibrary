package com.tz.xiyoulibrary.activity.modifypassword;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.toastview.CustomToast;

/**
 * 考虑到安全因素暂时未添加
 * 
 * @author tianzhao
 * 
 */
@SuppressLint("HandlerLeak")
@EActivity(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity {

	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;

	@ViewById(R.id.et_username_activity_modify_pwd)
	EditText mEditTextUserName;
	@ViewById(R.id.et_old_password_activity_modify_pwd)
	EditText mEditTextOldPwd;
	@ViewById(R.id.et_new_password_activity_modify_pwd)
	EditText mEditTextNewPwd;
	@ViewById(R.id.et_repass_password_activity_modify_pwd)
	EditText mEditTextRepassPwd;

	@ViewById(R.id.bt_modify_pwd_activity_modify_pwd)
	Button mButtonModifyPwd;

	private MyProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBar = new MyProgressBar(ModifyPasswordActivity.this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("修改密码");
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progressBar.isShowing())
				progressBar.dismiss();
			switch (msg.what) {
			case 0x001:
				showMsg("修改失败");
				break;
			case 0x002:
				
				break;
			}
		};
	};

	/**
	 * 修改密码
	 */
	@Click(R.id.bt_modify_pwd_activity_modify_pwd)
	public void ModifyPwd() {

	}

	private void showMsg(String msg) {
		CustomToast.showToast(ModifyPasswordActivity.this, msg, 2000);
	}
}
