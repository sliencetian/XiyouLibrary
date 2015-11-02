package com.tz.xiyoulibrary.activity.myfoot.view;

import java.util.List;

import com.tz.xiyoulibrary.bean.BookBean;

public interface IMyFootView {
	void showLoadingView();

	void showFavoriteView(List<BookBean> favoriteData);

	void showLoadFaluireView();

	void showNoDataView();

	void showMsg(String msg);
}
