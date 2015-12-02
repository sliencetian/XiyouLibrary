package com.tz.xiyoulibrary.activity.search.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.utils.Constants;

public class SearchModel implements ISearchModel {
	public int status;
	public Map<String, String> map = new HashMap<String, String>();
	public List<Map<String, String>> contentList;

	@Override
	public void SearchQuery(final RequestQueue queue, final String keyWord,
			final CallBack<SearchModel> callBack) {
		contentList = new ArrayList<Map<String, String>>();
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_SEARCH, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						try {
							JSONObject obj = new JSONObject(response);
							if (obj.getBoolean("Result")) {
								System.out.println("jinruif");
								JSONObject obj1 = obj.getJSONObject("Detail");
								org.json.JSONArray arrList = obj1
										.getJSONArray("BookData");

								if (arrList.length() == 0) {
									status = SEARCH_NO_DATA;
								} else {

									for (int i = 0; i < arrList.length(); i++) {

										JSONObject obj2 = arrList
												.getJSONObject(i);
										Map<String, String> map0 = new HashMap<String, String>();
										map0.put("ID", obj2.getString("ID"));
										map0.put("Title",
												obj2.getString("Title"));
										map0.put("Author",
												obj2.getString("Author"));
										map0.put("Pub", obj2.getString("Pub"));
										contentList.add(map0);
									}
									status = SEARCH_SUCCESS;
								}

							} else
								status = SEARCH_FAILURE;
							callBack.getModel(SearchModel.this);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							status = SEARCH_NO_DATA;
						}
						callBack.getModel(SearchModel.this);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						status = SEARCH_FAILURE;
						callBack.getModel(SearchModel.this);
					}

				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				try {
					map.put("keyword", URLEncoder.encode(keyWord, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					map.put("keyword", keyWord);
				}
				return map;

			}
		};
		request.setTag("search");
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);

	}

	@Override
	public String getId(int position) {
		// TODO Auto-generated method stub
		return contentList.get(position).get("ID");
	}

}
