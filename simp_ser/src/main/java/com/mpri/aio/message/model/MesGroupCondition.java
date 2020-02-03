package com.mpri.aio.message.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息群组条件
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Wed Nov 14 15:50:23 CST 2018
 * @Version:      v_1.02
 *    
 */
public class MesGroupCondition extends DataEntity<MesGroupCondition> {

	private static final long serialVersionUID = 1542181810107L;
	
	private String field;
	private String groupId;
	private String value;
	
	private String name;

	
	public String getField() {
		return this.field;
	}
	public void setField(String field) {
		this.field = field;
	}	
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MesGroupCondition() {};
	
	public MesGroupCondition(String groupId, String field,  String value) {
		this.groupId = groupId;
		this.field = field;
		this.value = value;
	}	

	
}
