package com.tz.xiyoulibrary.activity.bookdetial.view;

import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.presenter.BookDetialPresenter;
import com.tz.xiyoulibrary.pulltozoomview.PullToZoomListView;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.LogUtils;

@EActivity(R.layout.activity_book_detial)
public class BookDetialActivity extends BaseActivity implements IBookDetialView {
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
	private RequestQueue queue;

	@ViewById(R.id.ptzv_book_detial)
	PullToZoomListView mPullToZoomListView;
	private Map<String, Object> bookDetial;

	private String BarCode;
	private BookDetialPresenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		queue = Volley.newRequestQueue(BookDetialActivity.this);
		BarCode = getIntent().getStringExtra("BarCode");
		mPresenter = new BookDetialPresenter(this);

		LogUtils.d("BookDetialActivity.BarCode", BarCode);
	}

	@AfterViews
	public void initWidgetAfter() {
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("图书详情");
		// 获取图书详情
		getBookDetial();
	}

	/**
	 * 获取图书详情
	 */
	private void getBookDetial() {
		mPresenter.getBookDetial(queue, BarCode);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);
	}

	@Override
	public void showBookDetialView(Map<String, Object> bookDetial) {
		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.bookDetial = bookDetial;

		LayoutInflater inflater = LayoutInflater.from(BookDetialActivity.this);
		RelativeLayout foot = (RelativeLayout) inflater.inflate(
				R.layout.activity_book_detial_foot, null);
		// 初始化基本资料
		addOneView(foot);

		mPullToZoomListView.addFooterView(foot);
		String[] adapterData = new String[] {};
		mPullToZoomListView.setAdapter(new ArrayAdapter<String>(
				BookDetialActivity.this, android.R.layout.simple_list_item_1,
				adapterData));
		mPullToZoomListView.getHeaderView().setImageResource(
				R.drawable.img_book);
		mPullToZoomListView.getHeaderView().setScaleType(
				ImageView.ScaleType.CENTER_CROP);

	}

	private void addOneView(RelativeLayout root) {
		TextView tv;
		tv = (TextView) root.findViewById(R.id.tv_title_activity_book_detial);// 标题
		tv.setText(bookDetial.get("Title") + "");
		tv = (TextView) root.findViewById(R.id.tv_book_id_activity_book_detial);// 索书号
		tv.setText(bookDetial.get("ID") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_author_activity_book_detial);// 作者
		tv.setText(bookDetial.get("Author") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_page_activity_book_detial);// 页数
		try {
			tv.setText(bookDetial.get("Pages") + " 页");
		} catch (Exception e) {
			tv.setText(bookDetial.get("Form") + " 页");
		}

		tv = (TextView) root
				.findViewById(R.id.tv_book_avaliable_activity_book_detial);// 可借数量
		tv.setText(bookDetial.get("Avaliable") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_renttimes_activity_book_detial);// 借阅次数
		tv.setText(bookDetial.get("RentTimes") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_favtimes_activity_book_detial);// 收藏次数
		tv.setText(bookDetial.get("FavTimes") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_browsetimes_activity_book_detial);// 浏览次数
		tv.setText(bookDetial.get("BrowseTimes") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_total_activity_book_detial);// 藏书数量
		tv.setText(bookDetial.get("Total") + " 本");

		tv = (TextView) root
				.findViewById(R.id.tv_book_subject_activity_book_detial);// 主题分类
		tv.setText(bookDetial.get("Subject") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_pub_activity_book_detial);// 出版社
		tv.setText(bookDetial.get("Pub") + "");
	}

	@Override
	public void showLoadFaluireView() {
		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
	}

	@Override
	public void showNoDataView() {
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		queue.cancelAll(this);
	}
}
