package com.tz.xiyoulibrary.activity.mybroorw.view;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class MyBorrowAdapter extends CommonAdapter<BookBean> {

	public MyBorrowAdapter(Context context, List<BookBean> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, final BookBean b) {
		helper.setText(R.id.tv_book_title_item_myborrow, b.getTitle());
		helper.setText(R.id.tv_book_department_item_myborrow,
				"·Ö¹Ý:" + b.getDepartment());
		helper.setText(R.id.tv_book_data_item_myborrow, b.getDate());
		TextView tv = helper.getView(R.id.tv_book_state_item_myborrow);
		Button bt = helper.getView(R.id.bt_book_state_item_myborrow);
		if (b.getCanRenew()) {
			bt.setVisibility(View.VISIBLE);
			tv.setVisibility(View.GONE);
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					
				}
			});
		} else {
			bt.setVisibility(View.GONE);
			tv.setVisibility(View.VISIBLE);
			tv.setText(b.getState());
		}
	}
}
