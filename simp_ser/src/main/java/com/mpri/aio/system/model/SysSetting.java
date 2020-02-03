package com.mpri.aio.system.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  配置信息
 * @Author:       Carry
 * @project       smmp   
 * @CreateDate:   Wed Feb 13 10:22:06 CST 2019
 * @Version:      v_2.0
 *    
 */
public class SysSetting extends DataEntity<SysSetting> {

	private static final long serialVersionUID = 1550024508313L;
	
	private String code;
	private String name;
	private String status;
	private String value;

	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		super.cacheKey=code;
		this.code = code;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}	

}
