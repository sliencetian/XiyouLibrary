package com.tz.xiyoulibrary.activity.search.presenter;

import com.android.volley.RequestQueue;
import com.tz.xiyoulibrary.activity.callback.CallBack;
import com.tz.xiyoulibrary.activity.search.model.ISearchModel;
import com.tz.xiyoulibrary.activity.search.model.SearchModel;
import com.tz.xiyoulibrary.activity.search.view.ISearchView;

public class SearchPresenter {
	public ISearchModel searchModel;
	public ISearchView searchView;
	public CallBack<SearchModel> callBack;
	
	public SearchPresenter(ISearchView view){
		this.searchView = view;
		searchModel = new SearchModel();
		callBack = new CallBack<SearchModel>() {

			@Override
			public void getModel(SearchModel model) {
				// TODO Auto-generated method stub
				switch (model.status) {
				case ISearchModel.SEARCH_FAILURE:
//					不再显示
					System.out.println("failure");
					break;
				case ISearchModel.SEARCH_NO_DATA:
					System.out.println("没有数据");
					searchView.showNoDataView();
					break;
				case ISearchModel.SEARCH_SUCCESS:
					System.out.println("进入success");
					searchView.showBookListView(model.contentList);
					System.out.println("list="+model.contentList);
					
					break;
				
				}
				
			}
		};
	}
	
	public void getSearchList(RequestQueue queue,String keyword){
		searchModel.SearchQuery(queue,keyword , callBack);
		
		
	}
	public String getItemId(int position){
		return searchModel.getId(position);
		
	}

}
