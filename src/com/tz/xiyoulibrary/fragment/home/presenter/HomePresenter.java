package com.tz.xiyoulibrary.fragment.home.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.fragment.home.model.HomeModel;
import com.tz.xiyoulibrary.fragment.home.model.IHomeModel;
import com.tz.xiyoulibrary.fragment.home.view.IHomeView;

public class HomePresenter {
	IHomeView mhomeView;
	IHomeModel mhomeModel;
	CallBack<HomeModel> callBack;
	public HomePresenter(IHomeView homeView){
		this.mhomeView = homeView;
		mhomeModel = new HomeModel();
		 callBack = new CallBack<HomeModel>() {

			@Override
			public void getModel(HomeModel model) {
				switch (model.status) {
				case IHomeModel.LOADING:
					mhomeView.showLoadingView();
					
					break;
				case IHomeModel.LOADING_FALUIRE:
					mhomeView.showLoadFaluireView();
					mhomeView.showMsg(model.message);
					break;
				case IHomeModel.NO_DATA:
					mhomeView.showNoDataView();
					break;
				case IHomeModel.LOADING_SUCCESS:
					mhomeView.showHomeView(model.list);
					
					break;
				case IHomeModel.REFER_FALUIRE:
					mhomeView.showRefershFaluireView();
					mhomeView.showMsg(model.message);
					break;
				case IHomeModel.UP_REFER_SUCCESS:
					mhomeView.showUpRefershView(model.list);
					break;
				case IHomeModel.DOWN_REFER_SUCCESS:
					mhomeView.showDownRefershView(model.list);
					break;
				}
				
			}
		};
			
		}
	public void getHomeListData(RequestQueue queue,int CurrentPage,int whichTag){
		mhomeModel.getHomeData(queue, CurrentPage, whichTag, callBack);
		
	}
//	public int getListItemId(int position){
//		return mhomeModel.getItemId(position);
//	}
	
	public void refershHomeData(RequestQueue queue,int CurrentPage,int whichTag,int what){
		mhomeModel.refershHomeData(queue, CurrentPage, whichTag,what, callBack);
		
	}

}
