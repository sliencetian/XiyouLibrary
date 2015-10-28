package com.tz.xiyoulibrary.activity.myfootprint.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tz.xiyoulibrary.bean.BookBean;

/**
 * User: shine
 * Date: 2015-03-13
 * Time: 09:21
 * Description:
 */
@SuppressWarnings("unchecked")
public class BookPagerAdapter extends FragmentStatePagerAdapter {

    private List<BookBean> mCardList;
	@SuppressWarnings("rawtypes")
	private List<Fragment> mFragments = new ArrayList();

    public BookPagerAdapter(FragmentManager fragmentManager, List<BookBean> cardList) {
        super(fragmentManager);
        //使用迭代器遍历List,
        @SuppressWarnings("rawtypes")
		Iterator iterator = cardList.iterator();
        while (iterator.hasNext()) {
        	BookBean book = (BookBean) iterator.next();
            //实例化相应的Fragment并添加到List中
            mFragments.add(MyBorrowFragment.getInstance(book));
        }
        mCardList = cardList;
    }

    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public List<BookBean> getCardList() {
        return mCardList;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }
}
