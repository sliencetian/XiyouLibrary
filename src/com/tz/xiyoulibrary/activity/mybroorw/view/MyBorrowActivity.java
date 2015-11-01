package com.tz.xiyoulibrary.activity.mybroorw.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.view.BookDetialActivity_;
import com.tz.xiyoulibrary.activity.mybroorw.presenter.MyBorrowPresenter;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.Constants;

/**
 * 
 * @author Administrator �ҵĽ���
 */
@EActivity(R.layout.activity_myborrow)
public class MyBorrowActivity extends BaseActivity implements IMyborrowView {

	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;

	@ViewById(R.id.lv_borrow_activity_myborrow)
	ListView mListViewBorrow;
	private MyBorrowAdapter mMyBorrowAdapter;
	private RequestQueue queue;
	private MyBorrowPresenter mPresenter;

	private Titanic mTitanic;
	@ViewById(R.id.loading_text)
	TitanicTextView mTitanicTextView;

	@ViewById(R.id.rl_loading)
	RelativeLayout mRelativeLayoutLoading;
	@ViewById(R.id.rl_load_faluire)
	RelativeLayout mRelativeLayoutLoadFaluire;
	@ViewById(R.id.rl_load_no_data)
	RelativeLayout mRelativeLayoutLoadNoData;

	private List<BookBean> borrowData;

	private int borrow = 0;// �ѽ�ͼ������
	private int canBorrow = 0;// ʣ��ɽ�����
	private int renewBorrow = 0;// ����ͼ������
	private int exceedBorrow = 0;// ����ͼ������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPresenter = new MyBorrowPresenter(this);
		queue = Volley.newRequestQueue(MyBorrowActivity.this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("�ҵĽ���");
		// ��ȡ��������
		getBorrowData();
	}

	/**
	 * ��ȡ��������
	 */
	private void getBorrowData() {
		mPresenter.getBorrowData(queue);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@ItemClick(R.id.lv_borrow_activity_myborrow)
	public void pushBookDetial(int position) {
		Intent intent = new Intent(MyBorrowActivity.this,
				BookDetialActivity_.class);
		intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_BARCODE
				+ borrowData.get(position - 1).getBarCode());
		startActivity(intent);
	}

	@Click(R.id.rl_load_faluire)
	public void resetLoad() {
		// ���¼���
		getBorrowData();
	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);
	}

	@Override
	public void showBorrowView(List<BookBean> borrowData) {
		// �رռ��ض���
		mTitanic.cancel();
		// ���ؼ�����ͼ
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.borrowData = borrowData;
		// ��ʼ����������
		initBorrowCount();
		LayoutInflater inflater = LayoutInflater.from(MyBorrowActivity.this);

		// ���ͷ����
		LinearLayout head = (LinearLayout) inflater.inflate(
				R.layout.activity_myborrow_head, null);
		TextView tv_borrow = (TextView) head
				.findViewById(R.id.tv_borrow_book_activity_my_borrow);
		tv_borrow.setText("�ѽ�ͼ�飺   " + borrow + "��");
		TextView tv_can_borrow = (TextView) head
				.findViewById(R.id.tv_can_borrow_book_activity_my_borrow);
		tv_can_borrow.setText("ʣ��ɽ裺   " + canBorrow + "��");
		TextView tv_renew_borrow = (TextView) head
				.findViewById(R.id.tv_renew_book_activity_my_borrow);
		tv_renew_borrow.setText("����ͼ�飺   " + renewBorrow + "��");
		TextView tv_exceed_borrow = (TextView) head
				.findViewById(R.id.tv_exceed_book_activity_my_borrow);
		tv_exceed_borrow.setText("����ͼ�飺   " + exceedBorrow + "��");

		mListViewBorrow.addHeaderView(head);

		mMyBorrowAdapter = new MyBorrowAdapter(MyBorrowActivity.this,
				this.borrowData, R.layout.item_activity_myborrow);
		mListViewBorrow.setAdapter(mMyBorrowAdapter);
	}

	private void initBorrowCount() {
		borrow = borrowData.size();
		canBorrow = 15 - borrow;
		for (BookBean b : borrowData) {
			if (b.getState().equals("��������")) {
				renewBorrow++;
			} else if (b.getState().equals("������ͣ")) {
				exceedBorrow++;
			}
		}
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
