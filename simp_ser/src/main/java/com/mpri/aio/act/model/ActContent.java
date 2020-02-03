package com.mpri.aio.act.model;
import com.mpri.aio.base.model.DataEntity;


/**
 *  
 * @Description:  活动
 * @Author:       cary
 * @project       simp   
 * @CreateDate:   Fri Mar 01 13:30:13 CST 2019
 * @Version:      v_1.2
 *    
 */
public class ActContent extends DataEntity<ActContent> {

	private static final long serialVersionUID = 1551418193585L;
	
	//业务字段
	private String userId;
	private String val;
	private String actId;
	private String code;
	private int sort;


	
	//补充回显字段
	private String name;
	private String userTrueName;
	private String userSex;
	private String userContact;
	private String userName;
	private String userMobile;

	
	public String getUserTrueName() {
		return userTrueName;
	}
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserContact() {
		return userContact;
	}
	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getVal() {
		return this.val;
	}
	public void setVal(String val) {
		this.val = val;
	}	
	public String getActId() {
		return this.actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
}
