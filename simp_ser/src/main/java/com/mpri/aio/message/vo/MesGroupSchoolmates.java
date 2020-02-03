package com.mpri.aio.message.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class MesGroupSchoolmates {

	private String id;
	
	private String userId;
	@Excel(name="姓名",width = 30,orderNum = "0")
	private String name;
	@Excel(name="联系方式",width = 50,orderNum = "1")
	private String contact;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
