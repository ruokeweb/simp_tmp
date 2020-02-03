package com.mpri.aio.schoolmate.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友其他信息类型表
 * @Author:       Clown
 * @project       simp   
 * @CreateDate:   Sat Mar 02 16:01:26 CST 2019
 * @Version:      v_2.0
 *    
 */
public class SmOther extends DataEntity<SmOther> {

	private static final long serialVersionUID = 1551513669139L;
	
	private String type;
	private String value;
	private String name;

	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	

}
