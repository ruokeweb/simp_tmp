package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友积分场景表
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Tue Feb 26 10:46:44 CST 2019
 * @Version:      v_1.2
 *    
 */
public class PointFunction extends DataEntity<PointFunction> {

	private static final long serialVersionUID = 1551149160647L;
	
	private String name;
	private Integer point;
	private String code;
	private String isuse;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Integer getPoint() {
		return this.point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public String getIsuse() {
		return this.isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}	

}
