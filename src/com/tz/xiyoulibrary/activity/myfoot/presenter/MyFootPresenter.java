package com.tz.xiyoulibrary.activity.myfoot.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.myfoot.model.IMyFootModel;
import com.tz.xiyoulibrary.activity.myfoot.model.MyFootModel;
import com.tz.xiyoulibrary.activity.myfoot.view.IMyFootView;

public class MyFootPresenter {

	private IMyFootView mMyFootView;
	private IMyFootModel mMyFootModel;
	
	public MyFootPresenter(IMyFootView view){
		this.mMyFootView = view;
		mMyFootModel = new MyFootModel();
	}
	
	public void getFavoriteData(RequestQueue queue){
		mMyFootModel.getFavoriteData(queue, new CallBack<MyFootModel>() {
			
			@Override
			public void getModel(MyFootModel model) {
				switch (model.status) {
				case IMyFootModel.LOADING:
					mMyFootView.showLoadingView();
					break;
				case IMyFootModel.LOADING_FALUIRE:
					mMyFootView.showLoadFaluireView();
					mMyFootView.showMsg(model.msg);
					break;
				case IMyFootModel.NO_DATA:
					mMyFootView.showNoDataView();
					break;
				case IMyFootModel.LOADING_SUCCESS:
					mMyFootView.showFavoriteView(model.favoriteData);;
					break;
				}
			}
		});
	}
}
