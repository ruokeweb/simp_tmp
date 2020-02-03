package com.mpri.aio.info.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息点赞表表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Fri Dec 14 13:55:56 CST 2018
 * @Version:      v_1.2
 *    
 */
public class InfoMessageLike extends DataEntity<InfoMessageLike> {

	private static final long serialVersionUID = 1544766943334L;
	
	private String messageId;
	private String pubUser;
	//标记
	private Integer isLike;
	
	
	public Integer getIsLike() {
		return isLike;
	}
	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}
	public String getMessageId() {
		return this.messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}	
	public String getPubUser() {
		return this.pubUser;
	}
	public void setPubUser(String pubUser) {
		this.pubUser = pubUser;
	}	

}
