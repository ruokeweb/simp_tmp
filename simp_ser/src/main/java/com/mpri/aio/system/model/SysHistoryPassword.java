package com.mpri.aio.system.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  用户密码记录
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Thu Dec 26 11:02:28 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class SysHistoryPassword extends DataEntity<SysHistoryPassword> {

	private static final long serialVersionUID = 1577329302458L;
	
	private String userId;
	private String isFirstPassword;
	private String password;

	 /**
	  * 判断是否是弱密码
	  * @return
	  */
	 private boolean isWeakPassword;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getIsFirstPassword() {
		return this.isFirstPassword;
	}
	public void setIsFirstPassword(String isFirstPassword) {
		this.isFirstPassword = isFirstPassword;
	}	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	 public boolean isWeakPassword() {
		 return isWeakPassword;
	 }

	 public void setWeakPassword(boolean weakPassword) {
		 isWeakPassword = weakPassword;
	 }
 }
