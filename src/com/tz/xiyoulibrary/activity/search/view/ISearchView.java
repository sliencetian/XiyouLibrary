package com.tz.xiyoulibrary.activity.search.view;

import java.util.List;
import java.util.Map;

public interface ISearchView {
	void showBookListView(List<Map<String, String>> bookList);
	void showNoDataView();

}
