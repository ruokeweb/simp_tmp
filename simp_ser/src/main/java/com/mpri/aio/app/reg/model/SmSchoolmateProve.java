package com.mpri.aio.app.reg.model;

import com.mpri.aio.base.model.DataEntity;

/**   
*  
* @Description:  校友认证信息表
* @Author:       syp
*    
*/
public class SmSchoolmateProve extends DataEntity<SmSchoolmateProve> {

	private static final long serialVersionUID = 1536910398615L;
	
	private String startUserId;
	private String startUsername;
	private String proveUserId;
	private String proveUsername;
	private String status;
	//你是否点击认识了他
	private String isCognize;

	public String getStartUserId() {
		return this.startUserId;
	}
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}	
	public String getStartUsername() {
		return this.startUsername;
	}
	public void setStartUsername(String startUsername) {
		this.startUsername = startUsername;
	}	
	public String getProveUserId() {
		return this.proveUserId;
	}
	public void setProveUserId(String proveUserId) {
		this.proveUserId = proveUserId;
	}	
	public String getProveUsername() {
		return this.proveUsername;
	}
	public void setProveUsername(String proveUsername) {
		this.proveUsername = proveUsername;
	}
   public String getStatus()
   {
       return status;
   }
   public void setStatus(String status)
   {
       this.status = status;
   }
	public String getIsCognize() {
		return isCognize;
	}
	public void setIsCognize(String isCognize) {
		this.isCognize = isCognize;
	}	
   
}
