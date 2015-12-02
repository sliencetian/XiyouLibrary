package com.tz.xiyoulibrary.fragment.home.view;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class HomeAdapter extends CommonAdapter<Map<String,Object>>{

	public HomeAdapter(Context context, List<Map<String, Object>> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(ViewHolder helper, Map<String, Object> map) {
		// TODO Auto-generated method stub
		helper.setText(R.id.home_list_title,map.get("Title").toString());
		
		
	}
	

}
