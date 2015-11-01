package com.tz.xiyoulibrary.utils;

import com.tz.xiyoulibrary.activity.mybroorw.model.IMyBorrowModel;

public class JsonUtils {

	public static Object[] getErrorMsg(String errorTag) {
		Object[] o = new Object[2];
		if (errorTag.equals("ACCOUNT_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "�˺Ŵ������������˻�������";
		} else if (errorTag.equals("USER_NOT_LOGIN")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "�û�δ��½";
		} else if (errorTag.equals("NO_RECORD")) {
			o[0] = IMyBorrowModel.NO_DATA;
			o[1] = "��¼Ϊ��";
		} else if (errorTag.equals("REMOTE_SERVER_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "Զ�̷���������";
		} else if (errorTag.equals("PARAM_ERROR")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "��������";
		} else if (errorTag.equals("RENEW_FAILED")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "����ʧ��";
		} else if (errorTag.equals("OUT_OF_RANGE")) {
			o[0] = IMyBorrowModel.LOADING_FALUIRE;
			o[1] = "������Χ";
		}
		return o;
	}
}
