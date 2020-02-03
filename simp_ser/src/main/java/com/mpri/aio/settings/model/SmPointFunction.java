package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友积分场景
 * @Author:       syp
 * @project       sm   
 * @CreateDate:   Fri Sep 14 14:34:30 CST 2018
 * @Version:      v_1.0
 *    
 */
public class SmPointFunction extends DataEntity<SmPointFunction> {

	private static final long serialVersionUID = 1536906861191L;
	
	private String name;
	private String code;
	private Integer point;
	private String isuse;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public Integer getPoint() {
		return this.point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}	
	public String getIsuse() {
		return this.isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}	

}
