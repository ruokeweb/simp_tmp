package com.mpri.aio.app.wxdata.model;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

 /**   
 *  
 * @Description:  模板消息
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Fri Jul 19 14:05:13 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class WxdataTemplateSend extends DataEntity<WxdataTemplateSend> {

	private static final long serialVersionUID = 1563516281539L;
	
	private String touser;
	private String templateId;
	private String page;
	private String formId;
	private String emphasisKeyword;
	private Date validPeriod;
	private String eventId;

	
	public String getTouser() {
		return this.touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}	
	public String getTemplateId() {
		return this.templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}	
	public String getPage() {
		return this.page;
	}
	public void setPage(String page) {
		this.page = page;
	}	
	public String getFormId() {
		return this.formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}	
	public String getEmphasisKeyword() {
		return this.emphasisKeyword;
	}
	public void setEmphasisKeyword(String emphasisKeyword) {
		this.emphasisKeyword = emphasisKeyword;
	}	
	public Date getValidPeriod() {
		return this.validPeriod;
	}
	public void setValidPeriod(Date validPeriod) {
		this.validPeriod = validPeriod;
	}

	 public String getEventId() {
		 return eventId;
	 }

	 public void setEventId(String eventId) {
		 this.eventId = eventId;
	 }
 }
