package com.tz.xiyoulibrary.activity.bookdetial.model;

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
						// ��������
						formatBookDetialDataByJson(response, callBack);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						state = LOADING_FALUIRE;
						msg = "����ʧ��";
						callBack.getModel(BookDetialModel.this);
					}
				});
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	@Override
	public void collection(RequestQueue queue, final String id,
			final CallBack<BookDetialModel> callBack) {
		state = LOADING;
		callBack.getModel(this);
		StringRequest request = new StringRequest(Method.POST,
				Constants.GET_BOOK_ADD_FAVORITE,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtils.d("collection:", response);
						// ��������
						formatCollectionDataByJson(response, callBack);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						state = LOADING_FALUIRE;
						msg = "�����쳣";
						callBack.getModel(BookDetialModel.this);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", Application.SESSION);
				map.put("id", id);
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	/**
	 * �����ղ�����
	 */
	protected void formatCollectionDataByJson(String response,
			CallBack<BookDetialModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				state = LOADING_SUCCESS;
				msg = getMsgByDetial(o.getString("Detail"));
			} else {
				state = LOADING_FALUIRE;
				msg = "�ղ�ʧ��";
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = LOADING_FALUIRE;
			msg = "�ղ�ʧ��";
		}
		callBack.getModel(this);
	}

	private String getMsgByDetial(String detial) {
		String s = null;
		if (detial.equals("ADDED_SUCCEED")) {
			s = "�ղسɹ�";
		} else if (detial.equals("ALREADY_IN_FAVORITE")) {
			s = "�Ѿ��ղع���";
		} else if (detial.equals("USER_NOT_LOGIN")) {
			s = "�û���¼ʧЧ,�����µ�¼";
		} else if (detial.equals("PARAM_ERROR")) {
			s = "��������ȱ�ٲ���";
		} else {
			s = "�ղ�ʧ��";
		}
		return s;
	}

	/**
	 * ������ȡ������Ϣ����
	 */
	protected void formatBookDetialDataByJson(String response,
			CallBack<BookDetialModel> callBack) {
		try {
			JSONObject o = new JSONObject(response);
			if (o.getBoolean("Result")) {
				bookDetial = new HashMap<String, Object>();
				try {
					JSONObject o2 = o.getJSONObject("Detail");
					bookDetial.put("Title", o2.getString("Title"));
					try {
						bookDetial.put("Pub", o2.getString("Pub"));
					} catch (Exception e) {
						bookDetial.put("Pub", "����");
					}
					try {
						bookDetial.put("Summary", o2.getString("Summary"));
					} catch (Exception e) {
						bookDetial.put("Summary", "����");
					}
					try {
						bookDetial.put("Sort", o2.getString("Sort"));
					} catch (Exception e) {
						bookDetial.put("Sort", "����");
					}
					try {
						bookDetial.put("ISBN", o2.getString("ISBN"));
					} catch (Exception e) {
						bookDetial.put("ISBN", "����");
					}
					try {
						bookDetial.put("Author", o2.getString("Author"));
					} catch (Exception e) {
						bookDetial.put("Author", "����");
					}
					try {
						bookDetial.put("ID", o2.getString("ID"));
					} catch (Exception e) {
						bookDetial.put("ID", "����");
					}
					try {
						bookDetial.put("Form", o2.getString("Form"));
					} catch (Exception e) {
						bookDetial.put("Form", "���޼�¼");
					}
					try {
						bookDetial.put("Subject", o2.getString("Subject"));
					} catch (Exception e) {
						bookDetial.put("Subject", "���޼�¼");
					}
					try {
						bookDetial.put("RentTimes", o2.getString("RentTimes"));
					} catch (Exception e) {
						bookDetial.put("RentTimes", "����");
					}
					try {
						bookDetial.put("FavTimes", o2.getString("FavTimes"));
					} catch (Exception e) {
						bookDetial.put("FavTimes", "����");
					}
					try {
						bookDetial.put("BrowseTimes",
								o2.getString("BrowseTimes"));
					} catch (Exception e) {
						bookDetial.put("BrowseTimes", "����");
					}
					try {
						bookDetial.put("Total", o2.getString("Total"));
					} catch (Exception e) {
						bookDetial.put("Total", "����");
					}
					try {
						bookDetial.put("Avaliable", o2.getString("Avaliable"));
					} catch (Exception e) {
						bookDetial.put("Avaliable", "����");
					}
					// ��ͨ��Ϣ����
					LogUtils.d("BookDetial", "��ʼ������ͨ��Ϣ");
					JSONArray array = o2.getJSONArray("CirculationInfo");
					List<Map<String, String>> circulationInfoList = new ArrayList<Map<String, String>>();
					int size = array.length() > 50 ? 50 : array.length();
					for (int i = 0; i < size; i++) {
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
					LogUtils.d("BookDetial", "��ͨ��Ϣ�������");
					LogUtils.d("BookDetial", "��ʼ�������ͼ������");
					// ���ͼ����Ϣ����
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
					LogUtils.d("BookDetial", "����Ƽ��������");
					LogUtils.d("BookDetial", "��ʼ�������Զ��������");
					// ���Զ������Ϣ��û�и�����Ϊnull
					try {
						JSONObject o3 = o2.getJSONObject("DoubanInfo");
						if (o3.getString("Pages").equals("null")
								|| o3.getString("Pages").equals("")) {
							bookDetial.put("Pages", "���޼�¼");
						} else {
							bookDetial.put("Pages", o3.getString("Pages")
									+ " ҳ");
						}
						try {
							JSONObject o4 = o3.getJSONObject("Images");
							bookDetial.put("medium", o4.getString("large"));
							// bookDetial.put("small", o3.getString("small"));
							// bookDetial.put("large", o3.getString("large"));
						} catch (Exception e) {
							bookDetial.put("medium", "");
							e.printStackTrace();
							LogUtils.d("BookDetial", "���������鼮ͼƬ�쳣");
						}
						bookDetial.put("Summary", o3.getString("Summary"));
						bookDetial.put("Author_Info",
								o3.getString("Author_Info"));
					} catch (Exception e) {
						bookDetial.put("Summary", "����");
						bookDetial.put("Author_Info", "����");
						bookDetial.put("Pages", "���޼�¼");
						bookDetial.put("medium", "");
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
				msg = "��ȡ��Ϣʧ��";
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = LOADING_FALUIRE;
			msg = "��ȡ��Ϣʧ��";
		}
		callBack.getModel(this);
	}

}
