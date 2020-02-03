package com.mpri.aio.act.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *  
 * @Description:  活动
 * @Author:       cary
 * @project       simp   
 * @CreateDate:   Fri Mar 01 13:25:30 CST 2019
 * @Version:      v_1.2
 *    
 */
public class ActActivity extends DataEntity<ActActivity> {

	private static final long serialVersionUID = 1551417911926L;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date signStartDate;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;
	private String image;
	private String area;
	private Integer limitNo;
	private String assoId;
	private String name;
	private String content;
	private Integer readyNo;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date signEndDate;
	private String status;
	private String assoName;
	private String statusName;


	/**
	 * 报表查询时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(pattern = "yyyy-MM")
	private Date chartStartDate;
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(pattern = "yyyy-MM")
	private Date chartEndDate;

	public Date getChartStartDate() {
		return chartStartDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM")
	public void setChartStartDate(Date chartStartDate) {
		this.chartStartDate = chartStartDate;
	}

	public Date getChartEndDate() {
		return chartEndDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM")
	public void setChartEndDate(Date chartEndDate) {
		this.chartEndDate = chartEndDate;
	}

	public String getAssoName() {
		return assoName;
	}
	public void setAssoName(String assoName) {
		this.assoName = assoName;
	}
	public Date getSignStartDate() {
		return this.signStartDate;
	}
	public void setSignStartDate(Date signStartDate) {
		this.signStartDate = signStartDate;
	}	
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}	
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
	public String getArea() {
		return this.area;
	}
	public void setArea(String area) {
		this.area = area;
	}	
	public Integer getLimitNo() {
		return this.limitNo;
	}
	public void setLimitNo(Integer limitNo) {
		this.limitNo = limitNo;
	}	
	public String getAssoId() {
		return this.assoId;
	}
	public void setAssoId(String assoId) {
		this.assoId = assoId;
	}	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public Integer getReadyNo() {
		return this.readyNo;
	}
	public void setReadyNo(Integer readyNo) {
		this.readyNo = readyNo;
	}	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	public Date getSignEndDate() {
		return this.signEndDate;
	}
	public void setSignEndDate(Date signEndDate) {
		this.signEndDate = signEndDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
 }
