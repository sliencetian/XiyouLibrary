package com.tz.xiyoulibrary.fragment.home.view;

import java.util.List;
import java.util.Map;

public interface IHomeView {
	void showLoadingView();

	void showHomeView(List<Map<String, Object>> homeListData);

	void showLoadFaluireView();

	void showNoDataView();

	void showMsg(String msg);

	void showRefershFaluireView();

	void showUpRefershView(List<Map<String, Object>> homeListData);

	void showDownRefershView(List<Map<String, Object>> homeListData);
	
	void initViewPager();

}
