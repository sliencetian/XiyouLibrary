package com.tz.xiyoulibrary.activity.mycollection.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: shine
 * Date: 2015-03-13
 * Time: 09:21
 * Description:
 */
@SuppressWarnings("unchecked")
public class BookPagerAdapter extends FragmentStatePagerAdapter {

    private List<Map<String, String>> mBookList;
	@SuppressWarnings("rawtypes")
	private List<Fragment> mFragments = new ArrayList();

    public BookPagerAdapter(FragmentManager fragmentManager, List<Map<String, String>> bookList) {
        super(fragmentManager);
        //使用迭代器遍历List,
        @SuppressWarnings("rawtypes")
		Iterator iterator = bookList.iterator();
        while (iterator.hasNext()) {
        	Map<String, String> book = (Map<String, String>) iterator.next();
            //实例化相应的Fragment并添加到List中
            mFragments.add(MyBorrowFragment.getInstance(book));
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
}
