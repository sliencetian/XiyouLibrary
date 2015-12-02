package com.tz.xiyoulibrary.activity.mybroorw.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.bean.BookBean;
import com.tz.xiyoulibrary.dialog.progressbar.MyProgressBar;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.Constants;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

@SuppressLint("HandlerLeak")
public class MyBorrowAdapter extends CommonAdapter<BookBean> {
	private ImageLoader imageLoader;
	private int position;
	private MyProgressBar progressBar;
	private RequestQueue queue;

	public MyBorrowAdapter(Context context, List<BookBean> mDatas,
			int itemLayoutId, ImageLoader imageLoader, RequestQueue queue) {
		super(context, mDatas, itemLayoutId);
		this.imageLoader = imageLoader;
		this.queue = queue;
	}

	@Override
	public void convert(ViewHolder helper, final BookBean b) {
		helper.setText(R.id.tv_book_title_item_myborrow, b.getTitle());
		helper.setText(R.id.tv_book_department_item_myborrow,
				"分馆:" + b.getDepartment());
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
					position = mDatas.indexOf(b);
					progressBar = new MyProgressBar(mContext);
					progressBar.show();
					// 续借图书
					renewBook();
				}
			});
		} else {
			bt.setVisibility(View.GONE);
			tv.setVisibility(View.VISIBLE);
			tv.setText(b.getState());
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progressBar.isShowing())
				progressBar.dismiss();
			switch (msg.what) {
			case 0x001:// 失败
				CustomToast.showToast(mContext, "续借失败", 2000);
				break;
			case 0x002:// 成功
				CustomToast.showToast(mContext, "续借成功", 2000);
				mDatas.get(position).setState((String) msg.obj);
				notifyDataSetChanged();
				break;
			}
		};
	};

	/**
	 * 续借图书
	 */
	private void renewBook() {
		StringRequest renewRequest = new StringRequest(Method.POST,
				Constants.GET_BOOK_RENEW, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						try {
							JSONObject o = new JSONObject(response);
							if (o.getBoolean("Result")) {
								Message msg = Message.obtain();
								msg.what = 0x002;
								msg.obj = o.getString("Detail");
								handler.sendMessage(msg);
							} else {
								handler.sendEmptyMessage(0x001);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(0x001);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						handler.sendEmptyMessage(0x001);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				BookBean currBook  = mDatas.get(position);
				Map<String, String> map = new HashMap<String, String>();
				map.put("session", Application.SESSION);
				map.put("barcode", currBook.getBarCode());
				map.put("department_id", currBook.getDepartment_id());
				map.put("library_id", currBook.getLibrary_id());
				return map;
			}
		};
		renewRequest.setRetryPolicy(new DefaultRetryPolicy(
				Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(renewRequest);
	}

}
