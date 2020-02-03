package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友积分记录表
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Tue Feb 26 10:44:18 CST 2019
 * @Version:      v_1.2
 *    
 */
public class PointLogs extends DataEntity<PointLogs> {

	private static final long serialVersionUID = 1551149020129L;
	
	private String type;
	private String userId;
	private Integer integraltion;
	private String operateIp;
	private String username;


	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public Integer getIntegraltion() {
		return this.integraltion;
	}
	public void setIntegraltion(Integer integraltion) {
		this.integraltion = integraltion;
	}	
	public String getOperateIp() {
		return this.operateIp;
	}
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	 public String getUsername() {
		 return username;
	 }

	 public void setUsername(String username) {
		 this.username = username;
	 }
	
	 
	 
 }
