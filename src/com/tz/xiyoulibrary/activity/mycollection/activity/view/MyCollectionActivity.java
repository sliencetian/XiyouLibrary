package com.tz.xiyoulibrary.activity.mycollection.activity.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.extras.viewpager.PullToRefreshViewPager;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.mycollection.activity.presenter.MyCollectionPresenter;
import com.tz.xiyoulibrary.activity.mycollection.fragment.BookPagerAdapter;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.bean.Card;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.IRhythmItemListener;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.RhythmAdapter;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.RhythmLayout;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.utils.AnimatorUtils;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.widget.ViewPagerScroller;
import com.tz.xiyoulibrary.toastview.CustomToast;

public class MyCollectionActivity extends FragmentActivity implements
		IMyCollectionView {

	/**
	 * 钢琴布局
	 */
	private RhythmLayout mRhythmLayout;

	/**
	 * 钢琴布局的适配器
	 */
	private RhythmAdapter mRhythmAdapter;

	/**
	 * 接收PullToRefreshViewPager中的ViewPager控件
	 */
	private ViewPager mViewPager;

	/**
	 * 可以侧拉刷新的ViewPager，其实是一个LinearLayout控件
	 */
	private PullToRefreshViewPager mPullToRefreshViewPager;

	/**
	 * ViewPager的适配器
	 */
	private BookPagerAdapter bookPagerAdapter;

	/**
	 * 最外层的View，为了设置背景颜色而使用
	 */
	private View mMainView;

	private List<Card> mCardList;
	private List<Map<String, String>> mFavoriteList;

	private RelativeLayout mRelativeLayoutBack;
	private TextView mTextViewTitle;

	private MyCollectionPresenter mPresenter;

	private RequestQueue queue;

	/**
	 * 记录上一个选项卡的颜色值
	 */
	private int mPreColor;

	private IRhythmItemListener iRhythmItemListener = new IRhythmItemListener() {
		@Override
		public void onSelected(final int position) {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					mViewPager.setCurrentItem(position);
				}
			}, 100L);
		}
	};

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			int currColor = mCardList.get(position).getBackgroundColor();
			AnimatorUtils.showBackgroundColorAnimation(mMainView, mPreColor,
					currColor, 400);
			mPreColor = currColor;

			mMainView.setBackgroundColor(mCardList.get(position)
					.getBackgroundColor());
			mRhythmLayout.showRhythmAtPosition(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		setContentView(R.layout.activity_myfoot);
		mPresenter = new MyCollectionPresenter(this);
		queue = Volley.newRequestQueue(MyCollectionActivity.this);
		// 初始化ActionBar
		initActionBar();
		// 获取收藏书籍
		getFavoriteData();
		// 初始化数据
	//	init();
	}

	/**
	 * 获取收藏书籍
	 */
	private void getFavoriteData() {
		mPresenter.getFavoriteData(queue);
	}

	@Override
	public void showFavoriteData(List<Map<String, String>> favoriteData) {
		mFavoriteList = favoriteData;
		// 初始化颜色
		mCardList = new ArrayList<Card>();
		for (int i = 0; i < mFavoriteList.size(); i++) {
			Card card = new Card();
			// 随机生成颜色值
			card.setBackgroundColor((int) -(Math.random() * (16777216 - 1) + 1));
			// card.setBackgroundColor(getResources()
			// .getColor(R.color.theme_color));
			mCardList.add(card);
		}
		init();
	}

	private void init() {
		// 实例化控件
		mMainView = findViewById(R.id.main_view);
		mRhythmLayout = (RhythmLayout) findViewById(R.id.box_rhythm);
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pager);
		// 获取PullToRefreshViewPager中的ViewPager控件
		mViewPager = mPullToRefreshViewPager.getRefreshableView();
		// 设置钢琴布局的高度 高度为钢琴布局item的宽度+10dp
		int height = (int) mRhythmLayout.getRhythmItemWidth()
				+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						10.0F, getResources().getDisplayMetrics());
		mRhythmLayout.getLayoutParams().height = height;

		((RelativeLayout.LayoutParams) this.mPullToRefreshViewPager
				.getLayoutParams()).bottomMargin = height;

		bookPagerAdapter = new BookPagerAdapter(getSupportFragmentManager(),
				mFavoriteList);
		mViewPager.setAdapter(bookPagerAdapter);

		// 设置钢琴布局的适配器
		mRhythmAdapter = new RhythmAdapter(this, mCardList);
		mRhythmLayout.setAdapter(mRhythmAdapter);

		// 设置ViewPager的滚动速度
		setViewPagerScrollSpeed(this.mViewPager, 400);

		// 设置控件的监听
		mRhythmLayout.setRhythmListener(iRhythmItemListener);
		mViewPager.setOnPageChangeListener(onPageChangeListener);
		// 设置ScrollView滚动动画延迟执行的时间
		mRhythmLayout.setScrollRhythmStartDelayTime(400);

		// 初始化时将第一个键帽弹出,并设置背景颜色
		mRhythmLayout.showRhythmAtPosition(0);
		mPreColor = mCardList.get(0).getBackgroundColor();
		mMainView.setBackgroundColor(mPreColor);
	}

	/**
	 * 设置ViewPager的滚动速度，即每个选项卡的切换速度
	 * 
	 * @param viewPager
	 *            ViewPager控件
	 * @param speed
	 *            滚动速度，毫秒为单位
	 */
	private void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			ViewPagerScroller viewPagerScroller = new ViewPagerScroller(
					viewPager.getContext(), new OvershootInterpolator(0.6F));
			field.set(viewPager, viewPagerScroller);
			viewPagerScroller.setDuration(speed);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化ActionBar
	 */
	private void initActionBar() {
		mRelativeLayoutBack = (RelativeLayout) findViewById(R.id.rl_back_actionbar);
		mTextViewTitle = (TextView) findViewById(R.id.tv_title_actionbar);
		mTextViewTitle.setText("我的收藏");
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mRelativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public void showGetDataFaluire() {

	}

	@Override
	public void showGetDataNoData() {

	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}
}
