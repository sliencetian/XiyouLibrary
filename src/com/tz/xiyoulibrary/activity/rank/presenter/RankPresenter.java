package com.tz.xiyoulibrary.activity.rank.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.rank.model.IRankModel;
import com.tz.xiyoulibrary.activity.rank.model.RankModel;
import com.tz.xiyoulibrary.activity.rank.view.IRankView;

public class RankPresenter {

	private IRankView mRankView;
	private IRankModel mRankModel;

	private CallBack<RankModel> callBack;

	public RankPresenter(IRankView view) {
		this.mRankView = view;
		mRankModel = new RankModel();
		callBack = new CallBack<RankModel>() {

			@Override
			public void getModel(RankModel model) {
				switch (model.status) {
				case IRankModel.LOADING:
					mRankView.showLoadingView();
					break;
				case IRankModel.LOADING_FALUIRE:
					mRankView.showLoadFaluireView();
					mRankView.showMsg(model.msg);
					break;
				case IRankModel.NO_DATA:
					mRankView.showNoDataView();
					break;
				case IRankModel.LOADING_SUCCESS:
					mRankView.showRankView(model.rankData);
					break;
				case IRankModel.REFER_FALUIRE:
					mRankView.showRefershFaluireView();
					mRankView.showMsg(model.msg);
					break;
				case IRankModel.UP_REFER_SUCCESS:
					mRankView.showUpRefershView(model.rankData);
					break;
				case IRankModel.DOWN_REFER_SUCCESS:
					mRankView.showDownRefershView(model.rankData);
					break;
				}
			}
		};
	}

	public void getRankData(RequestQueue queue, String type) {
		mRankModel.getRankData(queue, type, callBack);
	}

	public void refershData(RequestQueue queue, String type, int currSize,
			int refershWhat) {
		mRankModel
				.refershRankData(queue, type, currSize, refershWhat, callBack);
	}
}
