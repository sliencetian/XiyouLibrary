package com.tz.xiyoulibrary.activity.mycollection.activity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
import com.tz.xiyoulibrary.utils.JsonUtils;
import com.tz.xiyoulibrary.utils.LogUtils;

public class MyCollectionModel implements IMyCollectionModel {

	public int status;
	public String msg;
	public List<Map<String, String>> favoriteData;

	@Override
	public List<Map<String, String>> getFavoriteData(RequestQueue queue,
			final CallBack<MyCollectionModel> callBack) {
		status = LOADING;
		callBack.getModel(MyCollectionModel.this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_FAVORITE, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("getFavorite:", response);
						// 解析数据
						formatDataByJson(response, callBack);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						status = LOADING_FALUIRE;
						msg = "加载失败";
						callBack.getModel(MyCollectionModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", Application.SESSION);
				return map;
			}
		};
		queue.add(request);
		return null;
	}

	/**
	 * 解析数据
	 * 
	 * @param response
	 * @return
	 */
	protected void formatDataByJson(String response,
			CallBack<MyCollectionModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				try {
					JSONArray array = o.getJSONArray("Detail");
					favoriteData = new ArrayList<Map<String, String>>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject o2 = array.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("Title", o2.getString("Title"));
						map.put("Pub", o2.getString("Pub"));
						map.put("Sort", o2.getString("Sort"));
						map.put("ISBN", o2.getString("ISBN"));
						map.put("Author", o2.getString("Author"));
						map.put("ID", o2.getString("ID"));
						favoriteData.add(map);
					}
					status = LOADING_SUCCESS;
				} catch (Exception e) {
					Object[] object = JsonUtils.getErrorMsg(o.getString("Detail"));
					status = (Integer) object[0];
					msg = (String) object[1];
				}
			} else {
				status = LOADING_FALUIRE;
				msg = "获取收藏失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = LOADING_FALUIRE;
			msg = "获取收藏失败";
		}
		callBack.getModel(this);
	}
}
