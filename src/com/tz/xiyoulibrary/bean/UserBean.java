package com.tz.xiyoulibrary.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = -4141648451031370940L;
	private String id;// 学号
	private String name;// 姓名
	private String fromData;// 有效期开始日期
	private String toData;// 有效期结束日期
	private String readerType;// 用户类别
	private String department;// 行政单位
	private String debt;// 欠费金额

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFromData() {
		return fromData;
	}

	public void setFromData(String fromData) {
		this.fromData = fromData;
	}

	public String getToData() {
		return toData;
	}

	public void setToData(String toData) {
		this.toData = toData;
	}

	public String getReaderType() {
		return readerType;
	}

	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

}
