package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友卡其他信息
 * @Author:       Clown
 * @project       simp   
 * @CreateDate:   Mon Mar 04 17:51:29 CST 2019
 * @Version:      v_2.0
 *    
 */
public class SmOtherInfo extends DataEntity<SmOtherInfo> {

	private static final long serialVersionUID = 1551693070602L;
	
	private String userId;
	private String value;
	private String otherId;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	public String getOtherId() {
		return this.otherId;
	}
	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}	

}
