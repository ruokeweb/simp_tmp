package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  学科管理
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Tue Feb 19 16:23:21 CST 2019
 * @Version:      v_1.2
 *    
 */
public class SettingSubject extends DataEntity<SettingSubject> {

	private static final long serialVersionUID = 1550564565713L;
	
	private String name;
	private String parentId;
	private String parentIds;
	private String code;
	private Integer sort;
	private Integer grade;
	private SettingSubject parentStSb;
	
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
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public Integer getGrade() {
		return this.grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	 public SettingSubject getParentStSb() {
		 return parentStSb;
	 }

	 public void setParentStSb(SettingSubject parentStSb) {
		 this.parentStSb = parentStSb;
	 }
 }
