package com.tz.xiyoulibrary.activity.homedetail.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.homedetail.model.HomeDetailModel;
import com.tz.xiyoulibrary.activity.homedetail.model.IHomeDetailModel;
import com.tz.xiyoulibrary.activity.homedetail.view.IHomeDetailView;

public class HomeDetailPresenter {
	IHomeDetailModel detailModel;
	IHomeDetailView detailView;
	
	public HomeDetailPresenter(IHomeDetailView view){
		detailModel = new HomeDetailModel();
		this.detailView = view;
	}
	
	
	
	public void getHomeDetailData(RequestQueue queue,String url){
		detailModel.getDetailData(queue, url, new CallBack<HomeDetailModel>() {
			
			@Override
			public void getModel(HomeDetailModel model) {
				// TODO Auto-generated method stub
				switch (model.status) {
				case IHomeDetailModel.LOADING:
					detailView.showLoadingView();
					break;
				case IHomeDetailModel.LOADING_FALUIRE:
					detailView.showLoadFailView();
					detailView.showMsg(model.message);
					break;
				case IHomeDetailModel.LOADING_SUCCESS:
					System.out.println("已经到presenter的加载成功");
					detailView.showDetailData(model.detailMap);
					break;
				case IHomeDetailModel.NO_DATA:
					detailView.showNoDataView();
					break;
				}
			}
		});
		
		
	}

}
