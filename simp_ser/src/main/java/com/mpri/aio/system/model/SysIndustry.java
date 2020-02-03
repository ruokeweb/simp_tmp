package com.mpri.aio.system.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  行业管理
 * @Author:       Carry
 * @project       simp   
 * @CreateDate:   Thu Feb 21 15:52:16 CST 2019
 * @Version:      v_2.0
 *    
 */
public class SysIndustry extends DataEntity<SysIndustry> {

	private static final long serialVersionUID = 1550735509359L;
	
	private String parentId;
	private String parentIds;
	private String code;
	private String sort;
	private String name;
	private SysIndustry parentSysIndustry;
	
	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
    	super.cacheKey=parentId;
        this.parentId = parentId == null ? null : parentId.trim();
	}	
	public String getParentIds() {
		return this.parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		super.cacheKey=code;
		this.code = code;
	}	
	public String getSort() {
		return this.sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SysIndustry getParentSysIndustry() {
		return parentSysIndustry;
	}
	public void setParentSysIndustry(SysIndustry parentSysIndustry) {
		this.parentSysIndustry = parentSysIndustry;
	}	
	

}
