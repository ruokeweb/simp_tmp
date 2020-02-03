package com.mpri.aio.act.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  活动
 * @Author:       cary
 * @project       simp   
 * @CreateDate:   Fri Mar 01 13:28:21 CST 2019
 * @Version:      v_1.2
 *    
 */
public class ActSetting extends DataEntity<ActSetting> {

	private static final long serialVersionUID = 1551418083213L;
	
	private String formType;
	private String name;
	private String activityId;
	private String code;
	private Integer sort;
	private String defaultVal;

	
	public String getFormType() {
		return this.formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getActivityId() {
		return this.activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public String getDefaultVal() {
		return this.defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}	

}
