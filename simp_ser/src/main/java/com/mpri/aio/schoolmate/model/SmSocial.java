package com.mpri.aio.schoolmate.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友社会兼职
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:55:48 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smSocial")
public class SmSocial extends DataEntity<SmSocial> {

	private static final long serialVersionUID = 1548662142086L;
	//@Excel(name="(社会兼职)名称")
	private String name;
	//@Excel(name="(社会兼职)职务")
	private String position;
	//@Excel(name="(社会兼职)开始时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date startdate;
	//@Excel(name="(社会兼职)结束时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date enddate;
	//@Excel(name="(社会兼职)状态")
	private String status;
	private String userId;
	//@Excel(name="(社会兼职)详细说明")
	private String infoval;


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
	public Date getStartdate() {
		return this.startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return this.enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInfoval() {
		return this.infoval;
	}
	public void setInfoval(String infoval) {
		this.infoval = infoval;
	}

}
