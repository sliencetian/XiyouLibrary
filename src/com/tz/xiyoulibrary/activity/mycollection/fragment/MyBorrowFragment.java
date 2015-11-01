package com.tz.xiyoulibrary.activity.mycollection.fragment;

import java.util.Map;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.bookdetial.view.BookDetialActivity_;
import com.tz.xiyoulibrary.utils.Constants;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * User: shine Date: 2015-03-13 Time: 09:32 Description:
 */
@SuppressLint("ValidFragment")
public class MyBorrowFragment extends Fragment {

	public static MyBorrowFragment getInstance(Map<String, String> book) {
		MyBorrowFragment fragment = new MyBorrowFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Title", book.get("Title"));
		bundle.putString("Pub", book.get("Pub"));
		bundle.putString("Sort", book.get("Sort"));
		bundle.putString("ISBN", book.get("ISBN"));
		bundle.putString("Author", book.get("Author"));
		bundle.putString("ID", book.get("ID"));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Bundle bundle = getArguments();
		View rootView = inflater.inflate(R.layout.fragment_mycollection, null);

		// 进行布局的数据添加
		TextView title = (TextView) rootView
				.findViewById(R.id.tv_book_title_mycollection);
		title.setText(bundle.getString("Title"));
		TextView author = (TextView) rootView
				.findViewById(R.id.tv_book_author_mycollection);
		author.setText("作者：" + bundle.getString("Author"));
		TextView id = (TextView) rootView
				.findViewById(R.id.tv_book_id_mycollection);
		id.setText("编号：" + bundle.getString("ID"));
		TextView isbn = (TextView) rootView
				.findViewById(R.id.tv_book_isbn_mycollection);
		isbn.setText("条形码：" + bundle.getString("ISBN"));
		TextView sort = (TextView) rootView
				.findViewById(R.id.tv_book_sort_mycollection);
		sort.setText("索书号：" + bundle.getString("Sort"));
		TextView pub = (TextView) rootView
				.findViewById(R.id.tv_book_pub_mycollection);
		pub.setText("出版社：" + bundle.getString("Pub"));
		
		rootView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BookDetialActivity_.class);
				intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_ID+bundle.getString("ID"));
				startActivity(intent);
			}
		});

		return rootView;
	}
}
