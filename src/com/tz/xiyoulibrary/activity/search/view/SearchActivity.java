package com.tz.xiyoulibrary.activity.search.view;

import java.util.List;
import java.util.Map;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.view.BookDetialActivity_;
import com.tz.xiyoulibrary.activity.search.presenter.SearchPresenter;
import com.tz.xiyoulibrary.bean.HistoryBean;
import com.tz.xiyoulibrary.utils.ACache;
import com.tz.xiyoulibrary.utils.Constants;

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements ISearchView {
	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;
	@ViewById(R.id.et_search)
	EditText et_search;
	@ViewById(R.id.iv_delete)
	ImageView iv_delete;
	@ViewById(R.id.search_list)
	ListView search_list;
	@ViewById(R.id.layout_search_no_data)
	RelativeLayout layout_search_no_data;

	private String keyword = ""; // 搜索的内容
	private SearchPresenter mPresenter;
	private RequestQueue queue;
	private SearchAdapter mSearchAdapter;
	private String item_id;
	private ACache acache;
	private HistoryBean mHistory;
	private List<Map<String, String>> bookList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	public void init() {

		mTextViewTitle.setText("图书检索");
		mRelativeLayoutBack.setVisibility(View.VISIBLE);

		acache = ACache.get(this);
		Object historyList = acache.getAsObject("history_list");
		if (historyList != null) {
			mHistory = (HistoryBean) historyList;
			showBookListView(mHistory.getHistoryList());
		} else {
			mHistory = new HistoryBean();
			System.out.println("进入else");
		}
		mPresenter = new SearchPresenter(this);
		queue = Volley.newRequestQueue(SearchActivity.this);
		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable content) {
				// TODO Auto-generated method stub
				System.out.println(content.length());
				if (content.length() > 0) {
					keyword = content.toString();
					iv_delete.setVisibility(View.VISIBLE);
					System.out.println("keyword = " + keyword);
					mPresenter.getSearchList(queue, keyword);

				} else {
					// 显示历史记录
					queue.cancelAll("search");
					iv_delete.setVisibility(View.GONE);
					// search_list.setVisibility(View.GONE);
					showBookListView(mHistory.getHistoryList());

				}

			}
		});

		search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				System.out.println("position" + position);
				TextView tv_id = (TextView) view.findViewById(R.id.book_id);
				// item_id = mPresenter.getItemId(position);
				item_id = tv_id.getText().toString();
				System.out.println("ID=" + item_id);
				System.out.println("bookList : " + bookList);
				Map<String, String> map = bookList.get(position);
				mHistory.getHistoryList().remove(map);
				System.out.println(bookList);
				mHistory.getHistoryList().add(map);
				acache.put("history_list", mHistory);

				// 写入历史记录
				// acacheList.add(item_id);

				Intent intent = new Intent(SearchActivity.this,
						BookDetialActivity_.class);
				intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_ID
						+ item_id);
				startActivity(intent);

			}
		});

	}

	@Click(R.id.iv_delete)
	public void deleteText() {
		et_search.setText("");
		iv_delete.setVisibility(View.GONE);
	}

	@Override
	public void showBookListView(List<Map<String, String>> bookList) {
		this.bookList = bookList;
		System.out.println(this.bookList);
		layout_search_no_data.setVisibility(View.GONE);
		search_list.setVisibility(View.VISIBLE);
		mSearchAdapter = new SearchAdapter(SearchActivity.this, bookList,
				R.layout.item_activity_search);
		search_list.setAdapter(mSearchAdapter);
	}

	@Override
	public void showNoDataView() {
		// TODO Auto-generated method stub
		layout_search_no_data.setVisibility(View.VISIBLE);
		search_list.setVisibility(View.GONE);

	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

}
