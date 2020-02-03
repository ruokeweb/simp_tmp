package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友网配置表
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Mon Sep 09 10:08:46 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class SettingWebvsb extends DataEntity<SettingWebvsb> {

	private static final long serialVersionUID = 1567994886958L;
	
	private String treeId;
	private String viewId;
	private String name;
	private int pageNo;
	private int pageSize;

	
	public String getTreeId() {
		return this.treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}	
	public String getViewId() {
		return this.viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "SettingWebvsb [treeId=" + treeId + ", viewId=" + viewId + ", name=" + name + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + "]";
	}	
	
	
}
