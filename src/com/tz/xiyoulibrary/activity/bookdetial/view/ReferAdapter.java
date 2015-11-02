package com.tz.xiyoulibrary.activity.bookdetial.view;

import java.util.List;
import java.util.Map;
import android.content.Context;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class ReferAdapter extends CommonAdapter<Map<String, String>> {

	public ReferAdapter(Context context, List<Map<String, String>> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Map<String, String> map) {
		helper.setText(R.id.tv_title_item_refer, "¡¶"+map.get("Title")+"¡·");
		helper.setText(R.id.tv_author_item_refer, map.get("Author"));
		helper.setText(R.id.tv_id_item_refer, map.get("ID"));
	}

}
