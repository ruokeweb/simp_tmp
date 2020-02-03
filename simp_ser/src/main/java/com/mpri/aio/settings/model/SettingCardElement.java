package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友卡元素管理
 * @Author:       zdl
 * @project       smmp   
 * @CreateDate:   Mon Feb 18 10:33:48 CST 2019
 * @Version:      v_1.2
 *    
 */
public class SettingCardElement extends DataEntity<SettingCardElement> {

	private static final long serialVersionUID = 1550457204681L;
	
	private String position;
	private String cardId;
	private String code;
	private String status;
	private String color;
	private String style;
	private String type;
	private String face;

	
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}	
	public String getCardId() {
		return this.cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}	
	public String getStyle() {
		return this.style;
	}
	public void setStyle(String style) {
		this.style = style;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getFace() {
		return this.face;
	}
	public void setFace(String face) {
		this.face = face;
	}	

}
