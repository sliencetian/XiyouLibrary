package com.tz.xiyoulibrary.activity.mycollection.activity.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;

public class MyCollectionModel implements IMyCollectionModel {

	public int status;
	public String msg;
	public List<Map<String, String>> favoriteData;

	@Override
	public List<Map<String, String>> getFavoriteData(RequestQueue queue,
			final CallBack<MyCollectionModel> callBack) {
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_FAVORITE, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("getFavorite:", response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						status = FALUIRE;
						msg = "º”‘ÿ ß∞‹";
						callBack.getModel(MyCollectionModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
//				map.put("session", Application.SESSION);
				map.put("session", "JSESSIONID=E67165C42D769B5597567F79023C739B");
				return map;
			}
		};
		queue.add(request);
		return null;
	}
}
