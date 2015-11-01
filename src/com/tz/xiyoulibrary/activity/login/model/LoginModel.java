package com.tz.xiyoulibrary.activity.login.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.bean.UserBean;
import com.tz.xiyoulibrary.utils.ConfigFile;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;

public class LoginModel implements ILoginModel {

	public int state;
	public String msg;

	@Override
	public void login(final RequestQueue queue, final String username,
			final String password, final CallBack<LoginModel> callBack) {
		if (checkInput(username, password)) {
			state = LOGIN_ING;
			callBack.getModel(this);
			StringRequest request = new StringRequest(Method.POST,
					Constants.GET_USER_LOGIN, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							LogUtils.d("LoginResponse:", response);
							try {
								JSONObject o = new JSONObject(response);
								if (o.getBoolean("Result")) {
									state = LOGIN_SUCCESS;
									msg = o.getString("Detail");
									Application.SESSION = msg;
									getUserInfo(queue, msg, callBack);
								} else {
									state = LOGIN_FAILURE;
									if (o.getString("Detail").equals(
											"ACCOUNT_ERROR")) {
										msg = "ÕËºÅ´íÎó£¬ÃÜÂë´íÎó»òÕË»§²»´æÔÚ";
									} else {
										msg = "µÇÂ¼Ê§°Ü";
									}
									callBack.getModel(LoginModel.this);
								}
							} catch (JSONException e) {
								e.printStackTrace();
								state = LOGIN_FAILURE;
								msg = "µÇÂ¼Ê§°Ü";
								callBack.getModel(LoginModel.this);
							}

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							error.printStackTrace();
							state = LOGIN_FAILURE;
							msg = "ÍøÂçÇëÇóÊ§°Ü";
							callBack.getModel(LoginModel.this);
						}
					}) {
				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
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
		} else {
			state = LOGIN_FAILURE;
			callBack.getModel(this);
		}
	}

	@Override
	public void getUserInfo(RequestQueue queue, final String session,
			final CallBack<LoginModel> callBack) {
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_USER_INFO, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("LoginResponse:", response);
						try {
							JSONObject o = new JSONObject(response);
							if (o.getBoolean("Result")) {
								state = LOGIN_SUCCESS;
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
								callBack.getModel(LoginModel.this);
							} else {
								state = LOGIN_FAILURE;
								msg = "µÇÂ¼Ê§°Ü";
								callBack.getModel(LoginModel.this);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							state = LOGIN_FAILURE;
							msg = "µÇÂ¼Ê§°Ü";
							callBack.getModel(LoginModel.this);
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						state = LOGIN_FAILURE;
						msg = "ÍøÂçÇëÇóÊ§°Ü";
						callBack.getModel(LoginModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", session);
				return map;
			}
		};
		queue.add(request);
	}

	@Override
	public String getUsername(Context context) {
		return ConfigFile.getUsername(context);
	}

	@Override
	public String getPassword(Context context) {
		return ConfigFile.getPassword(context);
	}

	@Override
	public void saveUsernameAndPassword(Context context, String username,
			String password) {
		ConfigFile.savePassword(context, password);
		ConfigFile.saveUsername(context, username);
	}

	@Override
	public boolean getIsSavePass(Context context) {
		return ConfigFile.getIsSavePass(context);
	}

	@Override
	public void setIsSavePass(Context context, boolean isSavePass) {
		ConfigFile.saveIsSavePass(context, isSavePass);
	}

	@Override
	public boolean checkInput(String username, String password) {
		if (username.equals("")) {
			msg = "ÓÃ»§Ãû²»ÄÜÎª¿Õ";
			return false;
		}
		if (password.equals("")) {
			msg = "ÃÜÂë²»ÄÜÎª¿Õ";
			return false;
		}
		return true;
	}
}
