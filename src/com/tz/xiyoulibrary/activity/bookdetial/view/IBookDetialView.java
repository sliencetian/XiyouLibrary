package com.tz.xiyoulibrary.activity.bookdetial.view;

import java.util.Map;

public interface IBookDetialView {
	void showLoadingView();

	void showBookDetialView(Map<String, Object> bookDetial);

	void showLoadFaluireView();

	void showNoDataView();

	void showMsg(String msg);
}
