package com.tz.xiyoulibrary.activity.about;

import java.io.File;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.advice.AdviceActivity_;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.question.QuestionActivity_;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.dialog.progressdialog.MyAlertDialog;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;
import com.tz.xiyoulibrary.utils.UpDateUtils;

@SuppressLint("HandlerLeak")
@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {
	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;
	@ViewById(R.id.tv_version_about_activity)
	TextView mTextViewVersion;
	@ViewById(R.id.tv_update_about_activity)
	TextView mTextViewUpdate;
	@ViewById(R.id.tv_question_about_activity)
	TextView mTextViewQuestion;
	@ViewById(R.id.tv_back_advice_about_activity)
	TextView mTextViewBackAdvice;
	@ViewById(R.id.tv_our_web_about_activity)
	TextView mTextViewOurWeb;
	
	private MyProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBar = new MyProgressBar(AboutActivity.this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("关于我们");
		mTextViewOurWeb.setText(Html.fromHtml("<u>" + "访问我们的主页" + "</u>"));
		mTextViewVersion.setText("Version" + Constants.versionName);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Click(R.id.tv_update_about_activity)
	public void update() {
		if (!UpDateUtils.isNew) {
			showUpdateView();
		} else {
			progressBar.show();
			handler.sendEmptyMessageDelayed(0x004, 2000);
		}
	}


	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x001) {
				progressBar.show();
				CustomToast.showToast(AboutActivity.this, "开始下载", 2000);
			} else if (msg.what == 0x003) {// 下载完成
				CustomToast.showToast(AboutActivity.this, "下载完成", 2000);
				LogUtils.d("MainActivity", "下载完成");
				progressBar.dismiss();
				File apkFile = (File) msg.obj;
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(apkFile),
						"application/vnd.android.package-archive");
				startActivity(intent);
			} else if (msg.what == 0x004) {
				progressBar.dismiss();
				CustomToast.showToast(AboutActivity.this, "已是最新版本", 2000);
			} else {// 正在下载
				LogUtils.d("MainActivity : currProgress", msg.what + "%");
				progressBar.setCurrProgress(msg.what);
			}
		};
	};

	private void showUpdateView() {
		MyAlertDialog alertDialog = new MyAlertDialog(AboutActivity.this,
				"亲！出新版本了，是否下载？", new MyAlertDialog.MyAlertDialogListener() {

					@Override
					public void confirm() {
						// 下载新文件
						UpDateUtils.downLoadApk(handler);
					}
				});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	/**
	 * 常见问题
	 */
	@Click(R.id.tv_question_about_activity)
	public void question() {
		startActivity(new Intent(AboutActivity.this, QuestionActivity_.class));
	}

	/**
	 * 意见反馈
	 */
	@Click(R.id.tv_back_advice_about_activity)
	public void backAdvice() {
		startActivity(new Intent(AboutActivity.this, AdviceActivity_.class));
	}

	@Click(R.id.tv_our_web_about_activity)
	public void uourWeb() {
		Uri uri = Uri.parse("http://xiyoumobile.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}
