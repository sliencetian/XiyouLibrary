package com.tz.xiyoulibrary.activity.myfootprint.fragment;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.bean.BookBean;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * User: shine Date: 2015-03-13 Time: 09:32 Description:
 */
@SuppressLint("ValidFragment")
public class MyBorrowFragment extends Fragment {
	
	public static MyBorrowFragment getInstance(BookBean book) {
		MyBorrowFragment fragment = new MyBorrowFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("book", book);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		BookBean book = (BookBean) bundle.getSerializable("book");
		View rootView = inflater.inflate(R.layout.fragment_myfoot, null);
		
		//进行布局的数据添加
		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(book.getTitle());
		
		return rootView;
	}
}
