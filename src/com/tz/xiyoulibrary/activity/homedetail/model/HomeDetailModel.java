package com.tz.xiyoulibrary.activity.homedetail.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public class HomeDetailModel implements IHomeDetailModel{
	
	public int status;
	public String message;
//	public String detailMsg;
	public Map<String, String> detailMap;

	
	

	@Override
	public void getDetailData(RequestQueue queue, String url,
			final CallBack<HomeDetailModel> callBack) {
		// TODO Auto-generated method stub
		status = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				System.out.println("response="+response);
				getDataByJson(response, callBack);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				status = LOADING_FALUIRE;
				message = "加载失败";
				callBack.getModel(HomeDetailModel.this);
			}
		}); 
		queue.add(request);

	}
	
	public void getDataByJson(String response,CallBack<HomeDetailModel> callBack){
		detailMap = new HashMap<String, String>();
			try {
				JSONObject obj1 = new JSONObject(response);
				if(obj1.getBoolean("Result")){
					JSONObject obj2 = obj1.getJSONObject("Detail");
					detailMap.put("Title",obj2.getString("Title"));
					detailMap.put("Publisher", obj2.getString("Publisher"));
					detailMap.put("Date", obj2.getString("Date"));
					detailMap.put("Passage", obj2.getString("Passage"));
					status = LOADING_SUCCESS;
				}else{
					status = LOADING_FALUIRE;
					message = "获取信息失败";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				status = LOADING_FALUIRE;
				message = "获取信息失败";
			}
			callBack.getModel(HomeDetailModel.this);
		
		
	}
	

}
