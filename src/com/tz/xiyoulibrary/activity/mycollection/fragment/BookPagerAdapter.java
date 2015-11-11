package com.tz.xiyoulibrary.activity.mycollection.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.android.volley.toolbox.ImageLoader;

/**
 * User: shine Date: 2015-03-13 Time: 09:21 Description:
 */
@SuppressWarnings("unchecked")
public class BookPagerAdapter extends FragmentStatePagerAdapter {

	private List<Map<String, String>> mBookList;
	@SuppressWarnings("rawtypes")
	private List<Fragment> mFragments = new ArrayList();

	public BookPagerAdapter(FragmentManager fragmentManager,
			List<Map<String, String>> bookList,ImageLoader imageLoader) {
		super(fragmentManager);
		int position = 0;
		// ʹ�õ���������List,
		@SuppressWarnings("rawtypes")
		Iterator iterator = bookList.iterator();
		while (iterator.hasNext()) {
			position++;
			Map<String, String> book = (Map<String, String>) iterator.next();
			book.put("position", position + "");
			// ʵ������Ӧ��Fragment����ӵ�List��
			mFragments.add(MyBorrowFragment.getInstance(book,imageLoader));
		}
		mBookList = bookList;
	}

	public int getCount() {
		return mFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	public List<Map<String, String>> getCardList() {
		return mBookList;
	}

	public List<Fragment> getFragments() {
		return mFragments;
	}

	public void setDatas(List<Map<String, String>> bookList) {
		this.mBookList = bookList;
		notifyDataSetChanged();
	}
}
