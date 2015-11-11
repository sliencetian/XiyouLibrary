package com.tz.xiyoulibrary.activity.myfoot.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.view.BookDetialActivity_;
import com.tz.xiyoulibrary.activity.myfoot.presenter.MyFootPresenter;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.BitmapCache;
import com.tz.xiyoulibrary.utils.Constants;

/**
 * 
 * @author Administrator 我的收藏
 */

@EActivity(R.layout.activity_myfoot)
public class MyFootActivity extends BaseActivity implements IMyFootView {

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
	private ImageLoader imageLoader;

	private List<BookBean> favoriteData;
	private MyFootPresenter mPresenter;
	@ViewById(R.id.lv_favorite_activity_myfoot)
	ListView mListViewMyFoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		queue = Volley.newRequestQueue(MyFootActivity.this);
		imageLoader = new ImageLoader(queue, new BitmapCache());
		mPresenter = new MyFootPresenter(this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("我的足迹");
		// 获取历史详情
		getFavoriteData();
	}

	/**
	 * 获取历史详情
	 */
	private void getFavoriteData() {
		mPresenter.getFavoriteData(queue);
	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);
	}

	@Override
	public void showFavoriteView(List<BookBean> favoriteData) {
		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.favoriteData = favoriteData;

		MyFootAdapter adapter = new MyFootAdapter(MyFootActivity.this,
				this.favoriteData, R.layout.item_activity_myfoot,imageLoader);
		mListViewMyFoot.setAdapter(adapter);
		mListViewMyFoot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view
						.findViewById(R.id.tv_book_id_item_myfoot);
				Intent intent = new Intent(MyFootActivity.this,
						BookDetialActivity_.class);
				intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_ID
						+ tv.getText().toString());
				startActivity(intent);
			}
		});
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
		mTextViewTip.setText("亲！您还没有借过书呢~");
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
	}
	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}
	@Click(R.id.rl_load_faluire)
	public void resetData(){
		getFavoriteData();
	}
	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}
}
