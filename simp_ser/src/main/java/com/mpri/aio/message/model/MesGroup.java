package com.mpri.aio.message.model;
import java.util.ArrayList;
import java.util.List;

import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息群组
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Mon Nov 12 17:33:03 CST 2018
 * @Version:      v_1.02
 *    
 */
public class MesGroup extends DataEntity<MesGroup> {

	private static final long serialVersionUID = 1542015171696L;
	
	private String description;
	private String name;
	
	private List<MesGroupCondition> mesGroupConditions = new ArrayList<MesGroupCondition>();
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MesGroupCondition> getMesGroupConditions() {
		return mesGroupConditions;
	}
	public void setMesGroupConditions(List<MesGroupCondition> mesGroupConditions) {
		this.mesGroupConditions = mesGroupConditions;
	}		
}
