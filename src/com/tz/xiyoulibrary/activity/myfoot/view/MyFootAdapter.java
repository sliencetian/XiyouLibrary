package com.tz.xiyoulibrary.activity.myfoot.view;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class MyFootAdapter extends CommonAdapter<BookBean> {

	private ImageLoader imageLoader;

	public MyFootAdapter(Context context, List<BookBean> mDatas,
			int itemLayoutId, ImageLoader imageLoader) {
		super(context, mDatas, itemLayoutId);
		this.imageLoader = imageLoader;
	}

	@Override
	public void convert(ViewHolder helper, BookBean b) {
		helper.setText(R.id.tv_book_title_item_myfoot, b.getTitle());
		helper.setText(R.id.tv_book_author_item_myborrow, b.getState());
		helper.setText(R.id.tv_book_pub_item_myfoot, b.getDate());
		helper.setText(R.id.tv_book_id_item_myfoot, b.getBarCode());
		ImageView bookImg = helper.getView(R.id.iv_book_img_item_myfoot);
		String imgUrl = b.getImgUrl();
		bookImg.setTag(imgUrl);
		if (TextUtils.equals(imgUrl, "")) {
			bookImg.setBackgroundResource(R.drawable.img_book_no);
		} else if (bookImg.getTag().toString().equals(imgUrl)) {
			ImageListener imageListener = ImageLoader.getImageListener(bookImg,
					R.drawable.img_book, R.drawable.img_book_no);
			imageLoader.get(imgUrl, imageListener, 240, 320);
		}
	}

}
