package com.tz.xiyoulibrary.utils;

import android.os.Environment;

/**
 * 
 * @author tianzhao 全局常量
 */
public class Constants {

	// 请求网络超时时间
	public static final int TIMEOUT_MS = 10000;

	// 图片缓存路径
	public static final String IMG_CACHE_DIR_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/xiyouLibrary";

	// 服务器地址
	private static final String ROOT_URL = "http://api.xiyoumobile.com/xiyoulibv2/";

	/***************************** 用户 ********************************/
	/**
	 * 登录
	 */
	public static final String GET_USER_LOGIN = ROOT_URL + "user/login";
	/**
	 * 用户信息
	 */
	public static final String GET_USER_INFO = ROOT_URL + "user/info";
	/**
	 * 修改密码
	 */
	public static final String GET_USER_MODIFY_PASSWORD = ROOT_URL + "user/modifyPassword";
	

	/***************************** 图书 ********************************/
	/**
	 * 用户借阅历史
	 */
	public static final String GET_BOOK_HISTORY = ROOT_URL + "user/history";
	/**
	 * 用户当前借阅情况
	 */
	public static final String GET_BOOK_RENT = ROOT_URL + "user/rent";
	/**
	 * 图书续借
	 */
	public static final String GET_BOOK_RENEW = ROOT_URL + "user/renew";
	/**
	 * 图书收藏
	 */
	public static final String GET_BOOK_FAVORITE = ROOT_URL + "user/favorite";
	/**
	 * 图书收藏(有图片)
	 */
	public static final String GET_BOOK_FAVORITE_IMG = ROOT_URL
			+ "user/favoriteWithImg";
	/**
	 * 添加图书收藏
	 */
	public static final String GET_BOOK_ADD_FAVORITE = ROOT_URL + "user/addFav";
	/**
	 * 删除图书收藏
	 */
	public static final String GET_BOOK_DELETE_FAVORITE = ROOT_URL
			+ "user/delFav";
	/**
	 * 图书检索
	 */
	public static final String GET_BOOK_SEARCH = ROOT_URL + "book/search";

	/**
	 * 图书详情by--barcode
	 * eg:http://api.xiyoumobile.com/xiyoulibv2/book/detail/Barcode/03277606
	 */
	public static final String GET_BOOK_DETAIL_BY_BARCODE = ROOT_URL
			+ "book/detail/Barcode/";
	/**
	 * 图书详情by--id
	 * eg:http://api.xiyoumobile.com/xiyoulibv2/book/detail/id/0100000015
	 */
	public static final String GET_BOOK_DETAIL_BY_ID = ROOT_URL
			+ "book/detail/id/";

	/**
	 * 图书排行榜
	 */
	public static final String GET_BOOK_RANK = ROOT_URL + "book/rank";

	/***************************** 新闻、公告 ********************************/
	/**
	 * 公告、新闻列表 参数1：type:news or announce 参数2：page:1、2、3......
	 * 新闻eg:http://api.xiyoumobile.com/xiyoulibv2/news/getList/news/1
	 * 公告eg:http://api.xiyoumobile.com/xiyoulibv2/news/getList/announce/1
	 */
	public static final String GET_NEWS_LIST = ROOT_URL + "news/getList/";
	/**
	 * 公告、新闻详情 参数1：type:news or announce 参数2：format:text or html 参数3：id
	 * 新闻eg:http://api.xiyoumobile.com/xiyoulibv2/news/getDetail/news/text/132
	 * 公告eg
	 * :http://api.xiyoumobile.com/xiyoulibv2/news/getDetail/announce/html/200
	 */
	public static final String GET_NEWS_DETAIL = ROOT_URL + "news/getDetail/";
	

}
