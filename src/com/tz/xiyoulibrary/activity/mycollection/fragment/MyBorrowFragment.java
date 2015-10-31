package com.tz.xiyoulibrary.activity.mycollection.fragment;

import java.util.Map;
import com.tz.xiyoulibrary.R;
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

	public static MyBorrowFragment getInstance(Map<String, String> book) {
		MyBorrowFragment fragment = new MyBorrowFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title", book.get("title"));
		bundle.putString("pub", book.get("pub"));
		bundle.putString("sort", book.get("sort"));
		bundle.putString("ISBN", book.get("ISBN"));
		bundle.putString("author", book.get("author"));
		bundle.putString("id", book.get("id"));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		View rootView = inflater.inflate(R.layout.fragment_myfoot, null);

		// 进行布局的数据添加
		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(bundle.getString("title"));

		return rootView;
	}
}
