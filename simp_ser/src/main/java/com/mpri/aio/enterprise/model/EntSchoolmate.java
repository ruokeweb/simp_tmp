package com.mpri.aio.enterprise.model;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 *  
 * @Description:  校友企业校友任职表
 * @Author:       lzq
 * @project       simp   
 * @CreateDate:   Thu Feb 14 15:59:00 CST 2019
 * @Version:      v_1.2
 *    
 */
public class EntSchoolmate extends DataEntity<EntSchoolmate> {

	private static final long serialVersionUID = 1550131119198L;
	private String department;
	private String enterId;
	private String position;
	private String userId;
	private SmSchoolmate smSchoolmate;



	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}	
	public String getEnterId() {
		return this.enterId;
	}
	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}	
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SmSchoolmate getSmSchoolmate() {
		return smSchoolmate;
	}

	public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
		this.smSchoolmate = smSchoolmate;
	}
}
