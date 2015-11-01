package com.tz.xiyoulibrary.activity.mybroorw.model;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.JsonUtils;
import com.tz.xiyoulibrary.utils.LogUtils;

public class MyBorrowModel implements IMyBorrowModel {

	public int status;
	public String msg;
	public List<BookBean> borrowData;

	@Override
	public void getBorrowData(RequestQueue queue,
			final CallBack<MyBorrowModel> callBack) {
		status = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_RENT, new Response.Listener<String>() {

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
						callBack.getModel(MyBorrowModel.this);
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
	}

	protected void formatDataByJson(String response,
			CallBack<MyBorrowModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {

				JSONArray array = o.getJSONArray("Detail");
				try {
					borrowData = new ArrayList<BookBean>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject o2 = array.getJSONObject(i);
						BookBean book = new BookBean();
						book.setTitle(o2.getString("Title"));
						book.setBarCode(o2.getString("Barcode"));
						book.setDepartment(o2.getString("Department"));
						book.setState(o2.getString("State"));
						book.setDate(o2.getString("Date"));
						book.setCanRenew(o2.getBoolean("CanRenew"));
						book.setDepartment_id(o2.getString("Department_id"));
						book.setLibrary_id(o2.getString("Library_id"));
						borrowData.add(book);
					}
					status = LOADING_SUCCESS;
				} catch (Exception e) {
					status = LOADING_FALUIRE;
					msg = JsonUtils.getErrorMsg(o.getString("Detail"));
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
