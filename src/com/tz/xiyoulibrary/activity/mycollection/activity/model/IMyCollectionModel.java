package com.tz.xiyoulibrary.activity.mycollection.activity.model;

import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface IMyCollectionModel {

	static final int LOADING_SUCCESS = 0;
	static final int LOADING_FALUIRE = 1;
	static final int NO_DATA = 2;
	static final int LOADING = 3;

	/**
	 * 获取收藏数据
	 */
	List<Map<String, String>> getFavoriteData(RequestQueue queue,
			CallBack<MyCollectionModel> callBack);

}
