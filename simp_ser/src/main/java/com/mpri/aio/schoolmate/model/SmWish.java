package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友祝福
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Tue May 28 17:57:43 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class SmWish extends DataEntity<SmWish> {

	private static final long serialVersionUID = 1559037434341L;
	
	private String userId;
	private String name;
	private String isshow;
	private String information;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getIsshow() {
		return this.isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}	
	public String getInformation() {
		return this.information;
	}
	public void setInformation(String information) {
		this.information = information;
	}	

}
