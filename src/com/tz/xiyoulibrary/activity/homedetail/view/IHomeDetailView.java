package com.tz.xiyoulibrary.activity.homedetail.view;

import java.util.Map;

public interface IHomeDetailView {
	void showLoadingView();
	void showMsg(String msg);
	void showDetailData(Map<String, String> detailMap);
	void showNoDataView();
	void showLoadFailView();
	

}
