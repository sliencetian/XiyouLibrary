package com.tz.xiyoulibrary.activity.homedetail.view;

import java.util.Map;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.homedetail.presenter.HomeDetailPresenter;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.LogUtils;

@SuppressLint("HandlerLeak")
@EActivity(R.layout.home_list_detail)
public class HomeDetailActivity extends BaseActivity implements IHomeDetailView {

	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;

	Titanic mTitanic;
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

	@ViewById(R.id.layout_homedetail)
	RelativeLayout layout_homedetail;
	@ViewById(R.id.homedetail)
	WebView homeDetailWebView;
	@ViewById(R.id.iv_home_moreoption)
	ImageView home_MoreOption;
	@ViewById(R.id.publisher)
	TextView homedetail_published;
	@ViewById(R.id.publish_date)
	TextView homedetail_publish_date;
	@ViewById(R.id.homeTitle)
	TextView homedetail_title;
	
	@ViewById(R.id.layout_publisher)
	LinearLayout layout_publisher;
	@ViewById(R.id.layout_publish_date)
	LinearLayout layout_publish_date;

	private String url;
	private HomeDetailPresenter mPresenter;
	private RequestQueue queue;
	private Map<String, String> detailMap;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queue = Volley.newRequestQueue(HomeDetailActivity.this);
		url = Constants.GET_NEWS_DETAIL
				+ getIntent().getStringExtra("laterUrl");
		mPresenter = new HomeDetailPresenter(this);
		LogUtils.d("HomeDetailActivity : ", "传过来的url=" + url);

	}

	@AfterViews
	public void init() {
		homeDetailWebView.setVerticalScrollBarEnabled(false); // 垂直不显示 ,去掉垂直滚动条

		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("详情");
		mPresenter.getHomeDetailData(queue, url);
	}

	@Click(R.id.iv_home_moreoption)
	public void moreOption() {
		// 弹出更多选项

	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);

	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			layout_publisher.setVisibility(View.VISIBLE);
			layout_publish_date.setVisibility(View.VISIBLE);
			homedetail_published.setText(detailMap.get("Publisher"));
			homedetail_publish_date.setText(detailMap.get("Date"));
		};
	};

	@Override
	public void showDetailData(Map<String, String> detailMap) {

		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.detailMap = detailMap;
		
		homedetail_title.setText(detailMap.get("Title"));
		System.out.println("title = " + detailMap.get("Title"));
		System.out.println("passage = " + detailMap.get("Passage"));
		homeDetailWebView.loadDataWithBaseURL(null, detailMap.get("Passage"),
				"text/html", "utf-8", null);
		handler.sendEmptyMessageDelayed(0, 1000);
	}

	@Override
	public void showNoDataView() {
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mTextViewTip.setText("哦啊~当前没有排行信息!");
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);

	}

	@Override
	public void showLoadFailView() {
		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);

	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Click(R.id.rl_load_faluire)
	public void resetData() {
		mPresenter.getHomeDetailData(queue, url);

	}

}
