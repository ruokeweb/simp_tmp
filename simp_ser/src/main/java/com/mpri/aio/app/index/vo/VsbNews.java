package com.mpri.aio.app.index.vo;

import java.util.Date;

/**
 * 网站群新闻vo
 * @author Administrator
 *
 */
public class VsbNews {

	private String id;
	
	private String date;
	
	private Date showDate;
	
	private String picUrl;
	
	private String tittle;
	
	private String showTittle;
	
	private String newsAbstract;
	
	private String showNewsAbstract;
	
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getShowDate() {
		return showDate;
	}

	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getShowTittle() {
		return showTittle;
	}

	public void setShowTittle(String showTittle) {
		this.showTittle = showTittle;
	}

	public String getNewsAbstract() {
		return newsAbstract;
	}

	public void setNewsAbstract(String newsAbstract) {
		this.newsAbstract = newsAbstract;
	}

	public String getShowNewsAbstract() {
		return showNewsAbstract;
	}

	public void setShowNewsAbstract(String showNewsAbstract) {
		this.showNewsAbstract = showNewsAbstract;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
