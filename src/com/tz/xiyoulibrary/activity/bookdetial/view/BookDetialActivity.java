package com.tz.xiyoulibrary.activity.bookdetial.view;

import java.util.List;
import java.util.Map;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.tencent.tauth.Tencent;
import com.tz.xiyoulibrary.R;
import com.tz.xiyoulibrary.activity.baseactivity.BaseActivity;
import com.tz.xiyoulibrary.activity.bookdetial.presenter.BookDetialPresenter;
import com.tz.xiyoulibrary.pulltozoomview.PullToZoomListView;
import com.tz.xiyoulibrary.titanicview.Titanic;
import com.tz.xiyoulibrary.titanicview.TitanicTextView;
import com.tz.xiyoulibrary.titanicview.Typefaces;
import com.tz.xiyoulibrary.toastview.CustomToast;
import com.tz.xiyoulibrary.utils.BitmapCache;
import com.tz.xiyoulibrary.utils.Constants;

@EActivity(R.layout.activity_book_detial)
public class BookDetialActivity extends BaseActivity implements IBookDetialView {
	@ViewById(R.id.rl_back_actionbar)
	RelativeLayout mRelativeLayoutBack;
	@ViewById(R.id.tv_title_actionbar)
	TextView mTextViewTitle;
	Titanic mTitanic;
	@ViewById(R.id.loading_text)
	TitanicTextView mTitanicTextView;
	@ViewById(R.id.rl_loading)
	RelativeLayout mRelativeLayoutLoading;
	@ViewById(R.id.rl_load_faluire)
	RelativeLayout mRelativeLayoutLoadFaluire;
	@ViewById(R.id.rl_load_no_data)
	RelativeLayout mRelativeLayoutLoadNoData;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	@ViewById(R.id.ptzv_book_detial)
	PullToZoomListView mPullToZoomListView;
	private Map<String, Object> bookDetial;

	private String url;
	private BookDetialPresenter mPresenter;
	private ProgressDialog progressDialog;

	Tencent mTencent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		queue = Volley.newRequestQueue(BookDetialActivity.this);
		url = getIntent().getStringExtra("url");
		mPresenter = new BookDetialPresenter(this);
		imageLoader = new ImageLoader(queue, new BitmapCache());

		progressDialog = new ProgressDialog(BookDetialActivity.this);
		progressDialog.setTitle("提示");
		progressDialog.setMessage("收藏中,请稍候...");
		progressDialog.setCancelable(false);

