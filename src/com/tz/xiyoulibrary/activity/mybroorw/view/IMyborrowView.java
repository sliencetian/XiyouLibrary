package com.tz.xiyoulibrary.activity.mybroorw.view;

import java.util.List;

import com.tz.xiyoulibrary.bean.BookBean;

public interface IMyborrowView {

	void showLoadingView();
	
	void showBorrowView(List<BookBean> borrowData);
	
	void showLoadFaluireView();
	
	void showNoDataView();
	
	void showMsg(String msg);
}
