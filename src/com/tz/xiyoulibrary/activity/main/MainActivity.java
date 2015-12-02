package com.tz.xiyoulibrary.activity.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.event.LoginSuccessEvent;
import com.tz.xiyoulibrary.activity.search.view.SearchActivity_;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.dialog.progressdialog.MyAlertDialog;
import com.tz.xiyoulibrary.fragment.home.HomeFragment;
import com.tz.xiyoulibrary.fragment.home.HomeFragment_;
import com.tz.xiyoulibrary.fragment.my.MyFragment_;
import com.tz.xiyoulibrary.fragment.setting.SettingFragment_;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.LogUtils;
import com.tz.xiyoulibrary.utils.UpDateUtils;
import com.ypy.eventbus.EventBus;

@SuppressLint("HandlerLeak")
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	// 顶部标题栏
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;
	/**
	 * 主界面的ViewPage
	 */
	@ViewById(R.id.vp_activity_main)
	ViewPager vp_main_tab;
	private FragmentPagerAdapter mAdapter = null;
	private List<Fragment> mFragments = null;
	/**
	 * 三个Fragment页面
	 */
	private HomeFragment homeFragment;
	private MyFragment_ myFragment;
	private SettingFragment_ settingFragment;

	/**
	 * 底部的三个radiobutton
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
		EventBus.getDefault().register(this);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mFragments = new ArrayList<Fragment>();

		homeFragment = new HomeFragment_();
		myFragment = new MyFragment_();
		settingFragment = new SettingFragment_();

		mFragments.add(homeFragment);
		mFragments.add(myFragment);
		mFragments.add(settingFragment);
	}

	@AfterViews
	public void initWidgetAfter() {
		// 设置首页查询可见
		mRelativeLayoutSearch.setVisibility(View.VISIBLE);
		// 初始化Fragment适配器
		initViewPage();
		// 初始化ViewPage
		initMenu();
		if (!UpDateUtils.isNew)
			showUpdateView();

		initLeanCouldPush();
	}

	/**
	 * 初始化Fragment适配器
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
	 * 初始化ViewPage
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
		Intent intent = new Intent(MainActivity.this, SearchActivity_.class);
		startActivity(intent);
	}

	public void initActionBar(int currPage) {
		if (currPage == 0) {
			mTextViewTitle.setText(getResources()
					.getString(R.string.main_title));
			mRelativeLayoutSearch.setVisibility(View.VISIBLE);
		} else if (currPage == 1) {
			if (Application.user == null) {
				mTextViewTitle.setText("我的");
			} else {
				mTextViewTitle.setText(Application.user.getName());
			}
			mRelativeLayoutSearch.setVisibility(View.INVISIBLE);
		} else {
			mTextViewTitle.setText(getResources().getString(
					R.string.setting_text));
			mRelativeLayoutSearch.setVisibility(View.INVISIBLE);
		}
	}

	private MyProgressBar progressBar;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x001) {
				progressBar.show();
				CustomToast.showToast(MainActivity.this, "开始下载", 2000);
			} else if (msg.what == 0x003) {// 下载完成
				CustomToast.showToast(MainActivity.this, "下载完成", 2000);
				LogUtils.d("MainActivity", "下载完成");
				progressBar.dismiss();
				File apkFile = (File) msg.obj;
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(apkFile),
						"application/vnd.android.package-archive");
				startActivity(intent);
			} else {// 正在下载
				LogUtils.d("MainActivity : currProgress", msg.what + "%");
				progressBar.setCurrProgress(msg.what);
			}
		};
	};

	public void onEventMainThread(LoginSuccessEvent event) {
		if (Application.user == null) {
			mTextViewTitle.setText("我的");
		} else if (vp_main_tab.getCurrentItem() == 1) {
			mTextViewTitle.setText(Application.user.getName());
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	};

	private void showUpdateView() {
		MyAlertDialog alertDialog = new MyAlertDialog(MainActivity.this,
				"亲！出新版本了，是否下载？", new MyAlertDialog.MyAlertDialogListener() {

					@Override
					public void confirm() {
						// 下载新文件
						progressBar = new MyProgressBar(MainActivity.this);
						UpDateUtils.downLoadApk(handler);
					}
				});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				CustomToast.showToast(this, "再按一次退出程序", 2000);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initLeanCouldPush() {
		// 设置默认打开的 Activity
		PushService.setDefaultPushCallback(this, MainActivity_.class);
	    // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
	    PushService.subscribe(this, "public", MainActivity_.class);
		AVInstallation.getCurrentInstallation().saveInBackground();
	}
}
