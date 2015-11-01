package com.tz.xiyoulibrary.activity.mybroorw.model;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface IMyBorrowModel {
	static final int LOADING_SUCCESS = 0;
	static final int LOADING_FALUIRE = 1;
	static final int NO_DATA = 2;
	static final int LOADING = 3;

	void getBorrowData(RequestQueue queue, CallBack<MyBorrowModel> callBack);
}
