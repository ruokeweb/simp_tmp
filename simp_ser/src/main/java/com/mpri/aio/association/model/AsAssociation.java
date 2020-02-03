package com.mpri.aio.association.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 *
 * @Description:  校友会
 * @Author:       syp
 * @project       sm
 * @CreateDate:   Thu Jan 24 16:49:29 CST 2019
 * @Version:      v_1.0
 *
 */
public class AsAssociation extends DataEntity<AsAssociation> {

	private static final long serialVersionUID = 1548319763660L;

	private String constitution;
	private String logo;
	private String province;
	private String type;
	private String name;
	private String recordFlag;
	private String country;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date nextPeriodsdate;
	private String regionType;
	private String address;
	private String hasBlogflag;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date openDate;
	private String registerFlag;
	private String authorizeFlag;
	private String hasOfficeflag;
	private String operator;
	private String certificateFlag;
	private String hasWebflag;
	private String parentId;
	private String secretary;
	private String chairman;
	private String hasWechatflag;
	private String parentIds;
	private Integer sum;
	private String city;
	private String periods;
	private String summary;
	private SmSchoolmate smSchoolmate;
	private AsAssociation parentAsAssociation;
	private String searchName;
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public AsAssociation getParentAsAssociation() {
		return parentAsAssociation;
	}
	public void setParentAsAssociation(AsAssociation parentAsAssociation) {
		this.parentAsAssociation = parentAsAssociation;
	}

	public SmSchoolmate getSmSchoolmate() {
		return smSchoolmate;
	}

	public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
		this.smSchoolmate = smSchoolmate;
	}

	public String getConstitution() {
		return this.constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getLogo() {
		return this.logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecordFlag() {
		return this.recordFlag;
	}
	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}
	public Date getNextPeriodsdate() {
		return this.nextPeriodsdate;
	}
	public void setNextPeriodsdate(Date nextPeriodsdate) {
		this.nextPeriodsdate = nextPeriodsdate;
	}
	public String getRegionType() {
		return this.regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHasBlogflag() {
		return this.hasBlogflag;
	}
	public void setHasBlogflag(String hasBlogflag) {
		this.hasBlogflag = hasBlogflag;
	}
	public Date getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getRegisterFlag() {
		return this.registerFlag;
	}
	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}
	public String getAuthorizeFlag() {
		return this.authorizeFlag;
	}
	public void setAuthorizeFlag(String authorizeFlag) {
		this.authorizeFlag = authorizeFlag;
	}
	public String getHasOfficeflag() {
		return this.hasOfficeflag;
	}
	public void setHasOfficeflag(String hasOfficeflag) {
		this.hasOfficeflag = hasOfficeflag;
	}
	public String getOperator() {
		return this.operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCertificateFlag() {
		return this.certificateFlag;
	}
	public void setCertificateFlag(String certificateFlag) {
		this.certificateFlag = certificateFlag;
	}
	public String getHasWebflag() {
		return this.hasWebflag;
	}
	public void setHasWebflag(String hasWebflag) {
		this.hasWebflag = hasWebflag;
	}
	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSecretary() {
		return this.secretary;
	}
	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}
	public String getChairman() {
		return this.chairman;
	}
	public void setChairman(String chairman) {
		this.chairman = chairman;
	}
	public String getHasWechatflag() {
		return this.hasWechatflag;
	}
	public void setHasWechatflag(String hasWechatflag) {
		this.hasWechatflag = hasWechatflag;
	}
	public String getParentIds() {
		return this.parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public Integer getSum() {
		if(this.sum==null) {
			this.sum=0;
		}
		return this.sum;
	}
	public void setSum(Integer sum) {

		this.sum = sum;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPeriods() {
		return this.periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getSummary() {
		return this.summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

}
