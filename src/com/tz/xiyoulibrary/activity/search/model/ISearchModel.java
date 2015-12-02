package com.tz.xiyoulibrary.activity.search.model;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface ISearchModel {
	static final int SEARCH_SUCCESS = 1;
	static final int SEARCH_NO_DATA = 2;
	static final int SEARCH_FAILURE = 3;
	void SearchQuery(RequestQueue queue,String keyword,CallBack<SearchModel> callBack);
	String getId(int position);

}
