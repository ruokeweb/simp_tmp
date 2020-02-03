package com.mpri.aio.message.vo;

import com.mpri.aio.message.model.MesGroupCondition;

public class MesGroupConditionVo extends MesGroupCondition{
	
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;

	private String department; //院系专业
	
	private String sex; //性别
	 
	private String type; //校友类型

	private String province; //省份
	private String country; //国家
	private String city; //国家
	private String schoolmatemark; //校友标签名称
	private String startdate; //入学年份
	private String enddate; //毕业年份

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSchoolmatemark() {
		return schoolmatemark;
	}

	public void setSchoolmatemark(String schoolmatemark) {
		this.schoolmatemark = schoolmatemark;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
