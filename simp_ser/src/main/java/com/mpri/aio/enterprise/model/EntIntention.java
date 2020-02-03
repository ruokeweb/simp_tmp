package com.mpri.aio.enterprise.model;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 *  
 * @Description:  校友企业校友意向表
 * @Author:       lzq
 * @project       simp   
 * @CreateDate:   Thu Feb 14 16:35:23 CST 2019
 * @Version:      v_1.2
 *    
 */
public class EntIntention extends DataEntity<EntIntention> {

	private static final long serialVersionUID = 1550133303152L;
	
	private String content;
	private String enterId;
	private String title;
	private String userId;

	private SmSchoolmate smSchoolmate;

	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getEnterId() {
		return this.enterId;
	}
	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
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
