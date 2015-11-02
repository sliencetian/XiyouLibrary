package com.tz.xiyoulibrary.activity.rank.model;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;

public interface IRankModel {
	static final int LOADING_SUCCESS = 0;
	static final int LOADING_FALUIRE = 1;
	static final int NO_DATA = 2;
	static final int LOADING = 3;
	static final int REFER_FALUIRE = 4;
	static final int UP_REFER_SUCCESS = 5;
	static final int DOWN_REFER_SUCCESS = 6;

	/**
	 * ��ȡ��������
	 * 
	 * @param type
	 *            1--�������а� 3--�ղ����а� 5--�鿴���а�
	 */
	void getRankData(RequestQueue queue, String type,
			CallBack<RankModel> callBack);
	
	void refershRankData(RequestQueue queue,String type,int currSize,int refershWhat,
			CallBack<RankModel> callBack);
}
