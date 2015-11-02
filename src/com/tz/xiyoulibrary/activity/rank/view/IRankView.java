package com.tz.xiyoulibrary.activity.rank.view;

import java.util.List;
import java.util.Map;

public interface IRankView {
	void showLoadingView();

	void showRankView(List<Map<String, String>> rankData);

	void showLoadFaluireView();

	void showNoDataView();

	void showMsg(String msg);

	void showRefershFaluireView();

	void showUpRefershView(List<Map<String, String>> rankData);

	void showDownRefershView(List<Map<String, String>> rankData);
}
