package com.mpri.aio.act.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import java.util.Date;

 /**   
 *  
 * @Description:  值年返校
 * @Author:       cary
 * @project       simp   
 * @CreateDate:   Mon May 27 15:49:44 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class ActSelforg extends DataEntity<ActSelforg> {

	private static final long serialVersionUID = 1558943358451L;
	
	private String conPhone;
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date actDate;
	private Integer limitNo;
	private String name;
	private Integer readyNo;
	private String content;
	private String isReception;
	private String userId;
	private String receptionService;
	private String conName;
	private String otherReception;

	
	public String getConPhone() {
		return this.conPhone;
	}
	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	public Date getActDate() {
		return this.actDate;
	}
	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}	
	public Integer getLimitNo() {
		return this.limitNo;
	}
	public void setLimitNo(Integer limitNo) {
		this.limitNo = limitNo;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Integer getReadyNo() {
		return this.readyNo;
	}
	public void setReadyNo(Integer readyNo) {
		this.readyNo = readyNo;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getIsReception() {
		return this.isReception;
	}
	public void setIsReception(String isReception) {
		this.isReception = isReception;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getReceptionService() {
		return this.receptionService;
	}
	public void setReceptionService(String receptionService) {
		this.receptionService = receptionService;
	}	
	public String getConName() {
		return this.conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}	
	public String getOtherReception() {
		return this.otherReception;
	}
	public void setOtherReception(String otherReception) {
		this.otherReception = otherReception;
	}	

}
