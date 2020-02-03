package com.mpri.aio.settings.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

 /**   
 *  
 * @Description:  院系专业
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Thu Jan 31 11:33:46 CST 2019
 * @Version:      v_1.2
 *    
 */
public class SettingDepartment extends DataEntity<SettingDepartment> {

	private static final long serialVersionUID = 1548905611867L;
	
	private String code;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;
	private Integer grade;
	private String icon;
	private String master;
	private String mobile;
	private String name;
	private String parentId;
	private String parentIds;
	private Integer sort;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;
	private String type;
	private String url;
	private SettingDepartment parentStDt;
	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	public Integer getGrade() {
		return this.grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}	
	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}	
	public String getMaster() {
		return this.master;
	}
	public void setMaster(String master) {
		this.master = master;
	}	
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
    	super.cacheKey=parentId;
        this.parentId = parentId == null ? null : parentId.trim();
	}	
	public String getParentIds() {
		return this.parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	 public SettingDepartment getParentStDt() {
		 return parentStDt;
	 }

	 public void setParentStDt(SettingDepartment parentStDt) {
		 this.parentStDt = parentStDt;
	 }
 }
