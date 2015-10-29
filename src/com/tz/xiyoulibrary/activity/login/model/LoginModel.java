package com.tz.xiyoulibrary.activity.login.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.utils.ConfigFile;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;

public class LoginModel implements ILoginModel {

	public int state;
	public String msg;

	@Override
	public void Login(RequestQueue queue, final String username,
			final String password, final CallBack<LoginModel> callBack) {
		if (checkInput(username, password)) {
			state = LOGIN_ING;
			callBack.getModel(this);
			JsonObjectRequest login = new JsonObjectRequest(Constants.LOGIN,
					null, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							LogUtils.d("LoginRequest", response.toString());
							try {
								if (response.getBoolean("Result")) {
									state = LOGIN_SUCCESS;
									msg = response.getString("Detail");
									callBack.getModel(LoginModel.this);
								} else {
									state = LOGIN_FAILURE;
									if (response.getString("Detail").equals(
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
			queue.add(login);
		} else {
			state = LOGIN_FAILURE;
			callBack.getModel(this);
		}
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
