package com.tz.xiyoulibrary.dialog.progressdialog;

import com.tz.xiyoulibrary.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAlertDialog extends Dialog {

	private String msg = "É¾³ý¸ÃÊÕ²Ø?";
	private TextView mTextViewMsg;
	private Button mButtonConfirm;
	private Button mButtonCancle;
	
	private MyAlertDialogListener mMyAlertDialogListener;
	
	public void setMyAlertDialogListener(MyAlertDialogListener mMyAlertDialogListener){
		this.mMyAlertDialogListener = mMyAlertDialogListener;
	}
	
	public interface MyAlertDialogListener{
		void confirm();
	}
	public MyAlertDialog(Context context) {
		super(context, R.style.MyDialog);
	}
	public MyAlertDialog(Context context,MyAlertDialogListener myAlertDialogListener) {
		super(context, R.style.MyDialog);
		this.mMyAlertDialogListener = myAlertDialogListener;
	}
	public MyAlertDialog(Context context,String msg,MyAlertDialogListener myAlertDialogListener) {
		super(context, R.style.MyDialog);
		this.mMyAlertDialogListener = myAlertDialogListener;
		this.msg = msg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myalertdialog);
		mButtonConfirm = (Button) findViewById(R.id.bt_alertdialog_confirm);
		mButtonCancle = (Button) findViewById(R.id.bt_alertdialog_cancle);
		mTextViewMsg = (TextView) findViewById(R.id.tv_alertdialog_msg);
		mTextViewMsg.setText(msg);
		mButtonConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mMyAlertDialogListener.confirm();
			}
		});
		mButtonCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

}
