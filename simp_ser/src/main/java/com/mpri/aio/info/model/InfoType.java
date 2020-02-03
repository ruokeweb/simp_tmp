package com.mpri.aio.info.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息类型表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Mon Dec 10 09:52:55 CST 2018
 * @Version:      v_1.2
 *    
 */
public class InfoType extends DataEntity<InfoType> {

	private static final long serialVersionUID = 1544406765323L;
	
	private Integer sort;
	private String typeCode;
	private String typeName;

	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public String getTypeCode() {
		return this.typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}	
	public String getTypeName() {
		return this.typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}	

}
