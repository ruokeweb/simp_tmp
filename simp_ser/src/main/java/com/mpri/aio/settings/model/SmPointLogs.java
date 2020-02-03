package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友积分记录
 * @Author:       syp
 * @project       sm   
 * @CreateDate:   Fri Sep 14 15:17:48 CST 2018
 * @Version:      v_1.0
 *    
 */
public class SmPointLogs extends DataEntity<SmPointLogs> {

	private static final long serialVersionUID = 1536909459123L;
	
	private String sysUserId;
	private String username;
	private String type;
	private String operateIp;
	private Integer integraltion;

	
	public String getSysUserId() {
		return this.sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getOperateIp() {
		return this.operateIp;
	}
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}	
	public Integer getIntegraltion() {
		return this.integraltion;
	}
	public void setIntegraltion(Integer integraltion) {
		this.integraltion = integraltion;
	}	

}
