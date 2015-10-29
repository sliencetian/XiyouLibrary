package com.tz.xiyoulibrary.fragment.my;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.application.Application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EFragment(R.layout.fragment_my)
public class MyFragment extends Fragment {
	@ViewById(R.id.rl_myborrow_fragment_my)
	RelativeLayout mRelativeLayoutBorrow;// 我的借阅
	@ViewById(R.id.rl_collection_fragment_my)
	RelativeLayout mRelativeLayoutCollection;// 我的收藏
	@ViewById(R.id.rl_foot_fragment_my)
	RelativeLayout mRelativeLayoutFoot;// 我的足迹
	@ViewById(R.id.rl_ranklist_fragment_my)
	RelativeLayout mRelativeLayoutRankList;// 排行榜

	@ViewById(R.id.tv_id_fragment_my)
	TextView mTextViewId;// 学号
	@ViewById(R.id.tv_department_fragment_my)
	TextView mTextViewDepartment;// 班级

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@AfterViews
	public void init() {
		mTextViewId.setText(Application.user.getId());
		mTextViewDepartment.setText(Application.user.getDepartment());
	}

	/**
	 * 我的借阅
	 */
	@Click(R.id.rl_myborrow_fragment_my)
	public void pushMyBorrow() {

	}

	/**
	 * 我的收藏
	 */
	@Click(R.id.rl_collection_fragment_my)
	public void pushCollection() {

	}

	/**
	 * 我的足迹
	 */
	@Click(R.id.rl_foot_fragment_my)
	public void pushFoot() {

	}

	/**
	 * 排行榜
	 */
	@Click(R.id.rl_ranklist_fragment_my)
	public void pushRankList() {

	}
}
