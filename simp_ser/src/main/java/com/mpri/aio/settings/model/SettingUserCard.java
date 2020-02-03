package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  用户校友卡
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Thu Nov 07 17:32:00 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class SettingUserCard extends DataEntity<SettingUserCard> {

	private static final long serialVersionUID = 1573119089938L;
	
	private String cardNo;
	private String jointCardNo;
	private String cardId;
	private String userId;
	private String status;
	private Integer ranking;
	/*校友卡*/
	private SettingCard settingCard;
	

	/*查询参数 校友会id*/
	private String asId;
	
	
	public SettingCard getSettingCard() {
		return settingCard;
	}
	public void setSettingCard(SettingCard settingCard) {
		this.settingCard = settingCard;
	}
	public String getAsId() {
		return asId;
	}
	public void setAsId(String asId) {
		this.asId = asId;
	}
	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}	
	public String getJointCardNo() {
		return this.jointCardNo;
	}
	public void setJointCardNo(String jointCardNo) {
		this.jointCardNo = jointCardNo;
	}	
	public String getCardId() {
		return this.cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public Integer getRanking() {
		return this.ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}	

}
