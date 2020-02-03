package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  等级积分设置
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Mon Feb 25 16:11:15 CST 2019
 * @Version:      v_1.2
 *    
 */
public class PointLevel extends DataEntity<PointLevel> {

	private static final long serialVersionUID = 1551082233669L;
	
	private String levelIcon;
	private String name;
	private String code;
	private Integer startPoint;
	private Integer value;
	private Integer endPoint;

	
	public String getLevelIcon() {
		return this.levelIcon;
	}
	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}	
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
	public Integer getStartPoint() {
		return this.startPoint;
	}
	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}	
	public Integer getValue() {
		return this.value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}	
	public Integer getEndPoint() {
		return this.endPoint;
	}
	public void setEndPoint(Integer endPoint) {
		this.endPoint = endPoint;
	}	

}
