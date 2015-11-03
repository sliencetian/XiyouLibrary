package com.tz.xiyoulibrary.activity.question;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;

@EActivity(R.layout.activity_question)
public class QuestionActivity extends BaseActivity {
	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	public void initWidgetAfter() {
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("常见问题");
	}
	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}
}
