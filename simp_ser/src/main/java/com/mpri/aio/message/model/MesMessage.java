package com.mpri.aio.message.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

 /**   
 *  
 * @Description:  通知消息表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Mon Nov 12 17:31:22 CST 2018
 * @Version:      v_1.02
 *    
 */
public class MesMessage extends DataEntity<MesMessage> {

	private static final long serialVersionUID = 1542015070613L;
	
	private String content;
	private Date delDate;
	private String receiveGroupId;
	private String receiveUserId;
	private Integer sendNum;
	private String sendUserId;
	private Integer sort;
	private String status;
	private String title;
	private String type;
	private String mesTemplate;
	private String sendType;
	
	private int preSendCount;

	private String receiveUserName;
	
	private String groupIds;
	
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonFormat(pattern ="yyyy-MM-dd", timezone = "GMT+8")
	public Date getDelDate() {
		return this.delDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}	
	public String getReceiveGroupId() {
		return this.receiveGroupId;
	}
	public void setReceiveGroupId(String receiveGroupId) {
		this.receiveGroupId = receiveGroupId;
	}	
	public String getReceiveUserId() {
		return this.receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}	
	public Integer getSendNum() {
		return this.sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}	
	public String getSendUserId() {
		return this.sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReceiveUserName() {
		return receiveUserName;
	}
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	public String getMesTemplate() {
		return mesTemplate;
	}
	public void setMesTemplate(String mesTemplate) {
		this.mesTemplate = mesTemplate;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public int getPreSendCount() {
		return preSendCount;
	}
	public void setPreSendCount(int preSendCount) {
		this.preSendCount = preSendCount;
	}	
	
}
