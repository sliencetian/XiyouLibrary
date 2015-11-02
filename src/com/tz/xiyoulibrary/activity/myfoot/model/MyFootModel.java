package com.tz.xiyoulibrary.activity.myfoot.model;

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
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.JsonUtils;
import com.tz.xiyoulibrary.utils.LogUtils;

public class MyFootModel implements IMyFootModel {

	public int status;
	public String msg;
	public List<BookBean> favoriteData;

	@Override
	public void getFavoriteData(RequestQueue queue,
			final CallBack<MyFootModel> callBack) {
		status = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_FAVORITE, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("getBookDetial:", response);
						// 解析数据
						formatDataByJson(response, callBack);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						status = LOADING_FALUIRE;
						msg = "加载失败";
						callBack.getModel(MyFootModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", Application.SESSION);
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);

	}

	protected void formatDataByJson(String response,
			CallBack<MyFootModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				try {
					favoriteData = new ArrayList<BookBean>();
					JSONArray array = o.getJSONArray("Detail");
					for(int i = 0;i < array.length();i++){
						JSONObject o2 = array.getJSONObject(i);
						BookBean book = new BookBean();
						book.setTitle(o2.getString("Title"));
						book.setPub(o2.getString("Pub"));
						book.setSort(o2.getString("Sort"));
						book.setId(o2.getString("ID"));
						book.setISBN(o2.getString("ISBN"));
						book.setAuthor(o2.getString("Author"));
						favoriteData.add(book);
					}
					status = LOADING_SUCCESS;
				} catch (Exception e) {
					Object[] object = JsonUtils.getErrorMsg(o
							.getString("Detail"));
					status = (Integer) object[0];
					msg = (String) object[1];
				}
			} else {
				status = LOADING_FALUIRE;
				msg = "获取信息失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = LOADING_FALUIRE;
			msg = "获取信息失败";
		}
		callBack.getModel(this);
	}

}
