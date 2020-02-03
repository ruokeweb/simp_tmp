package com.mpri.aio.enterprise.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import java.util.Date;

/**
 *  
 * @Description:  校友企业息表
 * @Author:       lzq
 * @project       smmp   
 * @CreateDate:   Tue Feb 12 08:41:49 CST 2019
 * @Version:      v_1.2
 *    
 */
public class EntEnterprise extends DataEntity<EntEnterprise> {

	private static final long serialVersionUID = 1549932097703L;
	private String address;
	private String content;
	private String email;

	private String industry;
	private String name;
	private String phone;
	private String type;
	private String webSite;
	private String wechat;


	@Override
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getIndustry() {
		return this.industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getWebSite() {
		return this.webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}	
	public String getWechat() {
		return this.wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}	

}
