package com.tz.xiyoulibrary.activity.bookdetial.view;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.View;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.utils.adapter.CommonAdapter;
import com.tz.xiyoulibrary.utils.adapter.ViewHolder;

public class CirculationAdapter extends CommonAdapter<Map<String, String>> {

	public CirculationAdapter(Context context,
			List<Map<String, String>> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Map<String, String> map) {
//		helper.setText(R.id.tv_barcode_item_circulation, map.get("Barcode"));
		helper.setText(R.id.tv_barcode_item_circulation, map.get("Sort"));
		helper.setText(R.id.tv_state_item_circulation, map.get("Status"));
		helper.setText(R.id.tv_department_item_circulation,
				map.get("Department"));
		if (map.get("Date").equals("null")) {
			helper.getView(R.id.ll_date_item_circulation).setVisibility(View.GONE);
		} else {
			helper.setText(R.id.tv_date_item_circulation, map.get("Date"));
		}
	}

}
