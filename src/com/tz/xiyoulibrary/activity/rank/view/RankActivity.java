package com.tz.xiyoulibrary.activity.rank.view;

import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.view.BookDetialActivity_;
import com.tz.xiyoulibrary.activity.rank.model.IRankModel;
import com.tz.xiyoulibrary.activity.rank.presenter.RankPresenter;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.Constants;

@EActivity(R.layout.activity_rank)
public class RankActivity extends BaseActivity implements IRankView {

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
	private RequestQueue queue;

	private List<Map<String, String>> rankData;
	private RankPresenter mPresenter;

	@ViewById(R.id.ptrlv_activity_rank)
	PullToRefreshListView mRefreshListView;
	private RankAdapter mRankAdapter;

	@ViewById(R.id.sp_sort_activity_rank)
	Spinner mSpinner;

	private String type = "1";

	private String[] typeItem = { "借阅排行榜", "收藏排行榜", "查看排行榜" };
	private String[] typeItemNumb = { "1", "3", "5" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		queue = Volley.newRequestQueue(RankActivity.this);
		mPresenter = new RankPresenter(this);

	}

	@AfterViews
	public void initWidgetAfter() {
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("我的足迹");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, typeItem);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);

		mRefreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						refreshRankData(IRankModel.DOWN_REFER_SUCCESS);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						refreshRankData(IRankModel.UP_REFER_SUCCESS);
					}
				});
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0)
					return;
				TextView tv = (TextView) view
						.findViewById(R.id.tv_id_item_activity_rank);
				Intent intent = new Intent(RankActivity.this,
						BookDetialActivity_.class);
				intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_ID
						+ tv.getText().toString());
				startActivity(intent);
			}
		});
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				type = typeItemNumb[arg2];
				getRankData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
		// 获取排行信息
		getRankData();
	}

	/**
	 * 获取排行信息
	 */
	private void getRankData() {
		mSpinner.setClickable(false);
		mPresenter.getRankData(queue, type);
	}

	/**
	 * 获取排行信息
	 */
	private void refreshRankData(int what) {
		mSpinner.setClickable(false);
		mPresenter.refershData(queue, type, rankData.size(), what);
	}

	@Override
	public void showLoadingView() {
		mSpinner.setClickable(true);
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);
	}

	@Override
	public void showRankView(List<Map<String, String>> rankData) {
		mRefreshListView.onRefreshComplete();
		mSpinner.setClickable(true);
		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.rankData = rankData;

		mRankAdapter = new RankAdapter(RankActivity.this, rankData,
				R.layout.item_activity_rank);
		mRefreshListView.setAdapter(mRankAdapter);
	}

	@Override
	public void showRefershFaluireView() {
		mSpinner.setClickable(true);
		mRefreshListView.onRefreshComplete();
	}

	@Override
	public void showUpRefershView(List<Map<String, String>> rankData) {
		mSpinner.setClickable(true);
		mRefreshListView.onRefreshComplete();
		this.rankData.addAll(rankData);
		mRankAdapter.setDatas(this.rankData);

	}

	@Override
	public void showDownRefershView(List<Map<String, String>> rankData) {
		mSpinner.setClickable(true);
		mRefreshListView.onRefreshComplete();
		this.rankData = rankData;
		mRankAdapter.setDatas(this.rankData);
	}

	@Override
	public void showLoadFaluireView() {
		mSpinner.setClickable(true);
		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
	}

	@Override
	public void showNoDataView() {
		mSpinner.setClickable(true);
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mTextViewTip.setText("哦啊~当前没有排行信息!");
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Click(R.id.rl_load_faluire)
	public void resetData() {
		getRankData();
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

}
