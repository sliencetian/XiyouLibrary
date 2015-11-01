package com.tz.xiyoulibrary.activity.mybroorw.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.mybroorw.model.IMyBorrowModel;
import com.tz.xiyoulibrary.activity.mybroorw.model.MyBorrowModel;
import com.tz.xiyoulibrary.activity.mybroorw.view.IMyborrowView;

public class MyBorrowPresenter {

	private IMyborrowView mMyborrowView;
	private IMyBorrowModel mMyBorrowModel;

	public MyBorrowPresenter(IMyborrowView view) {
		this.mMyborrowView = view;
		mMyBorrowModel = new MyBorrowModel();
	}

	public void getBorrowData(RequestQueue queue) {
		mMyBorrowModel.getBorrowData(queue, new CallBack<MyBorrowModel>() {

			@Override
			public void getModel(MyBorrowModel model) {
				switch (model.status) {
				case IMyBorrowModel.LOADING:
					mMyborrowView.showLoadingView();
					break;
				case IMyBorrowModel.LOADING_FALUIRE:
					mMyborrowView.showLoadFaluireView();
					mMyborrowView.showMsg(model.msg);
					break;
				case IMyBorrowModel.NO_DATA:
					mMyborrowView.showNoDataView();
					break;
				case IMyBorrowModel.LOADING_SUCCESS:
					mMyborrowView.showBorrowView(model.borrowData);
					break;
				}
			}
		});
	}
}
