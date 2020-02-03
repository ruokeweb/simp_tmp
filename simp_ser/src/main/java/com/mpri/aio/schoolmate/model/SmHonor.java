package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;


/**
 *
 * @Description:  校友荣誉成果表
 * @Author:       syp
 * @project       exchange_datasource
 * @CreateDate:   Mon Jan 28 15:53:35 CST 2019
 * @Version:      v_1.0
 *
 */
@ExcelTarget("smHonor")
public class SmHonor extends DataEntity<SmHonor> {

	private static final long serialVersionUID = 1548662010037L;
	//@Excel(name="(荣誉)详细说明")
	private String infoval;
	//@Excel(name="(荣誉)名称")
	private String name;
	//@Excel(name="(荣誉)类型")
	private String type;
	//@Excel(name="(荣誉)获取时间",format= "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private Date date;
	private String userId;
	//@Excel(name="(荣誉)行业")
	private String industry;
	//行业名称
	private String industryName;
	
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private Date endDate;

	
	
	public Date getStartDate() {
		return startDate;
	}
	@DateTimeFormat(pattern = "yyyy")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	@DateTimeFormat(pattern = "yyyy")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getInfoval() {
		return this.infoval;
	}
	public void setInfoval(String infoval) {
		this.infoval = infoval;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return this.date;
	}
	@DateTimeFormat(pattern = "yyyy")
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIndustry() {
		return this.industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

}
