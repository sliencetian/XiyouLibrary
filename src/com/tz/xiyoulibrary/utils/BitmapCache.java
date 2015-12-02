package com.tz.xiyoulibrary.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int maxSize = 10 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	/**
	 * �ȴӻ������ң����򷵻�ͼƬ��û����������ȡ
	 */
	@Override
	public Bitmap getBitmap(String url) {
		/**
		 * �ȴӻ������ң����򷵻أ�û���򷵻�null
		 */
		Bitmap bitmap = mCache.get(url);

		if (bitmap == null) {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			/**
			 * ���Ϊnull���򻺴���û�У��ӱ���SD����������
			 */
			File cacheDir = new File(Constants.IMG_CACHE_DIR_PATH);
			File[] cacheFiles = cacheDir.listFiles();
			if (cacheFiles != null) {
				int i = 0;
				for (; i < cacheFiles.length; i++) {
					if (TextUtils.equals(fileName, cacheFiles[i].getName()))
						break;
				}
				/**
				 * ���ҵ��򷵻�bitmap���򷵻�null
				 */
				if (i < cacheFiles.length) {
					bitmap = getSDBitmap(Constants.IMG_CACHE_DIR_PATH + "/"
							+ fileName);
					/**
					 * ����SD���л�ȡ��bitmap���뻺����
					 */
					mCache.put(url, bitmap);
				}
			}
		}
		/**
		 * �Ƿ�����ͼƬ
		 */
		if (Constants.network_type != ConnectivityManager.TYPE_WIFI
				&& !Constants.isLoadImg) {
			LogUtils.d("ImageLoad", "������ͼƬ");
			return BitmapFactory.decodeStream(getClass().getResourceAsStream("/assets/img_book_no.png"));
		}
		return bitmap;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		/**
		 * ���뻺����
		 */
		mCache.put(url, bitmap);
		/**
		 * �浽����SD��
		 */
		putSDBitmap(url.substring(url.lastIndexOf("/") + 1), bitmap);
	}

	/**
	 * �ӱ���SD���л�ȡͼƬ
	 * 
	 * @param imgPath
	 *            ͼƬ·��
	 * @return ͼƬ��Bitmap
	 */
	private Bitmap getSDBitmap(String imgPath) {
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		/**
		 * ������ʱ�����С
		 */
		options.inTempStorage = new byte[1024 * 1024];
		/**
		 * ͨ������Options.inPreferredConfigֵ�������ڴ����ģ� Ĭ��ΪARGB_8888: ÿ������4�ֽ�. ��32λ��
		 * Alpha_8: ֻ����͸���ȣ���8λ��1�ֽڡ� ARGB_4444: ��16λ��2�ֽڡ� RGB_565:��16λ��2�ֽ�
		 */
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		/**
		 * inPurgeable:����ΪTrue,��ʹ��BitmapFactory������Bitmap���ڴ洢Pixel���ڴ�ռ䣬
		 * ��ϵͳ�ڴ治��ʱ���Ա����գ���Ӧ����Ҫ�ٴη��ʸ�Bitmap��Pixelʱ��ϵͳ���ٴε���BitmapFactory
		 * ��decode������������Bitmap��Pixel���顣 ����ΪFalseʱ����ʾ���ܱ����ա�
		 */
		options.inPurgeable = true;
		options.inInputShareable = true;
		/**
		 * ����decodeʱ�����ű�����
		 */
		options.inSampleSize = 1;
		bm = BitmapFactory.decodeFile(imgPath, options);
		return bm;
	}

	/**
	 * ��ͼƬ���浽���ص�SD����
	 * 
	 * @param fileName
	 * @param bitmap
	 */
	private void putSDBitmap(final String fileName, final Bitmap bitmap) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File cacheDir = new File(Constants.IMG_CACHE_DIR_PATH);
				if (!cacheDir.exists())
					cacheDir.mkdirs();
				File cacheFile = new File(Constants.IMG_CACHE_DIR_PATH + "/"
						+ fileName);
				if (!cacheFile.exists()) {
					try {
						cacheFile.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(cacheFile);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
