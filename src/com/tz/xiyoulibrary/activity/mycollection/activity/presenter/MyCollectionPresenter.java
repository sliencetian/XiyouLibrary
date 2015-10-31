package com.tz.xiyoulibrary.activity.mycollection.activity.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.mycollection.activity.model.IMyCollectionModel;
import com.tz.xiyoulibrary.activity.mycollection.activity.model.MyCollectionModel;
import com.tz.xiyoulibrary.activity.mycollection.activity.view.IMyCollectionView;

public class MyCollectionPresenter {

	private IMyCollectionView mMyCollectionView;
	private IMyCollectionModel mMyCollectionModel;

	public MyCollectionPresenter(IMyCollectionView view) {
		this.mMyCollectionView = view;
		mMyCollectionModel = new MyCollectionModel();
	}

	/**
	 * 获取收藏书籍
	 */
	public void getFavoriteData(RequestQueue queue) {
		mMyCollectionModel.getFavoriteData(queue,
				new CallBack<MyCollectionModel>() {

					@Override
					public void getModel(MyCollectionModel model) {
						switch (model.status) {
						case IMyCollectionModel.FALUIRE:
							mMyCollectionView.showGetDataFaluire();
							mMyCollectionView.showMsg(model.msg);
							break;
						case IMyCollectionModel.NO_DATA:
							mMyCollectionView.showGetDataNoData();
							break;
						case IMyCollectionModel.SUCCESS:
							mMyCollectionView
									.showFavoriteData(model.favoriteData);
							break;
						}
					}
				});
	}
}
