package com.tz.xiyoulibrary.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;
import com.baidu.batsdk.BatSDK;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.event.LoginSuccessEvent;
import com.tz.xiyoulibrary.bean.UserBean;
import com.tz.xiyoulibrary.utils.ConfigFile;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;
import com.tz.xiyoulibrary.utils.UpDateUtils;
import com.ypy.eventbus.EventBus;

public class Application extends android.app.Application {

	// 百度统计appkey
	private static final String BAIDU_APP_KEY = "ad9d433b281f852b";
	public static final String BAIDU_REPORTID = "af8750724a";
	// LeanCloud
	private static final String LEANCLOUD_APP_ID = "9cgrBU19DR43tPm4N47koSDi";
	private static final String LEANCLOUD_APP_KEY = "JfnOEaxQ0wjDm9GMxNM2qXEm";

	public static String SESSION = "";
	public static String USERNAME = "";
	public static UserBean user;
	public static String HOME_RESPONSE = "";
	public static List<Map<String, Object>> HOME_NOTICE_LIST;
	public static List<Map<String, Object>> HOME_NEWS_LIST;
	public static List<View> homeAdViews;
	private int[] homeAdViewsId = { R.drawable.home_image1,
			R.drawable.home_image2, R.drawable.home_image3 };
	public static List<View> settingAdViews;
	private int[] settingAdViewsId = { R.drawable.h1, R.drawable.h3,
			R.drawable.h5 };

	private RequestQueue queue;

	@Override
	public void onCreate() {
		super.onCreate();
		queue = Volley.newRequestQueue(this);
		/**
		 * 初始化配置信息
		 */
		initConfig();
		/**
		 * 获取版本信息
		 */
		initVersion();

		/**
		 * 初始化轮播图片的数据
		 */
		initAdViews();
		/**
		 * 初始化百度统计
		 */
		BatSDK.init(this, BAIDU_APP_KEY);
		/**
		 * 初始化LeanCloud统计
		 */
		AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_KEY);
		AVOSCloud.setLastModifyEnabled(true);
		AVOSCloud.setDebugLogEnabled(true);

		/**
		 * 检查更新
		 */
		UpDateUtils.checkUpdate();
		/**
		 * 自动登录
		 */
		autoLogin();
	}


	/**
	 * 自动登录
	 */
	private void autoLogin() {
		if (!ConfigFile.getIsAutoLogin(this)
				|| ConfigFile.getPassword(this).equals(""))
			return;
		final String username = ConfigFile.getUsername(this);
		final String password = ConfigFile.getPassword(this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_USER_LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("LoginResponse:", response);
						try {
							JSONObject o = new JSONObject(response);
							if (o.getBoolean("Result")) {
								SESSION = o.getString("Detail");
								USERNAME = username;
								getUserInfo();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username);
				map.put("password", password);
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	public void getUserInfo() {
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_USER_INFO, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("LoginResponse:", response);
						try {
							JSONObject o = new JSONObject(response);
							if (o.getBoolean("Result")) {
								JSONObject info = o.getJSONObject("Detail");
								UserBean user = new UserBean();
								user.setId(info.getString("ID"));
								user.setName(info.getString("Name"));
								user.setFromData(info.getString("From"));
								user.setToData(info.getString("To"));
								user.setReaderType(info.getString("ReaderType"));
								user.setDepartment(info.getString("Department"));
								user.setDebt(info.get("Debt") + "");
								Application.user = user;
								EventBus.getDefault().post(
										new LoginSuccessEvent());
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", SESSION);
				return map;
			}
		};
		queue.add(request);
	}

	private void initConfig() {
		/**
		 * 初始化配置信息
		 */
		Constants.isLoadImg = ConfigFile.getNet(this);
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null)
			Constants.network_type = ni.getType();
	}

	/**
	 * 获取版本信息
	 */
	private void initVersion() {
		try {
			PackageManager pm = getPackageManager();
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			Constants.versionCode = pi.versionCode;
			Constants.versionName = pi.versionName;
			LogUtils.d("Version", "versionCode : " + Constants.versionCode
					+ "  versionName : " + Constants.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化轮播图片的数据
	 */
	public void initAdViews() {
		// 初始化首页图片数据
		homeAdViews = new ArrayList<View>();
		for (int i = 0; i < homeAdViewsId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(homeAdViewsId[i]);
			homeAdViews.add(imageView);
		}

		// 初始化设置图片数据
		settingAdViews = new ArrayList<View>();
		for (int i = 0; i < settingAdViewsId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(settingAdViewsId[i]);
			settingAdViews.add(imageView);
		}
	}
}
