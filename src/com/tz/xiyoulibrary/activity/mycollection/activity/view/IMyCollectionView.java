package com.tz.xiyoulibrary.activity.mycollection.activity.view;

import java.util.List;
import java.util.Map;

public interface IMyCollectionView {

	/**
	 * 显示收藏数据/获取数据成功
	 * 
	 * @param favoriteData
	 */
	void showFavoriteData(List<Map<String, String>> favoriteData);

	/**
	 * 获取数据失败
	 */
	void showGetDataFaluire();

	/**
	 * 当前没有收藏
	 */
	void showGetDataNoData();
	
	void showMsg(String msg);
}
