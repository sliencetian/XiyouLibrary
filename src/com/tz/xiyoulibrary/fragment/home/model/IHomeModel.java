package com.tz.xiyoulibrary.fragment.home.model;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface IHomeModel {
	static final int LOADING_SUCCESS = 0;
	static final int LOADING_FALUIRE = 1;
	static final int NO_DATA = 2;
	static final int LOADING = 3;
	static final int REFER_FALUIRE = 4;
	static final int UP_REFER_SUCCESS = 5;
	static final int DOWN_REFER_SUCCESS = 6;
	
	void getHomeData(RequestQueue queue,int Currentpage,int whichTag,final CallBack<HomeModel> callBack);
	void refershHomeData(RequestQueue queue,int CurrentPage,int whichTag,int what,final CallBack<HomeModel> callBack);
//	int getItemId(int position);


}
