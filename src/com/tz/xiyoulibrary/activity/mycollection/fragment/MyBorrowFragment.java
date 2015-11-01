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
		View rootView = inflater.inflate(R.layout.fragment_mycollection, null);

		// ���в��ֵ��������
		TextView title = (TextView) rootView
				.findViewById(R.id.tv_book_title_mycollection);
		title.setText(bundle.getString("Title"));
		TextView author = (TextView) rootView
				.findViewById(R.id.tv_book_author_mycollection);
		author.setText("���ߣ�" + bundle.getString("Author"));
		TextView id = (TextView) rootView
				.findViewById(R.id.tv_book_id_mycollection);
		id.setText("��ţ�" + bundle.getString("ID"));
		TextView isbn = (TextView) rootView
				.findViewById(R.id.tv_book_isbn_mycollection);
		isbn.setText("�����룺" + bundle.getString("ISBN"));
		TextView sort = (TextView) rootView
				.findViewById(R.id.tv_book_sort_mycollection);
		sort.setText("����ţ�" + bundle.getString("Sort"));
		TextView pub = (TextView) rootView
				.findViewById(R.id.tv_book_pub_mycollection);
		pub.setText("�����磺" + bundle.getString("Pub"));

		return rootView;
	}
}
