package com.mpri.aio.act.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  值年返校内容
 * @Author:       cary
 * @project       simp   
 * @CreateDate:   Mon May 27 15:50:50 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class ActSelforgContent extends DataEntity<ActSelforgContent> {

	private static final long serialVersionUID = 1558943423568L;
	
	private String userId;
	private String actSelforgId;
	private String name;//姓名

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getActSelforgId() {
		return this.actSelforgId;
	}
	public void setActSelforgId(String actSelforgId) {
		this.actSelforgId = actSelforgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 }