		ShareSDK.initSDK(this);
	}

	@AfterViews
	public void initWidgetAfter() {
		mTitanicTextView
				.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
		mTitanic = new Titanic();
		mRelativeLayoutBack.setVisibility(View.VISIBLE);
		mTextViewTitle.setText("图书详情");
		// 获取图书详情
		getBookDetial();
	}

	/**
	 * 获取图书详情
	 */
	private void getBookDetial() {
		mPresenter.getBookDetial(queue, url);
	}

	@Click(R.id.rl_back_actionbar)
	public void back() {
		finish();
	}

	@Click(R.id.rl_load_no_data)
	public void resetGetData() {
		getBookDetial();
	}

	@Override
	public void showLoadingView() {
		mRelativeLayoutLoading.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mTitanic.start(mTitanicTextView);
	}

	@Override
	public void showBookDetialView(Map<String, Object> bookDetial) {
		// 关闭加载动画
		mTitanic.cancel();
		// 隐藏加载视图
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		this.bookDetial = bookDetial;

		LayoutInflater inflater = LayoutInflater.from(BookDetialActivity.this);
		RelativeLayout foot = (RelativeLayout) inflater.inflate(
				R.layout.activity_book_detial_foot, null);
		// 初始化基本资料
		addOneView(foot);
		// 添加流通情况
		addTwoView(foot);
		// 添加图书摘要
		addThreeView(foot);
		// 添加相关推荐
		addFourView(foot);

		mPullToZoomListView.addFooterView(foot);
		String[] adapterData = new String[] {};
		mPullToZoomListView.setAdapter(new ArrayAdapter<String>(
				BookDetialActivity.this, android.R.layout.simple_list_item_1,
				adapterData));
		String imgUrl = bookDetial.get("medium").toString();
		if (TextUtils.equals(imgUrl, "")) {
			mPullToZoomListView.getHeaderView().setImageResource(
					R.drawable.img_book);
		} else {
			ImageListener listener = ImageLoader.getImageListener(
					mPullToZoomListView.getHeaderView(),
					R.drawable.img_book_no, R.drawable.img_book_no);
			imageLoader.get(imgUrl, listener);
		}
		mPullToZoomListView.getHeaderView().setScaleType(
				ImageView.ScaleType.CENTER_CROP);
	}

	/**
	 * 添加相关推荐
	 */
	private void addFourView(RelativeLayout root) {
		ListView lv = (ListView) root
				.findViewById(R.id.lv_refer_activity_book_detial);// 流通情况
		@SuppressWarnings("unchecked")
		ReferAdapter adapter = new ReferAdapter(BookDetialActivity.this,
				(List<Map<String, String>>) bookDetial.get("ReferBooks"),
				R.layout.item_refer_adapter);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view
						.findViewById(R.id.tv_id_item_refer);
				Intent intent = new Intent(BookDetialActivity.this,
						BookDetialActivity_.class);
				intent.putExtra("url", Constants.GET_BOOK_DETAIL_BY_ID
						+ tv.getText().toString());
				startActivity(intent);
			}
		});
	}

	/**
	 * 添加图书摘要
	 */
	private void addThreeView(RelativeLayout root) {
		TextView tv_no_data = (TextView) root
				.findViewById(R.id.tv_no_book_detial);
		LinearLayout ll = (LinearLayout) root.findViewById(R.id.ll_book_detial);
		if (!bookDetial.get("Author_Info").equals("")) {
			tv_no_data.setVisibility(View.GONE);
			ll.setVisibility(View.VISIBLE);
			TextView tv = (TextView) root
					.findViewById(R.id.tv_book_author_info_activity_book_detial);
			tv.setText(bookDetial.get("Author_Info") + "");
		} else {
			root.findViewById(R.id.ll_book_author_info_activity_book_detial)
					.setVisibility(View.GONE);
		}
		if (!bookDetial.get("Summary").equals("")) {
			tv_no_data.setVisibility(View.GONE);
			ll.setVisibility(View.VISIBLE);
			TextView tv = (TextView) root
					.findViewById(R.id.tv_book_Summary_activity_book_detial);
			tv.setText(bookDetial.get("Summary") + "");
		} else {
			root.findViewById(R.id.ll_book_Summary_activity_book_detial)
					.setVisibility(View.GONE);
		}
		if (bookDetial.containsKey("Author_Info")
				&& bookDetial.get("Summary").equals("")) {
			ll.setVisibility(View.GONE);
			tv_no_data.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 流通情况
	 */
	private void addTwoView(RelativeLayout root) {
		TextView tv;
		tv = (TextView) root
				.findViewById(R.id.tv_book_avaliable_activity_book_detial_two);// 可借数量
		tv.setText(bookDetial.get("Avaliable") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_total_activity_book_detial_two);// 藏书数量
		tv.setText(bookDetial.get("Total") + " 本");
		ListView lv = (ListView) root
				.findViewById(R.id.lv_circlu_activity_book_detial);// 流通情况
		@SuppressWarnings("unchecked")
		CirculationAdapter adapter = new CirculationAdapter(
				BookDetialActivity.this,
				(List<Map<String, String>>) bookDetial.get("CirculationInfo"),
				R.layout.item_circulation_adapter);
		lv.setAdapter(adapter);
	}

	/**
	 * 基本资料
	 */
	private void addOneView(RelativeLayout root) {
		Button collection = (Button) root
				.findViewById(R.id.bt_collection_activity_book_detial);
		Button share = (Button) root
				.findViewById(R.id.bt_share_activity_book_detial);
		collection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPresenter.collection(queue, bookDetial.get("ID") + "");
			}
		});
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare(BookDetialActivity.this, null, true);
			}
		});
		TextView tv;
		tv = (TextView) root.findViewById(R.id.tv_title_activity_book_detial);// 标题
		tv.setText(bookDetial.get("Title") + "");
		tv = (TextView) root.findViewById(R.id.tv_book_id_activity_book_detial);// 索书号
		tv.setText(bookDetial.get("ID") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_author_activity_book_detial);// 作者
		tv.setText(bookDetial.get("Author") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_page_activity_book_detial);// 页数
		try {
			tv.setText(bookDetial.get("Pages") + "");
		} catch (Exception e) {
			tv.setText(bookDetial.get("Form") + "");
		}

		tv = (TextView) root
				.findViewById(R.id.tv_book_avaliable_activity_book_detial);// 可借数量
		tv.setText(bookDetial.get("Avaliable") + " 本");
		tv = (TextView) root
				.findViewById(R.id.tv_book_renttimes_activity_book_detial);// 借阅次数
		tv.setText(bookDetial.get("RentTimes") + " 次");
		tv = (TextView) root
				.findViewById(R.id.tv_book_favtimes_activity_book_detial);// 收藏次数
		tv.setText(bookDetial.get("FavTimes") + " 次");
		tv = (TextView) root
				.findViewById(R.id.tv_book_browsetimes_activity_book_detial);// 浏览次数
		tv.setText(bookDetial.get("BrowseTimes") + " 次");
		tv = (TextView) root
				.findViewById(R.id.tv_book_total_activity_book_detial);// 藏书数量
		tv.setText(bookDetial.get("Total") + " 本");

		tv = (TextView) root
				.findViewById(R.id.tv_book_subject_activity_book_detial);// 主题分类
		tv.setText(bookDetial.get("Subject") + "");
		tv = (TextView) root
				.findViewById(R.id.tv_book_pub_activity_book_detial);// 出版社
		tv.setText(bookDetial.get("Pub") + "");
	}

	@Override
	public void showLoadFaluireView() {
		mTitanic.cancel();
		mRelativeLayoutLoadFaluire.setVisibility(View.VISIBLE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
		mRelativeLayoutLoadNoData.setVisibility(View.GONE);
	}

	@Override
	public void showNoDataView() {
		mTitanic.cancel();
		mRelativeLayoutLoadNoData.setVisibility(View.VISIBLE);
		mRelativeLayoutLoadFaluire.setVisibility(View.GONE);
		mRelativeLayoutLoading.setVisibility(View.GONE);
	}

	@Override
	public void showMsg(String msg) {
		CustomToast.showToast(this, msg, 2000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		queue.cancelAll(this);
	}

	@Override
	public void hidenDialog() {
		progressDialog.dismiss();
	}

	@Override
	public void showDialog() {
		progressDialog.show();
	}

	/**
	 * 演示调用ShareSDK执行分享
	 * 
	 * @param context
	 * @param platformToShare
	 *            指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
	 * @param showContentEdit
	 *            是否显示编辑页
	 */
	private void showShare(Context context, String platformToShare,
			boolean showContentEdit) {
		OnekeyShare oks = new OnekeyShare();
		oks.setSilent(!showContentEdit);
		if (platformToShare != null) {
			oks.setPlatform(platformToShare);
		}
		// ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC 第二个是SKYBLUE
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		oks.disableSSOWhenAuthorize();
		String imgUrl = bookDetial.get("medium").toString();
		oks.setTitle("西邮图书馆---"+"《" + bookDetial.get("Title") + "》");
		oks.setTitleUrl("http://lib.xiyoumobile.com/moreInfo.html?id="
				+ bookDetial.get("ID").toString());
		oks.setText(bookDetial.get("Summary")+"");
		// oks.setImagePath("/sdcard/test-pic.jpg"); //分享sdcard目录下的图片
		if (!TextUtils.equals(imgUrl, "")) {
			oks.setImageUrl(imgUrl);
		} else {
			// oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		}
		oks.setComment("分享"); // 我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		oks.setSite("西邮图书馆"); // QZone分享完之后返回应用时提示框上显示的名称
		oks.setSiteUrl("http://lib.xiyoumobile.com/moreInfo.html?id="
				+ bookDetial.get("ID").toString());// QZone分享参数
		oks.setSiteUrl(imgUrl);// QZone分享参数
		oks.setVenueName("西邮图书馆");
		oks.setVenueDescription("form xiyouLibrary");
		oks.setShareFromQQAuthSupport(false);
		// 将快捷分享的操作结果将通过OneKeyShareCallback回调
		// oks.setCallback(new OneKeyShareCallback());
		// 去自定义不同平台的字段内容
		// oks.setShareContentCustomizeCallback(new
		// ShareContentCustomizeDemo());
		// 在九宫格设置自定义的图标
		/*
		 * Bitmap enableLogo = BitmapFactory.decodeResource(
		 * context.getResources(), R.drawable.ssdk_oks_logo_qzone); Bitmap
		 * disableLogo = BitmapFactory.decodeResource( context.getResources(),
		 * R.drawable.ssdk_oks_logo_qzone); String label = "QQ空间";
		 * OnClickListener listener = new OnClickListener() { public void
		 * onClick(View v) { shareToQQzone(); } };
		 * oks.setCustomerLogo(enableLogo, disableLogo, label, listener);
		 */
		// 隐藏支付宝
		// 为EditPage设置一个背景的View
		// oks.setEditPageBackground(getPage());
		// 隐藏九宫格中的新浪微博
		// oks.addHiddenPlatform(SinaWeibo.NAME);

		// String[] AVATARS = {
		// "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
		// "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
		// "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
		// "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
		// "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
		// "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
		// };
		// oks.setImageArray(AVATARS); //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

		// 启动分享
		oks.show(context);
	}
}
