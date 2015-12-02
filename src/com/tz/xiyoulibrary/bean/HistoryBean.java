package com.tz.xiyoulibrary.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3580740086543110122L;

	private List<Map<String, String>> mHistoryList = new ArrayList<Map<String,String>>();

	public List<Map<String, String>> getHistoryList() {
		return mHistoryList;
	}

	public void setHistoryList(List<Map<String, String>> mHistoryList) {
		this.mHistoryList = mHistoryList;
	}
	
}
