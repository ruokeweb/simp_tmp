package com.mpri.aio.info.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  信息留言表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Fri Dec 14 09:44:55 CST 2018
 * @Version:      v_1.2
 *    
 */
public class InfoMessage extends DataEntity<InfoMessage> {

	private static final long serialVersionUID = 1544751882936L;
	
	private String content;
	private String fatherContent;
	private String infoId;
	private Integer likeNum;
	private String pubArea;
	private String pubUser;
	private InfoInformation information; 

	
	public InfoInformation getInformation() {
		return information;
	}
	public void setInformation(InfoInformation information) {
		this.information = information;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getFatherContent() {
		return this.fatherContent;
	}
	public void setFatherContent(String fatherContent) {
		this.fatherContent = fatherContent;
	}	
	public String getInfoId() {
		return this.infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}	
	public Integer getLikeNum() {
		return this.likeNum;
	}
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}	
	public String getPubArea() {
		return this.pubArea;
	}
	public void setPubArea(String pubArea) {
		this.pubArea = pubArea;
	}	
	public String getPubUser() {
		return this.pubUser;
	}
	public void setPubUser(String pubUser) {
		this.pubUser = pubUser;
	}	

}
