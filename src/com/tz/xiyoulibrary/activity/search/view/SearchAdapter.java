package com.tz.xiyoulibrary.activity.search.view;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class SearchAdapter extends CommonAdapter<Map<String,String>>{

	public SearchAdapter(Context context, List<Map<String, String>> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(ViewHolder helper, Map<String, String> map) {
		// TODO Auto-generated method stub
		helper.setText(R.id.book_title,map.get("Title").toString());
		helper.setText(R.id.book_author, map.get("Author").toString());
		helper.setText(R.id.book_id, map.get("ID").toString());
	}
	

}
