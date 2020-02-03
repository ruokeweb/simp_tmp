package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友政治面貌
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:56:39 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smPolitic")
public class SmPolitics extends DataEntity<SmPolitics> {

	private static final long serialVersionUID = 1548662193773L;
	//@Excel(name="(政治面貌)名称")
	private String name;
	//@Excel(name="(政治面貌)职务")
	private String position;
	//@Excel(name="(政治面貌)加入时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date startdate;
	//@Excel(name="(政治面貌)退出时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date enddate;
	//@Excel(name="(政治面貌)")
	private String type;
	private String userId;
	//@Excel(name="(政治面貌)详细信息")
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return this.enddate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
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
