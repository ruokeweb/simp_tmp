package com.mpri.aio.association.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

 /**   
 *  
 * @Description:  校友会任职信息
 * @Author:       lzq
 * @project       smmp   
 * @CreateDate:   Thu Feb 21 13:26:24 CST 2019
 * @Version:      v_1.2
 *    
 */
public class AsPost extends DataEntity<AsPost> {

	private static final long serialVersionUID = 1550726759097L;
	 @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	 private Date leaveDate;
	 @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	 private Date postDate;
	private Integer postNum;
	private String associationId;
	private String roleType;
	private String userId;
	private String isPosting;
	private SmSchoolmate smSchoolmate;

	
	public Date getLeaveDate() {
		return this.leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}	
	public Date getPostDate() {
		return this.postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}	
	public Integer getPostNum() {
		return this.postNum;
	}
	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}	
	public String getAssociationId() {
		return this.associationId;
	}
	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}	
	public String getRoleType() {
		return this.roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getIsPosting() {
		return this.isPosting;
	}
	public void setIsPosting(String isPosting) {
		this.isPosting = isPosting;
	}

	 public SmSchoolmate getSmSchoolmate() {
		 return smSchoolmate;
	 }

	 public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
		 this.smSchoolmate = smSchoolmate;
	 }
 }
