package com.mpri.aio.schoolmate.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class TelephoneInfoVo {

	@Excel(name="序号",width = 15,orderNum = "0")
	private int order;
    @Excel(name = "姓名" , width = 20)
    private String name;
    @Excel(name = "手机号码" , width = 50,orderNum = "1")
    private String contact;

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
    
}
