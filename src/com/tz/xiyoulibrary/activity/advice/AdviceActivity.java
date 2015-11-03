package com.tz.xiyoulibrary.activity.advice;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.NetUtils;

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

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new ProgressDialog(AdviceActivity.this);
		progressDialog.setTitle("提示");
		progressDialog.setMessage("正在提交...");
		progressDialog.setCancelable(false);
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
			progressDialog.dismiss();
			if (NetUtils.isNetworkAvailable(AdviceActivity.this)) {
				CustomToast.showToast(AdviceActivity.this, "提交成功", 2000);
				finish();
			} else {
				CustomToast.showToast(AdviceActivity.this, "网络连接错误", 2000);
			}
		};
	};

	@Click(R.id.bt_activity_advice)
	public void sendAdvice() {
		if (mEditTextAdvice.getText().toString().equals("")) {
			CustomToast.showToast(AdviceActivity.this, "意见不能为空", 2000);
			return;
		}
		progressDialog.show();
		handler.sendEmptyMessageDelayed(0x123, 2000);
	}
}
