package com.tz.xiyoulibrary.activity.mybroorw.view;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class MyBorrowAdapter extends CommonAdapter<BookBean> {
	private ImageLoader imageLoader;

	public MyBorrowAdapter(Context context, List<BookBean> mDatas,
			int itemLayoutId, ImageLoader imageLoader) {
		super(context, mDatas, itemLayoutId);
		this.imageLoader = imageLoader;
	}

	@Override
	public void convert(ViewHolder helper, final BookBean b) {
		helper.setText(R.id.tv_book_title_item_myborrow, b.getTitle());
		helper.setText(R.id.tv_book_department_item_myborrow,
				"·Ö¹Ý:" + b.getDepartment());
		helper.setText(R.id.tv_book_data_item_myborrow, b.getDate());
		ImageView bookImg = helper.getView(R.id.iv_book_img_item_myborrow);
		String imgUrl = b.getImgUrl();
		bookImg.setTag(imgUrl);
		if (TextUtils.equals(imgUrl, "")) {
			bookImg.setBackgroundResource(R.drawable.img_book_no);
		} else if (bookImg.getTag().toString().equals(imgUrl)) {
			ImageListener imageListener = ImageLoader.getImageListener(bookImg,
					R.drawable.img_book, R.drawable.img_book_no);
			imageLoader.get(imgUrl, imageListener, 240, 320);
		}
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
