package com.tz.xiyoulibrary.activity.main;

import java.util.ArrayList;
import java.util.List;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.fragment.home.HomeFragment;
import com.tz.xiyoulibrary.fragment.my.MyFragment_;
import com.tz.xiyoulibrary.fragment.setting.SettingFragment_;
import com.tz.xiyoulibrary.toastview.CustomToast;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	// ����������
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;
	/**
	 * �������ViewPage
	 */
	@ViewById(R.id.vp_activity_main)
	ViewPager vp_main_tab;
	private FragmentPagerAdapter mAdapter = null;
	private List<Fragment> mFragments = null;
	/**
	 * ����Fragmentҳ��
	 */
	private HomeFragment homeFragment;
	private MyFragment_ myFragment;
	private SettingFragment_ settingFragment;

	/**
	 * �ײ�������radiobutton
	 */
	@ViewById(R.id.rb_main_tab_menu1)
	RadioButton rb_main_tab_menu1;
	@ViewById(R.id.rb_main_tab_menu2)
	RadioButton rb_main_tab_menu2;
	@ViewById(R.id.rb_main_tab_menu3)
	RadioButton rb_main_tab_menu3;
	@ViewById(R.id.rg_menu_activity_main)
	RadioGroup rg_main_menu;

	@ViewById(R.id.rl_search_actionbar)
	RelativeLayout mRelativeLayoutSearch;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		init();
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		mFragments = new ArrayList<Fragment>();

		homeFragment = new HomeFragment();
		myFragment = new MyFragment_();
		settingFragment = new SettingFragment_();

		mFragments.add(homeFragment);
		mFragments.add(myFragment);
		mFragments.add(settingFragment);
	}

	@AfterViews
	public void initWidgetAfter() {
		// ������ҳ��ѯ�ɼ�
		mRelativeLayoutSearch.setVisibility(View.VISIBLE);
		// ��ʼ��Fragment������
		initViewPage();
		// ��ʼ��ViewPage
		initMenu();
	}

	/**
	 * ��ʼ��Fragment������
	 */
	private void initMenu() {
		rg_main_menu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				switch (id) {
				case R.id.rb_main_tab_menu1:
					vp_main_tab.setCurrentItem(0);
					initActionBar(0);
					break;
				case R.id.rb_main_tab_menu2:
					vp_main_tab.setCurrentItem(1);
					initActionBar(1);
					break;
				case R.id.rb_main_tab_menu3:
					vp_main_tab.setCurrentItem(2);
					initActionBar(2);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPage() {
		FragmentManager fm = getSupportFragmentManager();
		mAdapter = new FragmentPagerAdapter(fm) {

			@Override
			public int getCount() {
				return mFragments == null ? 0 : mFragments.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}
		};
		vp_main_tab.setAdapter(mAdapter);
		vp_main_tab.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				initActionBar(position);
				switch (position) {
				case 0:
					rb_main_tab_menu1.setChecked(true);
					break;
				case 1:
					rb_main_tab_menu2.setChecked(true);
					break;
				case 2:
					rb_main_tab_menu3.setChecked(true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Click(R.id.rl_search_actionbar)
	public void search() {
		CustomToast.showToast(this, "��ѯ", 2000);
	}

	public void initActionBar(int currPage) {
		if (currPage == 0) {
			mTextViewTitle.setText(getResources()
					.getString(R.string.main_title));
			mRelativeLayoutSearch.setVisibility(View.VISIBLE);
		} else if (currPage == 1) {
			mTextViewTitle.setText(Application.user.getName());
			mRelativeLayoutSearch.setVisibility(View.INVISIBLE);
		} else {
			mTextViewTitle.setText(getResources().getString(
					R.string.setting_text));
			mRelativeLayoutSearch.setVisibility(View.INVISIBLE);
		}
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				CustomToast.showToast(this, "�ٰ�һ���˳�����", 2000);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
