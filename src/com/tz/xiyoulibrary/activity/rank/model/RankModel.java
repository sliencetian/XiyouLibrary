package com.tz.xiyoulibrary.activity.rank.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.JsonUtils;
import com.tz.xiyoulibrary.utils.LogUtils;

public class RankModel implements IRankModel {

	public int status;
	public String msg;
	public List<Map<String, String>> rankData;

	private static final int SIZE = 20;

	/**
	 * 获取数据
	 */
	@Override
	public void getRankData(RequestQueue queue, final String type,
			final CallBack<RankModel> callBack) {
		status = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_RANK, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("getRankData:", response);
						// 解析数据
						formatDataByJson(response, callBack,
								IRankModel.LOADING_SUCCESS, 0);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						status = LOADING_FALUIRE;
						msg = "网络异常";
						callBack.getModel(RankModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", type);
				map.put("size", SIZE + "");
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	/**
	 * 刷新加载
	 */
	@Override
	public void refershRankData(RequestQueue queue, final String type,
			final int currSize, final int refershWhat,
			final CallBack<RankModel> callBack) {
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_RANK, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("refershRankData:", response);
						// 解析数据
						formatDataByJson(response, callBack, refershWhat,
								currSize);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						status = REFER_FALUIRE;
						msg = "网络异常";
						callBack.getModel(RankModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", type);
				map.put("size", ((currSize + SIZE) >= 99 ? 99
						: (currSize + SIZE)) + "");
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	protected void formatDataByJson(String response,
			CallBack<RankModel> callBack, int what, int size) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				try {
					rankData = new ArrayList<Map<String, String>>();
					JSONArray array = o.getJSONArray("Detail");

					for (int i = size; i < array.length(); i++) {
						JSONObject o2 = array.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("Title", o2.getString("Title"));
						map.put("ID", o2.getString("ID"));
						map.put("BorNum", o2.getString("BorNum"));
						rankData.add(map);
					}
					status = what;
				} catch (Exception e) {
					Object[] object = JsonUtils.getErrorMsg(o
							.getString("Detail"));
					status = REFER_FALUIRE;
					msg = (String) object[1];
				}
			} else {
				status = REFER_FALUIRE;
				msg = "获取信息失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = REFER_FALUIRE;
			msg = "获取信息失败";
		}
		callBack.getModel(this);
	}

}
