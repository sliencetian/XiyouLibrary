package com.tz.xiyoulibrary.fragment.home.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.utils.Constants;

public class HomeModel implements IHomeModel{
	public int status;
	public String message;
	
	String partUrl = Constants.GET_NEWS_LIST;
	String url;
	
	public Map<String,Object> map = new HashMap<String, Object>();
	public List<Map<String,Object>> list;

//	网络请求
	@Override
	public void getHomeData(RequestQueue queue,final int CurrentPage ,final int whichTag,
			final CallBack<HomeModel> callBack) {
		// TODO Auto-generated method stub
		status = IHomeModel.LOADING;
		callBack.getModel(this);
		if(whichTag == 0)
			url = partUrl + "announce/"+CurrentPage;
		else
			url = partUrl + "news/"+CurrentPage;
		System.out.println("当前url="+url);
		
		StringRequest request  = new StringRequest(url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println(response);
				getDataByJson(response,callBack,whichTag,LOADING_SUCCESS);
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				status = LOADING_FALUIRE;
				message = "网络异常";
				callBack.getModel(HomeModel.this);
				
			}
		});
		
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
		
	}
	
//解析数据
	protected void getDataByJson(String response,CallBack<HomeModel> callBack,int whichTag,int what) {
		
		list = new ArrayList<Map<String,Object>>();
		try {
			JSONObject obj1 = new JSONObject(response);
			if(obj1.getBoolean("Result")){
//				用于判断是否需要再请求
				Application.HOME_RESPONSE = response;
				
				JSONObject obj2 = obj1.getJSONObject("Detail");
				map.put("CurrentPage", obj2.getInt("CurrentPage"));
				map.put("Pages", obj2.get("Pages"));
				map.put("Amount", obj2.get("Amount"));
				JSONArray arr = obj2.getJSONArray("Data");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj3 = arr.getJSONObject(i);
					Map<String, Object> map0 = new HashMap<String, Object>();
					map0.put("ID", obj3.getInt("ID"));
					map0.put("Title", obj3.getString("Title"));
					map0.put("Date", obj3.getString("Date"));
					list.add(map0);
				}
				map.put("Detail", list);
				status = what;
				if(whichTag == 0){
					System.out.println("whichTag = 0  ");
					Application.HOME_NOTICE_LIST = list;
				}else{
					System.out.println("whichTag = 1");
					Application.HOME_NEWS_LIST = list;
				}
				
			}else{
				status = REFER_FALUIRE;
				message = "获取信息失败";
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = REFER_FALUIRE;
			message = "获取信息失败";
			
		}
		callBack.getModel(HomeModel.this);
		
		
	}


//	刷新数据
	@Override
	public void refershHomeData(RequestQueue queue, int CurrentPage,final int whichTag,final int what,
			final CallBack<HomeModel> callBack) {
		if(whichTag == 0)
			url = partUrl + "announce/"+CurrentPage;
		else
			url = partUrl + "news/"+CurrentPage;
		System.out.println("刷新的url="+url);
		
		StringRequest request  = new StringRequest(url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println(response);
				getDataByJson(response,callBack,whichTag,what);
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				status = REFER_FALUIRE;
				message = "网络请求错误";
				callBack.getModel(HomeModel.this);
				
			}
		});
		queue.add(request);
			
	}

//	@Override
//	public int getItemId(int position) {
//		// TODO Auto-generated method stub
//		System.out.println("getItemId = "+(Integer) list.get(position).get("ID"));
//		return (Integer) list.get(position).get("ID");
//	}

}
