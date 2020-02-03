package com.mpri.aio.message.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  用户消息标记表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Mon Nov 12 17:32:26 CST 2018
 * @Version:      v_1.02
 *    
 */
public class MesUserMessage extends DataEntity<MesUserMessage> {

	private static final long serialVersionUID = 1542015134217L;
	
	private String messageId;
	private String status;
	private String userId;

	
	public String getMessageId() {
		return this.messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	

}
