package com.tz.xiyoulibrary.dialog.progressbar;

import com.tz.xiyoulibrary.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MyProgressBar extends Dialog {

	private TextView textView;

	public MyProgressBar(Context context) {
		super(context, R.style.MyDialog);
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		textView = (TextView) findViewById(R.id.tv_progressbar2_msg);
	}

	public void setCurrProgress(int curr) {
		textView.setText(curr + "%");
	}
}
