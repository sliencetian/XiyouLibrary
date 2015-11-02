package com.tz.xiyoulibrary.activity.myfoot.view;

import java.util.List;

import android.content.Context;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class MyFootAdapter extends CommonAdapter<BookBean> {

	public MyFootAdapter(Context context, List<BookBean> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, BookBean b) {
		helper.setText(R.id.tv_book_title_item_myfoot, b.getTitle());
		helper.setText(R.id.tv_book_author_item_myborrow, b.getAuthor());
		helper.setText(R.id.tv_book_pub_item_myfoot, b.getPub());
		helper.setText(R.id.tv_book_id_item_myfoot, b.getId());
	}

}
