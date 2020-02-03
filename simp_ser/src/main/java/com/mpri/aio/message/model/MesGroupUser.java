package com.mpri.aio.message.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息组用户关系表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Mon Nov 12 17:28:51 CST 2018
 * @Version:      v_1.02
 *    
 */
public class MesGroupUser extends DataEntity<MesGroupUser> {

	private static final long serialVersionUID = 1542014919263L;
	
	private String groupId;
	private String userId;

	
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	

}
