package com.tz.xiyoulibrary.activity.advice;

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
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.toastview.CustomToast;

@EActivity(R.layout.activity_advice)
public class AdviceActivity extends BaseActivity {
	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;

	@ViewById(R.id.bt_activity_advice)
	Button mButtonSend;
	@ViewById(R.id.et_activity_advice)
	EditText mEditTextAdvice;

	private MyProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBar = new MyProgressBar(AdviceActivity.this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("问题反馈");
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progressBar.isShowing())
				progressBar.dismiss();
			switch (msg.what) {
			case 0x0001:
				CustomToast.showToast(AdviceActivity.this, "提交成功", 2000);
				finish();
				break;
			case 0x0002:
				CustomToast.showToast(AdviceActivity.this, "提交失败", 2000);
				break;
			}
		};
	};

	@Click(R.id.bt_activity_advice)
	public void sendAdvice() {
		if (mEditTextAdvice.getText().toString().equals("")) {
			CustomToast.showToast(AdviceActivity.this, "意见不能为空", 2000);
			return;
		}
		progressBar.show();
		AVObject backAdvice = new AVObject("advice");
		backAdvice.put("usernumb", Application.user.getId());
		backAdvice.put("username", Application.user.getName());
		backAdvice.put("From", Application.user.getFromData());
		backAdvice.put("To", Application.user.getToData());
		backAdvice.put("Department", Application.user.getDepartment());
		backAdvice.put("advice", mEditTextAdvice.getText().toString());
		backAdvice.saveInBackground(new SaveCallback() {

			@Override
			public void done(AVException e) {
				if (e == null) {
					handler.sendEmptyMessage(0x0001);
				} else {
					handler.sendEmptyMessage(0x0002);
				}
			}
		});
	}
}
