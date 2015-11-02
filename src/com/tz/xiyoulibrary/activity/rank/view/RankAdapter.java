package com.tz.xiyoulibrary.activity.rank.view;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class RankAdapter extends CommonAdapter<Map<String, String>> {

	public RankAdapter(Context context, List<Map<String, String>> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Map<String, String> map) {
		helper.setText(R.id.tv_title_item_activity_rank, "¡¶" + map.get("Title")
				+ "¡·");
		helper.setText(R.id.tv_id_item_activity_rank, map.get("ID"));
		helper.setText(R.id.tv_borwnumb_item_activity_rank, map.get("BorNum"));
	}

}
