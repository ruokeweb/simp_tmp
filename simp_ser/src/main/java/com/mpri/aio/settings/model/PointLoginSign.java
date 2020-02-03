package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

 /**   
 *  
 * @Description:  校友登陆签到表
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Tue Feb 26 10:45:42 CST 2019
 * @Version:      v_1.2
 *    
 */
public class PointLoginSign extends DataEntity<PointLoginSign> {

	private static final long serialVersionUID = 1551149103842L;
	
	private String signstate;
	private String userId;
	private Date signDate;

	
	public String getSignstate() {
		return this.signstate;
	}
	public void setSignstate(String signstate) {
		this.signstate = signstate;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public Date getSignDate() {
		return this.signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}	

}
