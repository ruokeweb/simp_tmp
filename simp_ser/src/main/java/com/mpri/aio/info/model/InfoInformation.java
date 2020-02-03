package com.mpri.aio.info.model;
import com.mpri.aio.base.model.DataEntity; 
import java.util.Date;

 /**   
 *  
 * @Description:  信息详情表
 * @Author:       syp
 * @project       smmp   
 * @CreateDate:   Wed Dec 12 09:24:19 CST 2018
 * @Version:      v_1.2
 *    
 */
public class InfoInformation extends DataEntity<InfoInformation> {

	private static final long serialVersionUID = 1544577848336L;
	
	private String author;
	private String content;
	private String indexImages;
	private String indexImgPos;
	private String isTopnic;
	private Integer messageNum;
	private String pubStatus;
	private Date pubTime;
	private String pubUser;
	private Integer readNum;
	private String secondTitle;
	private Integer sort;
	private String title;
	private String typeId;

	
	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getIndexImages() {
		return this.indexImages;
	}
	public void setIndexImages(String indexImages) {
		this.indexImages = indexImages;
	}	
	public String getIndexImgPos() {
		return this.indexImgPos;
	}
	public void setIndexImgPos(String indexImgPos) {
		this.indexImgPos = indexImgPos;
	}	
	public String getIsTopnic() {
		return this.isTopnic;
	}
	public void setIsTopnic(String isTopnic) {
		this.isTopnic = isTopnic;
	}	
	public Integer getMessageNum() {
		return this.messageNum;
	}
	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}	
	public String getPubStatus() {
		return this.pubStatus;
	}
	public void setPubStatus(String pubStatus) {
		this.pubStatus = pubStatus;
	}	
	public Date getPubTime() {
		return this.pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}	
	public String getPubUser() {
		return this.pubUser;
	}
	public void setPubUser(String pubUser) {
		this.pubUser = pubUser;
	}	
	public Integer getReadNum() {
		return this.readNum;
	}
	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}	
	public String getSecondTitle() {
		return this.secondTitle;
	}
	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}	
	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public String getTypeId() {
		return this.typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}	

}
