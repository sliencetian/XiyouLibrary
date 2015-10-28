package com.tz.xiyoulibrary.activity.login.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.utils.Constants;

public class LoginModel implements ILoginModel {

	int state;
	String msg;

	@Override
	public void Login(RequestQueue queue, final String username,
			final String password, final CallBack<LoginModel> callBack) {
		JsonObjectRequest login = new JsonObjectRequest(Constants.LOGIN, null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getBoolean("Result")) {
								state = LOGIN_FAILURE;
								msg = response.getString("Detail");
								callBack.getModel(LoginModel.this);
							} else {
								state = LOGIN_FAILURE;
								if (response.getString("Detail").equals(
										"ACCOUNT_ERROR")) {
									msg = "’À∫≈¥ÌŒÛ£¨√‹¬Î¥ÌŒÛªÚ’Àªß≤ª¥Ê‘⁄";
								} else {
									msg = "µ«¬º ß∞‹";
								}
								callBack.getModel(LoginModel.this);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							state = LOGIN_FAILURE;
							msg = "µ«¬º ß∞‹";
							callBack.getModel(LoginModel.this);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						state = LOGIN_FAILURE;
						msg = "Õ¯¬Á«Î«Û ß∞‹";
						callBack.getModel(LoginModel.this);
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
		queue.add(login);
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

}
