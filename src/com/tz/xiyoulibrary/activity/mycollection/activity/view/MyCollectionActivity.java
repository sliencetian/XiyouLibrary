package com.tz.xiyoulibrary.activity.mycollection.activity.view;

import java.lang.reflect.Field;
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
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.IRhythmItemListener;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.RhythmAdapter;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.control.RhythmLayout;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.utils.AnimatorUtils;
import com.tz.xiyoulibrary.activity.mycollection.viewutils.widget.ViewPagerScroller;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;

public class MyCollectionActivity extends FragmentActivity implements
		IMyCollectionView {

	/**
	 * ���ٲ���
	 */
	private RhythmLayout mRhythmLayout;

	/**
	 * ���ٲ��ֵ�������
	 */
	private RhythmAdapter mRhythmAdapter;

	/**
	 * ����PullToRefreshViewPager�е�ViewPager�ؼ�
	 */
	private ViewPager mViewPager;

	/**
	 * ���Բ���ˢ�µ�ViewPager����ʵ��һ��LinearLayout�ؼ�
	 */
	private PullToRefreshViewPager mPullToRefreshViewPager;

	/**
	 * ViewPager��������
	 */
	private BookPagerAdapter bookPagerAdapter;

	/**
	 * ������View��Ϊ�����ñ�����ɫ��ʹ��
	 */
	private View mMainView;

	private List<Map<String, String>> mFavoriteList;

	private RelativeLayout mRelativeLayoutBack;
	private TextView mTextViewTitle;

	private MyCollectionPresenter mPresenter;

	private RequestQueue queue;

	private TitanicTextView mTitanicTextView;

	private RelativeLayout mRelativeLayoutLoading;
	private Titanic mTitanic;
	private RelativeLayout mRelativeLayoutLoadFaluire;
	private RelativeLayout mRelativeLayoutLoadNoData;
	private TextView mTextViewTip;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		setContentView(R.layout.activity_mycollection);
		mPresenter = new MyCollectionPresenter(this);
		queue = Volley.newRequestQueue(MyCollectionActivity.this);
		mTitanic = new Titanic();
		// ��ʼ���ؼ�
		findViews();
		// set fancy typeface
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		// ��ʼ��ActionBar
		initActionBar();
		// ��Ӽ����¼�
		setListener();
		// ��ȡ�ղ��鼮
		getFavoriteData();
	}

	private void setListener() {
		mRelativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mRelativeLayoutLoadFaluire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getFavoriteData();
			}
		});
	}

	private void findViews() {
		mTitanicTextView = (TitanicTextView) findViewById(R.id.loading_text);
		mRelativeLayoutBack = (RelativeLayout) findViewById(R.id.rl_back_actionbar);
		mTextViewTitle = (TextView) findViewById(R.id.tv_title_actionbar);
		mTextViewTip = (TextView) findViewById(R.id.tv_load_no_data_tip);

		mRelativeLayoutLoading = (RelativeLayout) findViewById(R.id.rl_loading);
		mRelativeLayoutLoadFaluire = (RelativeLayout) findViewById(R.id.rl_load_faluire);
		mRelativeLayoutLoadNoData = (RelativeLayout) findViewById(R.id.rl_load_no_data);
	}

	/**
	 * ��ȡ�ղ��鼮
	 */
	private void getFavoriteData() {
		mPresenter.getFavoriteData(queue);
	}

	@Override
	public void showFavoriteData(List<Map<String, String>> favoriteData) {
		// �رռ��ض���
		mTitanic.cancel();
		// ���ؼ�����ͼ
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);

		mFavoriteList = favoriteData;
		for (Map<String, String> map : mFavoriteList) {
			map.put("Color", getResources().getColor(R.color.theme_color) + "");
		}
		// ��ʼ����ɫ
		/*
		 * mCardList = new ArrayList<Card>(); for (int i = 0; i <
		 * mFavoriteList.size(); i++) { Card card = new Card(); // ���������ɫֵ //
		 * card.setBackgroundColor((int) -(Math.random() * (16777216 - 1) + //
		 * 1)); card.setBackgroundColor(getResources()
		 * .getColor(R.color.theme_color)); mCardList.add(card); }
		 */
		init();
	}

	@Override
	public void showGetDataFaluire() {
		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
	}

	@Override
	public void showGetDataNoData() {
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mTextViewTip.setText("�ף�����û���κ��ղ�Ŷ~");
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
	}

	@Override
	public void showLoadView() {
		mTitanic.start(mTitanicTextView);
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	private void init() {
		// ʵ�����ؼ�
		mMainView = findViewById(R.id.main_view);
		mRhythmLayout = (RhythmLayout) findViewById(R.id.box_rhythm);
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pager);
		// ��ȡPullToRefreshViewPager�е�ViewPager�ؼ�
		mViewPager = mPullToRefreshViewPager.getRefreshableView();
		// ���ø��ٲ��ֵĸ߶� �߶�Ϊ���ٲ���item�Ŀ��+10dp
		int height = (int) mRhythmLayout.getRhythmItemWidth()
				+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						10.0F, getResources().getDisplayMetrics());
		mRhythmLayout.getLayoutParams().height = height;

		((RelativeLayout.LayoutParams) this.mPullToRefreshViewPager
				.getLayoutParams()).bottomMargin = height;

		bookPagerAdapter = new BookPagerAdapter(getSupportFragmentManager(),
				mFavoriteList);
		mViewPager.setAdapter(bookPagerAdapter);

		// ���ø��ٲ��ֵ�������
		mRhythmAdapter = new RhythmAdapter(this, mFavoriteList);
		mRhythmLayout.setAdapter(mRhythmAdapter);

		// ����ViewPager�Ĺ����ٶ�
		setViewPagerScrollSpeed(this.mViewPager, 400);

		// ���ÿؼ��ļ���
		mRhythmLayout.setRhythmListener(iRhythmItemListener);
		mViewPager.setOnPageChangeListener(onPageChangeListener);
		// ����ScrollView���������ӳ�ִ�е�ʱ��
		mRhythmLayout.setScrollRhythmStartDelayTime(400);

		// ��ʼ��ʱ����һ����ñ����,�����ñ�����ɫ
		mRhythmLayout.showRhythmAtPosition(0);
		mPreColor = Integer.parseInt(mFavoriteList.get(0).get("Color"));
		mMainView.setBackgroundColor(mPreColor);

	}

	/**
	 * ����ViewPager�Ĺ����ٶȣ���ÿ��ѡ����л��ٶ�
	 * 
	 * @param viewPager
	 *            ViewPager�ؼ�
	 * @param speed
	 *            �����ٶȣ�����Ϊ��λ
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
	 * ��ʼ��ActionBar
	 */
	private void initActionBar() {
		mTextViewTitle.setText("�ҵ��ղ�");
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
	}

	/**
	 * ��¼��һ��ѡ�����ɫֵ
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
			int currColor = Integer.parseInt(mFavoriteList.get(position).get(
					"Color"));
			AnimatorUtils.showBackgroundColorAnimation(mMainView, mPreColor,
					currColor, 400);
			mPreColor = currColor;

			mMainView.setBackgroundColor(Integer.parseInt(mFavoriteList.get(
					position).get("Color")));
			mRhythmLayout.showRhythmAtPosition(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		queue.cancelAll(this);
	}

	public RequestQueue getQueue() {
		return queue;
	}

	public void upDate(String position) {
		CustomToast.showToast(MyCollectionActivity.this, "ɾ���ɹ�", 2000);
		mFavoriteList.remove(Integer.parseInt(position) - 1);
		if (mFavoriteList.size() == 0) {
			showGetDataNoData();
		} else {
			init();
			mRhythmLayout.showRhythmAtPosition(1);
			mRhythmLayout.showRhythmAtPosition(0);
		}
	}
}
