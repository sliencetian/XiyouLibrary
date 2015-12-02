package com.tz.xiyoulibrary.utils;

import java.io.File;
import java.util.List;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

public class UpDateUtils {

	/**
	 * 是否是最新版本
	 */
	public static boolean isNew = true;
	/**
	 * 新版本的url
	 */
	public static String apkUrl = "";

	public static int apkSize;
	private static File file;

	/**
	 * 检查更新
	 */
	public static void checkUpdate() {
		AVQuery<AVObject> checkUpdate = new AVQuery<AVObject>("apkfile");
		checkUpdate.findInBackground(new FindCallback<AVObject>() {

			@Override
			public void done(List<AVObject> list, AVException e) {
				if (e == null) {
					LogUtils.d("UpDateUtils", list.toString());
					AVObject object = list.get(0);
					int versionCode = object.getInt("versionCode");
					if (versionCode > Constants.versionCode) {
						isNew = false;
						AVFile avFile = object.getAVFile("apk");
						apkUrl = avFile.getUrl();
						apkSize = avFile.getSize();
					}
				} else {
					e.printStackTrace();
				}
			}
		});
	}

	public static void downLoadApk(final Handler handler) {
		file = new File(Environment.getExternalStorageDirectory()
				+ "/XiyouLibrary.apk");
		FinalHttp fp = new FinalHttp();
		String target = file.getAbsolutePath();
		fp.download(apkUrl, target, new AjaxCallBack<File>() {
			@Override
			public void onStart() {
				super.onStart();
				handler.sendEmptyMessage(0x001);
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
				handler.sendEmptyMessage((int) (current * 100 / count));
			}

			@Override
			public void onSuccess(File t) {
				super.onSuccess(t);
				Message msg = Message.obtain();
				msg.what = 0x003;
				msg.obj = t;
				handler.sendMessage(msg);
			}
		});

	}

}
