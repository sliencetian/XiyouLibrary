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
		Bundle bundle = getArguments();
		View rootView = inflater.inflate(R.layout.fragment_myfoot, null);

		// 进行布局的数据添加
		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(bundle.getString("Title"));

		return rootView;
	}
}
