package com.mpri.aio.donation.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  捐赠致谢
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Wed Mar 27 16:42:55 CST 2019
 * @Version:      v_1.2
 *    
 */
public class DonThanks extends DataEntity<DonThanks> {

	private static final long serialVersionUID = 1553676137545L;
	
	private String name;
	private String userId;
	private String information;
	private String isshow;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getInformation() {
		return this.information;
	}
	public void setInformation(String information) {
		this.information = information;
	}	
	public String getIsshow() {
		return this.isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}	

}
