package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友历史数据表
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:54:51 CST 2019
 * @Version:      v_1.0
 *    
 */

@ExcelTarget("smHistorydata")
public class SmHistorydata extends DataEntity<SmHistorydata> {

	private static final long serialVersionUID = 1548662085636L;
	//@Excel(name="(历史数据)毕业时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date joinDate;
	private String userId;
	//@Excel(name="(历史数据)原始通讯地址")
	private String nativeAdress;
	//@Excel(name="(历史数据)其他学位学历")
	private String otherDegree;
	//@Excel(name="(历史数据)入学前地区单位")
	private String beforeSchoolunit;
	//@Excel(name="(历史数据)其他学校")
	private String otherSchool;
	//@Excel(name="(历史数据)其他院系专业")
	private String otherSpecialty;
	//@Excel(name="(历史数据)毕业时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date degreeDate;
	//@Excel(name="(历史数据)个人爱好")
	private String personLove;
	//@Excel(name="(历史数据)定向委培单位")
	private String trustUnit;


	public Date getJoinDate() {
		return this.joinDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNativeAdress() {
		return this.nativeAdress;
	}
	public void setNativeAdress(String nativeAdress) {
		this.nativeAdress = nativeAdress;
	}
	public String getOtherDegree() {
		return this.otherDegree;
	}
	public void setOtherDegree(String otherDegree) {
		this.otherDegree = otherDegree;
	}
	public String getBeforeSchoolunit() {
		return this.beforeSchoolunit;
	}
	public void setBeforeSchoolunit(String beforeSchoolunit) {
		this.beforeSchoolunit = beforeSchoolunit;
	}
	public String getOtherSchool() {
		return this.otherSchool;
	}
	public void setOtherSchool(String otherSchool) {
		this.otherSchool = otherSchool;
	}
	public String getOtherSpecialty() {
		return this.otherSpecialty;
	}
	public void setOtherSpecialty(String otherSpecialty) {
		this.otherSpecialty = otherSpecialty;
	}
	public Date getDegreeDate() {
		return this.degreeDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setDegreeDate(Date degreeDate) {
		this.degreeDate = degreeDate;
	}
	public String getPersonLove() {
		return this.personLove;
	}
	public void setPersonLove(String personLove) {
		this.personLove = personLove;
	}
	public String getTrustUnit() {
		return this.trustUnit;
	}
	public void setTrustUnit(String trustUnit) {
		this.trustUnit = trustUnit;
	}

}
