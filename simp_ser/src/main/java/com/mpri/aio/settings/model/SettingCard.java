package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友卡管理
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Fri Feb 15 10:15:16 CST 2019
 * @Version:      v_1.2
 *    
 */
public class SettingCard extends DataEntity<SettingCard> {

	private static final long serialVersionUID = 1550196896999L;
	
	private String backBackground;
	private String cardStyleLine;
	private String cardStyleOpacity;
	private String cardStyleRadius;
	private Integer endLevel;
	private String frontBackground;
	private String name;
	private Integer startLevel;
	private String type;
	private String asId;

	
	public String getAsId() {
		return asId;
	}
	public void setAsId(String asId) {
		this.asId = asId;
	}
	public String getBackBackground() {
		return this.backBackground;
	}
	public void setBackBackground(String backBackground) {
		this.backBackground = backBackground;
	}	
	public String getCardStyleLine() {
		return this.cardStyleLine;
	}
	public void setCardStyleLine(String cardStyleLine) {
		this.cardStyleLine = cardStyleLine;
	}	
	public String getCardStyleOpacity() {
		return this.cardStyleOpacity;
	}
	public void setCardStyleOpacity(String cardStyleOpacity) {
		this.cardStyleOpacity = cardStyleOpacity;
	}	
	public String getCardStyleRadius() {
		return this.cardStyleRadius;
	}
	public void setCardStyleRadius(String cardStyleRadius) {
		this.cardStyleRadius = cardStyleRadius;
	}	
	public Integer getEndLevel() {
		return this.endLevel;
	}
	public void setEndLevel(Integer endLevel) {
		this.endLevel = endLevel;
	}	
	public String getFrontBackground() {
		return this.frontBackground;
	}
	public void setFrontBackground(String frontBackground) {
		this.frontBackground = frontBackground;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Integer getStartLevel() {
		return this.startLevel;
	}
	public void setStartLevel(Integer startLevel) {
		this.startLevel = startLevel;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	

}
