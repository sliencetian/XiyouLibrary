package com.tz.xiyoulibrary.utils;

import com.tz.xiyoulibrary.activity.mybroorw.model.IMyBorrowModel;

public class JsonUtils {

	public static Object[] getErrorMsg(String errorTag) {
		Object[] o = new Object[2];
		if (errorTag.equals("ACCOUNT_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "账号错误，密码错误或账户不存在";
		} else if (errorTag.equals("USER_NOT_LOGIN")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "用户未登陆";
		} else if (errorTag.equals("NO_RECORD")) {
			o[0] = IMyBorrowModel.NO_DATA;
			o[1] = "记录为空";
		} else if (errorTag.equals("REMOTE_SERVER_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "远程服务器错误";
		} else if (errorTag.equals("PARAM_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "参数错误";
		} else if (errorTag.equals("RENEW_FAILED")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "续借失败";
		} else if (errorTag.equals("OUT_OF_RANGE")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "超出范围";
		}
		return o;
	}
}
