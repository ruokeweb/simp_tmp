package com.mpri.aio.license.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

 /**   
 *  
 * @Description:  认证
 * @Author:       daihongbo
 * @project       simp   
 * @CreateDate:   Mon Sep 16 19:24:31 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class SysLicense extends DataEntity<SysLicense> {

	private static final long serialVersionUID = 1568633013464L;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
	private Date expireDate;
	private String schoolName;
	private String licensePub;
	private String licensePri;
	private String licenseType;

	
	public Date getExpireDate() {
		return this.expireDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}	
	public String getSchoolName() {
		return this.schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}	
	public String getLicensePub() {
		return this.licensePub;
	}
	public void setLicensePub(String licensePub) {
		this.licensePub = licensePub;
	}	
	public String getLicensePri() {
		return this.licensePri;
	}
	public void setLicensePri(String licensePri) {
		this.licensePri = licensePri;
	}	
	public String getLicenseType() {
		return this.licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
 }
