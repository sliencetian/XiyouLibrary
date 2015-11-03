package com.tz.xiyoulibrary.fragment.setting;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.login.view.LoginActivity_;
import com.tz.xiyoulibrary.switchview.togglebutton.ToggleButton;
import com.tz.xiyoulibrary.utils.ConfigFile;

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

	@ViewById(R.id.tb_message_notic_fragment_setting)
	ToggleButton mToggleButtonMessageBotic;
	@ViewById(R.id.tb_net_fragment_setting)
	ToggleButton mToggleButtonNet;
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
	 * ��������
	 */
	@Click(R.id.tb_message_notic_fragment_setting)
	public void setMessageNotic() {
		if (ConfigFile.getMessageNotic(getActivity())) {
			mToggleButtonMessageBotic.toggleOff();
			ConfigFile.saveMessageNotic(getActivity(), false);
		} else {
			mToggleButtonMessageBotic.toggleOn();
			ConfigFile.saveMessageNotic(getActivity(), true);
		}
	}

	/**
	 * ��������
	 */
	@Click(R.id.tb_net_fragment_setting)
	public void pushNet() {
		if (ConfigFile.getNet(getActivity())) {
			mToggleButtonNet.toggleOff();
			ConfigFile.saveNet(getActivity(), false);
		} else {
			mToggleButtonNet.toggleOn();
			ConfigFile.saveNet(getActivity(), true);
		}
	}

	/**
	 * ��������
	 */
	@Click(R.id.rl_question_fragment_setting)
	public void pushQuestin() {

	}

	/**
	 * �������
	 */
	@Click(R.id.rl_back_advice_fragment_setting)
	public void pushBackAdvice() {

	}

	/**
	 * ����
	 */
	@Click(R.id.rl_about_fragment_setting)
	public void pushAbout() {

	}

	/**
	 * �˳�
	 */
	@Click(R.id.rl_exit_fragment_setting)
	public void pushExit() {
		new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				.setMessage("ȷ���˳�?")
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						startActivity(new Intent(getActivity(),
								LoginActivity_.class));
						getActivity().finish();
					}
				}).create().show();
	}

	@AfterViews
	public void init() {
		if (ConfigFile.getMessageNotic(getActivity())) {
			mToggleButtonMessageBotic.toggleOn();
		} else {
			mToggleButtonMessageBotic.toggleOff();
		}
		if (ConfigFile.getNet(getActivity())) {
			mToggleButtonNet.toggleOn();
		} else {
			mToggleButtonNet.toggleOff();
		}
	}
}
