package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友校园经历
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:38:47 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smProfession")
public class SmExperience extends DataEntity<SmExperience> {

	private static final long serialVersionUID = 1548661121779L;
	//@Excel(name="校友经历(组织名称)")
	private String name;
	//@Excel(name="校友经历(职务)")
	private String position;
	//@Excel(name="校友经历(详细说明)")
	private String content;
	//@Excel(name ="校友经历(加入时间)",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;
	private String userId;
	//@Excel(name ="校友经历(退出时间)",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;


	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getEndDate() {
		return this.endDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
