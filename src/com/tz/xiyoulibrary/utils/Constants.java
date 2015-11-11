package com.tz.xiyoulibrary.utils;

import android.os.Environment;

/**
 * 
 * @author tianzhao ȫ�ֳ���
 */
public class Constants {

	// �������糬ʱʱ��
	public static final int TIMEOUT_MS = 10000;

	// ͼƬ����·��
	public static final String IMG_CACHE_DIR_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/xiyouLibrary";

	// ��������ַ
	private static final String ROOT_URL = "http://api.xiyoumobile.com/xiyoulibv2/";

	/***************************** �û� ********************************/
	/**
	 * ��¼
	 */
	public static final String GET_USER_LOGIN = ROOT_URL + "user/login";
	/**
	 * �û���Ϣ
	 */
	public static final String GET_USER_INFO = ROOT_URL + "user/info";
	/**
	 * �޸�����
	 */
	public static final String GET_USER_MODIFY_PASSWORD = ROOT_URL + "user/modifyPassword";
	

	/***************************** ͼ�� ********************************/
	/**
	 * �û�������ʷ
	 */
	public static final String GET_BOOK_HISTORY = ROOT_URL + "user/history";
	/**
	 * �û���ǰ�������
	 */
	public static final String GET_BOOK_RENT = ROOT_URL + "user/rent";
	/**
	 * ͼ������
	 */
	public static final String GET_BOOK_RENEW = ROOT_URL + "user/renew";
	/**
	 * ͼ���ղ�
	 */
	public static final String GET_BOOK_FAVORITE = ROOT_URL + "user/favorite";
	/**
	 * ͼ���ղ�(��ͼƬ)
	 */
	public static final String GET_BOOK_FAVORITE_IMG = ROOT_URL
			+ "user/favoriteWithImg";
	/**
	 * ���ͼ���ղ�
	 */
	public static final String GET_BOOK_ADD_FAVORITE = ROOT_URL + "user/addFav";
	/**
	 * ɾ��ͼ���ղ�
	 */
	public static final String GET_BOOK_DELETE_FAVORITE = ROOT_URL
			+ "user/delFav";
	/**
	 * ͼ�����
	 */
	public static final String GET_BOOK_SEARCH = ROOT_URL + "book/search";

	/**
	 * ͼ������by--barcode
	 * eg:http://api.xiyoumobile.com/xiyoulibv2/book/detail/Barcode/03277606
	 */
	public static final String GET_BOOK_DETAIL_BY_BARCODE = ROOT_URL
			+ "book/detail/Barcode/";
	/**
	 * ͼ������by--id
	 * eg:http://api.xiyoumobile.com/xiyoulibv2/book/detail/id/0100000015
	 */
	public static final String GET_BOOK_DETAIL_BY_ID = ROOT_URL
			+ "book/detail/id/";

	/**
	 * ͼ�����а�
	 */
	public static final String GET_BOOK_RANK = ROOT_URL + "book/rank";

	/***************************** ���š����� ********************************/
	/**
	 * ���桢�����б� ����1��type:news or announce ����2��page:1��2��3......
	 * ����eg:http://api.xiyoumobile.com/xiyoulibv2/news/getList/news/1
	 * ����eg:http://api.xiyoumobile.com/xiyoulibv2/news/getList/announce/1
	 */
	public static final String GET_NEWS_LIST = ROOT_URL + "news/getList/";
	/**
	 * ���桢�������� ����1��type:news or announce ����2��format:text or html ����3��id
	 * ����eg:http://api.xiyoumobile.com/xiyoulibv2/news/getDetail/news/text/132
	 * ����eg
	 * :http://api.xiyoumobile.com/xiyoulibv2/news/getDetail/announce/html/200
	 */
	public static final String GET_NEWS_DETAIL = ROOT_URL + "news/getDetail/";
	

}
