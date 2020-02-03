package com.mpri.aio.association.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

 /**   
 *  
 * @Description:  校友会对应关系表
 * @Author:       lzq
 * @project       smmp   
 * @CreateDate:   Thu Feb 21 13:24:13 CST 2019
 * @Version:      v_1.2
 *    
 */
public class AsAssociationUser extends DataEntity<AsAssociationUser> {

	private static final long serialVersionUID = 1550726623013L;

	 @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date joinDate;
	private String schoomateId;
	private String associationId;
	private String userId;
	 @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	 private Date exitDate;
	private SmSchoolmate smSchoolmate;

	 public SmSchoolmate getSmSchoolmate() {
		 return smSchoolmate;
	 }

	 public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
		 this.smSchoolmate = smSchoolmate;
	 }

	 public Date getJoinDate() {
		return this.joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}	
	public String getSchoomateId() {
		return this.schoomateId;
	}
	public void setSchoomateId(String schoomateId) {
		this.schoomateId = schoomateId;
	}	
	public String getAssociationId() {
		return this.associationId;
	}
	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public Date getExitDate() {
		return this.exitDate;
	}
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}	

}
