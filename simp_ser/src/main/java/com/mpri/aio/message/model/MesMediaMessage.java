package com.mpri.aio.message.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

 /**   
 *  
 * @Description:  每日多媒体通知
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Tue Nov 05 14:18:55 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class MesMediaMessage extends DataEntity<MesMediaMessage> {

	private static final long serialVersionUID = 1572934705115L;
	
	private String appendix;
	private String sendUserId;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date delDate;
	private String status;
	private String type;
	private String title;
	private String coverImage;
	private String content;
	
	/*附件名称*/
	private String appendixName;

	
	public String getAppendixName() {
		return appendixName;
	}
	public void setAppendixName(String appendixName) {
		this.appendixName = appendixName;
	}
	public String getAppendix() {
		return this.appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}	
	public String getSendUserId() {
		return this.sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}	
	public Date getDelDate() {
		return this.delDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public String getCoverImage() {
		return this.coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	

}
