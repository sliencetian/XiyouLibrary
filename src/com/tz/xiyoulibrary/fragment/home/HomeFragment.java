package com.tz.xiyoulibrary.fragment.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.homedetail.view.HomeDetailActivity_;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.customerviewpager.CustomerViewPage;
import com.tz.xiyoulibrary.fragment.home.model.IHomeModel;
import com.tz.xiyoulibrary.fragment.home.presenter.HomePresenter;
import com.tz.xiyoulibrary.fragment.home.view.HomeAdapter;
import com.tz.xiyoulibrary.fragment.home.view.IHomeView;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.LogUtils;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements IHomeView {

	@ViewById(R.id.news)
	TextView news;
	@ViewById(R.id.notices)
	TextView notices;
	@ViewById(R.id.homelist)
	PullToRefreshListView homeListView;
	@ViewById(R.id.home_pager)
	CustomerViewPage homeViewPager;
	@ViewById(R.id.viewGroup)
	ViewGroup homeViewGroup;
	@ViewById(R.id.loading_text)
	TitanicTextView mTitanicTextView;
	@ViewById(R.id.rl_loading)
	RelativeLayout mRelativeLayoutLoading;
	@ViewById(R.id.rl_load_faluire)
	RelativeLayout mRelativeLayoutLoadFaluire;
	@ViewById(R.id.rl_load_no_data)
	RelativeLayout mRelativeLayoutLoadNoData;
	@ViewById(R.id.tv_load_no_data_tip)
	TextView mTextViewTip;
	@ViewById(R.id.layout_home)
	RelativeLayout layout_home;

	Titanic mTitanic;

	private RequestQueue queue;
	private HomePresenter presenter;
	private HomeAdapter homeAdapter;
	private int whichTag = 0; // 0 公告 1 新闻
	private int currentNewsPage = 1;
	private int currentNoticesPage = 1;
	private int item_id;
	private List<Map<String, Object>> homeListData = new ArrayList<Map<String, Object>>();

	// 写到判断是否需要再请求

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@AfterViews
	public void init() {
		System.out.println("进入homefragment");
		news.setBackgroundColor(this.getResources()
				.getColor(R.color.home_title));

		presenter = new HomePresenter(this);
		queue = Volley.newRequestQueue(getActivity());
		homeListView.setMode(Mode.BOTH);

		mTitanicTextView.setTypeface(Typefaces.get(getActivity(),
				"Satisfy-Regular.ttf"));
		mTitanic = new Titanic();

		// 初始化viewpager中的图片
		initViewPager();

		if (Application.HOME_RESPONSE == "") {

			if (whichTag == 0) {

				this.presenter.getHomeListData(queue, currentNoticesPage,
						whichTag);
			} else {
				this.presenter
						.getHomeListData(queue, currentNewsPage, whichTag);
			}
		} else {
			if (whichTag == 0) {
				homeListData = Application.HOME_NOTICE_LIST;
			} else {
				homeListData = Application.HOME_NEWS_LIST;
			}
			showHomeView(homeListData);
		}

		// 点击列表选项
		homeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				if (position == 0)
					return;
				// item_id = presenter.getListItemId(position-1);
				// 直接从homeListData中获取
				item_id = (Integer) homeListData.get(position - 1).get("ID");
				System.out.println("item id= " + item_id);
				Intent intent = new Intent(getActivity(),
						HomeDetailActivity_.class);
				if (whichTag == 0) {
					intent.putExtra("laterUrl", "announce/html/" + item_id);
				} else {
					intent.putExtra("laterUrl", "news/html/" + item_id);
				}
				startActivity(intent);
			}
		});

		// 设置下拉刷新和上拉加载
		homeListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (whichTag == 0) {
							if (currentNoticesPage > 1) {
								currentNoticesPage = currentNoticesPage - 1;
							}
							presenter.refershHomeData(queue,
									currentNoticesPage, whichTag,
									IHomeModel.DOWN_REFER_SUCCESS);

						} else {
							if (currentNewsPage > 1) {
								currentNewsPage = currentNewsPage - 1;
							}
							presenter.refershHomeData(queue, currentNewsPage,
									whichTag, IHomeModel.DOWN_REFER_SUCCESS);
						}
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (whichTag == 0) {
							currentNoticesPage = currentNoticesPage + 1;
							presenter.refershHomeData(queue,
									currentNoticesPage, whichTag,
									IHomeModel.UP_REFER_SUCCESS);
						} else {
							currentNewsPage = currentNewsPage + 1;
							presenter.refershHomeData(queue, currentNewsPage,
									whichTag, IHomeModel.UP_REFER_SUCCESS);
						}

					}
				});

	}

	// 写到点击事件
	@Click(R.id.news)
	public void news() {
		news.setBackgroundColor(this.getResources().getColor(R.color.white));
		notices.setBackgroundColor(this.getResources().getColor(
				R.color.home_title));
		whichTag = 1;

		if (Application.HOME_NEWS_LIST == null) {
			presenter.getHomeListData(queue, currentNewsPage, whichTag);
		} else {
			this.homeListData = Application.HOME_NEWS_LIST;
			showHomeView(homeListData);
		}

	}

	@Click(R.id.notices)
	public void notices() {
		notices.setBackgroundColor(this.getResources().getColor(R.color.white));
		news.setBackgroundColor(this.getResources()
				.getColor(R.color.home_title));
		whichTag = 0;

		if (Application.HOME_NOTICE_LIST == null) {
			presenter.getHomeListData(queue, currentNoticesPage, whichTag);
		} else {
			this.homeListData = Application.HOME_NOTICE_LIST;
			showHomeView(homeListData);

		}

	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);

	}

	@Override
	public void showHomeView(List<Map<String, Object>> homeListData) {
		homeListView.onRefreshComplete();
		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);

		this.homeListData = homeListData;
		System.out.println("homelistdata = " + this.homeListData);
		homeAdapter = new HomeAdapter(getActivity(), homeListData,
				R.layout.item_home_list);
		homeListView.setAdapter(homeAdapter);

	}

	@Override
	public void showLoadFaluireView() {
		homeListView.onRefreshComplete();

		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);

	}

	@Override
	public void showNoDataView() {
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mTextViewTip.setText("哦啊~当前没有信息!");
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);

	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(getActivity(), msg, 2000);

	}

	@Override
	public void showRefershFaluireView() {
		homeListView.onRefreshComplete();

	}

	@Override
	public void showUpRefershView(List<Map<String, Object>> homeListData) {
		homeListView.onRefreshComplete();
		this.homeListData.addAll(homeListData);
		homeAdapter.setDatas(this.homeListData);

	}

	@Override
	public void showDownRefershView(List<Map<String, Object>> homeListData) {
		homeListView.onRefreshComplete();
		this.homeListData = homeListData;
		homeAdapter.setDatas(this.homeListData);

	}

	@Override
	public void initViewPager() {
		homeViewPager.setViewPageViews(Application.homeAdViews);
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.d("HomeFragment : ", "onPause()");
		homeViewPager.stop();
	}

}
