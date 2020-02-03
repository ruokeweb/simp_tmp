package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 *  
 * @Description:  校友联系方式
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:30:05 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smContact")
public class SmContact extends DataEntity<SmContact> {

	private static final long serialVersionUID = 1548660600187L;
	//@Excel(name="联系方式(类型)")
	private String type;
	private String userId;
	//@Excel(name="联系方式")
	private String contact;
	private String isDefault;
	
	/**
	 * 导出联系方式时的姓名
	 */
	private String name;
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getIsDefault() {
		return this.isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
