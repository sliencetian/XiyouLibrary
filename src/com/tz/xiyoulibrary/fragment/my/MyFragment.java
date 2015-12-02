package com.tz.xiyoulibrary.fragment.my;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.event.LoginSuccessEvent;
import com.tz.xiyoulibrary.activity.login.view.LoginActivity_;
import com.tz.xiyoulibrary.activity.mybroorw.view.MyBorrowActivity_;
import com.tz.xiyoulibrary.activity.mycollection.activity.view.MyCollectionActivity;
import com.tz.xiyoulibrary.activity.myfoot.view.MyFootActivity_;
import com.tz.xiyoulibrary.activity.rank.view.RankActivity_;
import com.tz.xiyoulibrary.application.Application;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.ypy.eventbus.EventBus;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);

	}

	@AfterViews
	public void init() {
		if (Application.user == null) {
			mTextViewId.setText("未登录");
			mTextViewDepartment.setText("");
		} else {
			mTextViewId.setText(Application.user.getId());
			mTextViewDepartment.setText(Application.user.getDepartment());
		}
	}

	/**
	 * 我的借阅
	 */
	@Click(R.id.rl_myborrow_fragment_my)
	public void pushMyBorrow() {
		if (checkLogin())
			startActivity(new Intent(getActivity(), MyBorrowActivity_.class));
	}

	/**
	 * 我的收藏
	 */
	@Click(R.id.rl_collection_fragment_my)
	public void pushCollection() {
		if (checkLogin())
			startActivity(new Intent(getActivity(), MyCollectionActivity.class));
	}

	/**
	 * 我的足迹
	 */
	@Click(R.id.rl_foot_fragment_my)
	public void pushFoot() {
		if (checkLogin())
			startActivity(new Intent(getActivity(), MyFootActivity_.class));
	}

	/**
	 * 排行榜
	 */
	@Click(R.id.rl_ranklist_fragment_my)
	public void pushRankList() {
		startActivity(new Intent(getActivity(), RankActivity_.class));
	}

	/**
	 * 跳入登录
	 */
	@Click(R.id.tv_id_fragment_my)
	public void pushLoginActivity() {
		if (Application.user == null)
			startActivity(new Intent(getActivity(), LoginActivity_.class));
	}

	public void onEventMainThread(LoginSuccessEvent event) {
		init();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		init();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private boolean checkLogin() {
		if (Application.user == null) {
			CustomToast.showToast(getActivity(), "请先登录", 2000);
			return false;
		}
		return true;
	}
}
