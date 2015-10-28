package com.tz.xiyoulibrary.bean;

import java.io.Serializable;

/**
 * 
 * @author tianzhao 图书馆
 */
public class BookBean implements Serializable {

	private static final long serialVersionUID = -5223987826917760780L;
	private String title;
	private String barCode;// 条形码
	private String department;// 所在分馆
	private String state;// 当前状态
	private String date;// 应还日期
	private boolean canRenew;// 是否可续借
	private String department_id;// 书库ID号，用于续借
	private String library_id;// 分馆ID号，用于续借

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isCanRenew() {
		return canRenew;
	}

	public void setCanRenew(boolean canRenew) {
		this.canRenew = canRenew;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}

}
