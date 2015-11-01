package com.tz.xiyoulibrary.utils;

public class JsonUtils {

	public static String getErrorMsg(String errorTag) {
		String errorInfo = null;
		if (errorTag.equals("ACCOUNT_ERROR")) {
			errorInfo = "账号错误，密码错误或账户不存在";
		} else if (errorTag.equals("USER_NOT_LOGIN")) {
			errorInfo = "用户未登陆";
		} else if (errorTag.equals("NO_RECORD")) {
			errorInfo = "记录为空";
		} else if (errorTag.equals("REMOTE_SERVER_ERROR")) {
			errorInfo = "远程服务器错误";
		} else if (errorTag.equals("PARAM_ERROR")) {
			errorInfo = "参数错误";
		} else if (errorTag.equals("RENEW_FAILED")) {
			errorInfo = "续借失败";
		} else if (errorTag.equals("OUT_OF_RANGE")) {
			errorInfo = "超出范围";
		}
		return errorInfo;
	}
}
