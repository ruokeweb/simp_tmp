package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 *  
 * @Description:  校友通讯地址
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:31:22 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smAddress")
public class SmAddress extends DataEntity<SmAddress> {

	private static final long serialVersionUID = 1548660676143L;
	//@Excel(name="地址(省)")
	private String province;
	//@Excel(name="地址(市)")
	private String city;
	//@Excel(name="地址(国家)")
	private String country;
	private String type;
	private String userId;
	@Excel(name="通讯地址",orderNum = "23",needMerge = true,width = 30)
	private String detail;
	@Excel(name="通讯地址邮编",orderNum = "24",needMerge = true ,width = 20)
	private String zipcode;

	//@Excel(name="地址(县)")
	private String district;

	private String isDefault;

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
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
	public String getDetail() {
		return this.detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDistrict() {
		return this.district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
