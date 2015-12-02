package com.tz.xiyoulibrary.fragment.setting;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.about.AboutActivity_;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.customerviewpager.CustomerViewPage;
import com.tz.xiyoulibrary.dialog.progressdialog.MyAlertDialog;
import com.tz.xiyoulibrary.switchview.togglebutton.ToggleButton;
import com.tz.xiyoulibrary.utils.ConfigFile;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;
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
	@ViewById(R.id.rl_about_fragment_setting)
	RelativeLayout mRelativeLayoutAbout;
	@ViewById(R.id.rl_exit_fragment_setting)
	RelativeLayout mRelativeLayoutExit;
	@ViewById(R.id.vp_fragment_setting)
	CustomerViewPage viewPage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 消息通知
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
	 * 网络下载图片
	 */
	@Click(R.id.tb_net_fragment_setting)
	public void pushNet() {
		if (Constants.isLoadImg) {
			mToggleButtonNet.toggleOff();
			ConfigFile.saveNet(getActivity(), false);
			Constants.isLoadImg = false;
		} else {
			mToggleButtonNet.toggleOn();
			ConfigFile.saveNet(getActivity(), true);
			Constants.isLoadImg = true;
		}
	}

	/**
	 * 关于
	 */
	@Click(R.id.rl_about_fragment_setting)
	public void pushAbout() {
		startActivity(new Intent(getActivity(), AboutActivity_.class));
	}

	/**
	 * 退出
	 */
	@Click(R.id.rl_exit_fragment_setting)
	public void pushExit() {
		new MyAlertDialog(getActivity(), "确认退出?",
				new MyAlertDialog.MyAlertDialogListener() {

					@Override
					public void confirm() {
						Application.user = null;
						ConfigFile.savePassword(getActivity(), "");
						getActivity().finish();
					}
				}).show();
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
		viewPage.setViewPageViews(Application.settingAdViews);
	}

	@Override
	public void onPause() {
		super.onPause();
		viewPage.stop();
		LogUtils.d("SettingFragment : ", "onPause()");
	}
}
