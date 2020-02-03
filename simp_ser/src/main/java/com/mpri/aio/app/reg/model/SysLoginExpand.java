package com.mpri.aio.app.reg.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  登录扩展
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Tue Jul 16 13:23:04 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class SysLoginExpand extends DataEntity<SysLoginExpand> {

	private static final long serialVersionUID = 1563254556793L;
	
	private String userId;
	private String type;
	private String expand;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getExpand() {
		return this.expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}	

}
