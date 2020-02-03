package com.mpri.aio.schoolmate.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

 /**   
 *  
 * @Description:  校友其他教育经历表
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Tue Jul 30 15:06:53 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class SmHisEducation extends DataEntity<SmHisEducation> {

	private static final long serialVersionUID = 1564470381056L;
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private Date enddate;
	private String userId;
	private String education;
	private String type;
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private Date startdate;

	
	public Date getEnddate() {
		return this.enddate;
	}
	@DateTimeFormat(pattern = "yyyy")
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getEducation() {
		return this.education;
	}
	public void setEducation(String education) {
		this.education = education;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public Date getStartdate() {
		return this.startdate;
	}
	@DateTimeFormat(pattern = "yyyy")
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}	

}
