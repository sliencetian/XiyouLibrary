package com.tz.xiyoulibrary.fragment.setting;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.login.view.LoginActivity_;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

@EFragment(R.layout.fragment_setting)
public class SettingFragment extends Fragment {

	@ViewById(R.id.rl_question_fragment_setting)
	RelativeLayout mRelativeLayoutQuestion;
	@ViewById(R.id.rl_back_advice_fragment_setting)
	RelativeLayout mRelativeLayoutBackAdvice;
	@ViewById(R.id.rl_about_fragment_setting)
	RelativeLayout mRelativeLayoutAbout;
	@ViewById(R.id.rl_exit_fragment_setting)
	RelativeLayout mRelativeLayoutExit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 常见问题
	 */
	@Click(R.id.rl_question_fragment_setting)
	public void pushQuestin() {

	}

	/**
	 * 意见反馈
	 */
	@Click(R.id.rl_back_advice_fragment_setting)
	public void pushBackAdvice() {

	}

	/**
	 * 关于
	 */
	@Click(R.id.rl_about_fragment_setting)
	public void pushAbout() {

	}

	/**
	 * 退出
	 */
	@Click(R.id.rl_exit_fragment_setting)
	public void pushExit() {
		new AlertDialog.Builder(getActivity()).setTitle("提示")
				.setMessage("确认退出?")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						startActivity(new Intent(getActivity(),
								LoginActivity_.class));
						getActivity().finish();
					}
				}).create().show();
	}

}
