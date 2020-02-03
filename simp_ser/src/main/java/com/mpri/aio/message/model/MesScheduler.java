package com.mpri.aio.message.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

 /**   
 *  
 * @Description:  定时通知消息
 * @Author:       Clown
 * @project       simp   
 * @CreateDate:   Tue Mar 12 15:51:03 CST 2019
 * @Version:      v_2.0
 *    
 */
public class MesScheduler extends DataEntity<MesScheduler> {

	private static final long serialVersionUID = 1552377039753L;
	
	private String name;
	private String sendType;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date date;
	private String mesTemplate;
	private String content;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getSendType() {
		return this.sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}	
	public Date getDate() {
		return this.date;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setDate(Date date) {
		this.date = date;
	}	
	public String getMesTemplate() {
		return this.mesTemplate;
	}
	public void setMesTemplate(String mesTemplate) {
		this.mesTemplate = mesTemplate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	

	
}
