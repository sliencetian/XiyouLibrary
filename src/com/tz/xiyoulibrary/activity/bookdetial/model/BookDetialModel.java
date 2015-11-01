package com.tz.xiyoulibrary.activity.bookdetial.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class BookDetialModel implements IBookDetialModel {

	public int state;
	public String msg;
	public Map<String, Object> bookDetial;

	@Override
	public void getBookDetial(RequestQueue queue, String url,
			final CallBack<BookDetialModel> callBack) {
		state = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("getBookDetial:", response);
						// 解析数据
						formatDataByJson(response, callBack);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						state = LOADING_FALUIRE;
						msg = "加载失败";
						callBack.getModel(BookDetialModel.this);
					}
				});
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	protected void formatDataByJson(String response,
			CallBack<BookDetialModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				bookDetial = new HashMap<String, Object>();
				try {
					JSONObject o2 = o.getJSONObject("Detail");
					bookDetial.put("Title", o2.getString("Title"));
					bookDetial.put("Pub", o2.getString("Pub"));
					bookDetial.put("Sort", o2.getString("Sort"));
					bookDetial.put("ISBN", o2.getString("ISBN"));
					bookDetial.put("Author", o2.getString("Author"));
					bookDetial.put("ID", o2.getString("ID"));
					try {
						bookDetial.put("Form", o2.getString("Form"));
					} catch (Exception e) {
						bookDetial.put("Form", "暂无记录");
					}
					bookDetial.put("Subject", o2.getString("Subject"));
					bookDetial.put("RentTimes", o2.getString("RentTimes"));
					bookDetial.put("FavTimes", o2.getString("FavTimes"));
					bookDetial.put("BrowseTimes", o2.getString("BrowseTimes"));
					bookDetial.put("Total", o2.getString("Total"));
					bookDetial.put("Avaliable", o2.getString("Avaliable"));
					// 流通信息数组
					JSONArray array = o2.getJSONArray("CirculationInfo");
					List<Map<String, String>> circulationInfoList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject o3 = array.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("Barcode", o3.getString("Barcode"));
						map.put("Sort", o3.getString("Sort"));
						map.put("Department", o3.getString("Department"));
						map.put("Status", o3.getString("Status"));
						map.put("Date", o3.getString("Date"));
						circulationInfoList.add(map);
					}
					bookDetial.put("CirculationInfo", circulationInfoList);
					// 相关图书信息数组
					JSONArray array2 = o2.getJSONArray("ReferBooks");
					List<Map<String, String>> referBooksList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < array2.length(); i++) {
						JSONObject o3 = array2.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("ID", o3.getString("ID"));
						map.put("Title", o3.getString("Title"));
						map.put("Author", o3.getString("Author"));
						referBooksList.add(map);
					}
					bookDetial.put("ReferBooks", referBooksList);
					// 来自豆瓣的信息，没有该书则为null
					try {
						JSONObject o3 = o2.getJSONObject("DoubanInfo");
						if (o3.getString("Pages").equals("")) {
							bookDetial.put("Pages", "暂无记录");
						} else {
							bookDetial.put("Pages", o3.getString("Pages")
									+ " 页");
						}
						try {
							JSONObject o4 = o3.getJSONObject("Images");
							bookDetial.put("medium", o4.getString("medium"));
							// bookDetial.put("small", o3.getString("small"));
							// bookDetial.put("large", o3.getString("large"));
						} catch (Exception e) {
							e.printStackTrace();
						}
						bookDetial.put("Summary", o3.getString("Summary"));
						bookDetial.put("Author_Info",
								o3.getString("Author_Info"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					state = LOADING_SUCCESS;
				} catch (Exception e) {
					Object[] object = JsonUtils.getErrorMsg(o
							.getString("Detail"));
					state = (Integer) object[0];
					msg = (String) object[1];
				}
			} else {
				state = LOADING_FALUIRE;
				msg = "获取信息失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = LOADING_FALUIRE;
			msg = "获取信息失败";
		}
		callBack.getModel(this);
	}

}
