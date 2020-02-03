package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友职业经历
 * @Author:       syp
 * @project       simp 2.0   
 * @CreateDate:   Mon Jan 28 15:55:24 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smProfession")
public class SmProfession extends DataEntity<SmProfession> {

	private static final long serialVersionUID = 1548662118742L;
	//@Excel(name="(职业经历)县")
	private String district;
	@Excel(name="(职业经历)省",orderNum = "21",width = 25)
	private String province;
	//@Excel(name="(职业经历)结束时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date endDate;
	//@Excel(name="(职业经历)传真")
	private String fax;
	//@Excel(name="(职业经历)开始时间",format="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8") 
	private Date startDate;
	@Excel(name="(职业经历)市",orderNum = "22",width = 25)
	private String city;
	//@Excel(name="(职业经历)状态")
	private String status;
	@Excel(name="(职业经历)国家",orderNum = "20",width = 25)
	private String country;
	//@Excel(name="(职业经历)电话")
	private String telephone;
	@Excel(name="单位行业",orderNum = "19",width = 25)
	private String industry;
	private String userId;
	//@Excel(name="(职业经历)部门")
	private String department;
	//@Excel(name="(职业经历)县")
	@Excel(name="单位性质",orderNum = "18",needMerge = true,width = 25)
	private String nature;
	@Excel(name="工作单位" ,orderNum = "16",needMerge = true,width = 40)
	private String workplace;
	//@Excel(name="(职业经历)详细地址")
	private String detail;
	@Excel(name="职务",orderNum = "17",needMerge = true,width = 25)
	private String position;


	 //年份日期区间
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private String startYearInternalFirst;
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private String startYearInternalSencond;
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private String endYearInternalFirst;
	@DateTimeFormat(pattern = "yyyy")
	@JsonFormat(pattern = "yyyy",timezone="GMT+8")
	private String endYearInternalSencond;	
	
	//行业名称
	private String industryName;
	
	private String type;
	
	public String getDistrict() {
		return this.district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Date getEndDate() {
		return this.endDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFax() {
		return this.fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelephone() {
		return this.telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIndustry() {
		return this.industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getNature() {
		return this.nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getWorkplace() {
		return this.workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getDetail() {
		return this.detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	 public String getStartYearInternalFirst() {
		 return startYearInternalFirst;
	 }
	 @DateTimeFormat(pattern = "yyyy")
	 public void setStartYearInternalFirst(String startYearInternalFirst) {
		 this.startYearInternalFirst = startYearInternalFirst;
	 }

	 public String getStartYearInternalSencond() {
		 return startYearInternalSencond;
	 }
	 @DateTimeFormat(pattern = "yyyy")
	 public void setStartYearInternalSencond(String startYearInternalSencond) {
		 this.startYearInternalSencond = startYearInternalSencond;
	 }

	 public String getEndYearInternalFirst() {
		 return endYearInternalFirst;
	 }
	 @DateTimeFormat(pattern = "yyyy")
	 public void setEndYearInternalFirst(String endYearInternalFirst) {
		 this.endYearInternalFirst = endYearInternalFirst;
	 }

	 public String getEndYearInternalSencond() {
		 return endYearInternalSencond;
	 }
	 @DateTimeFormat(pattern = "yyyy")
	 public void setEndYearInternalSencond(String endYearInternalSencond) {
		 this.endYearInternalSencond = endYearInternalSencond;
	 }

 }
