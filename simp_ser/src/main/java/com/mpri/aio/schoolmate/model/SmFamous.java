package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity;

import java.util.Date;

/**
 *  
 * @Description:  知名校友管理
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Fri Mar 01 13:28:24 CST 2019
 * @Version:      v_1.2
 *    
 */
public class SmFamous extends DataEntity<SmFamous> {

	private static final long serialVersionUID = 1551418044184L;
	private String userId;
	private Integer weight;
	private Integer sort;
	private String name;
	private String sex;
	private String truePhoto;
	private Date birthday;
	private String egName;
	private String photo;
	private String information;
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public Integer getWeight() {
		return this.weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTruePhoto() {
		return truePhoto;
	}

	public void setTruePhoto(String truePhoto) {
		this.truePhoto = truePhoto;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEgName() {
		return egName;
	}

	public void setEgName(String egName) {
		this.egName = egName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
}
