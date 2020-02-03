package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;


/**
 *
 * @Description:  校友家庭成员
 * @Author:       syp
 * @project       exchange_datasource
 * @CreateDate:   Mon Jan 28 15:35:41 CST 2019
 * @Version:      v_1.0
 *
 */
@ExcelTarget("smFamily")
public class SmFamily extends DataEntity<SmFamily> {

	private static final long serialVersionUID = 1548660935338L;
	//@Excel(name="家庭成员(姓名)")
	private String name;
	//@Excel(name="家庭成员(电话)")
	private String phone;
	//@Excel(name="家庭成员(职位)")
	private String profession;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	//@Excel(name="家庭成员(生日)",format="yyyy-MM-dd")
	private Date birthday;
	//@Excel(name="家庭成员(关系)")
	private String relation;
	//@Excel(name="家庭成员(性别)")
	private String sex;
	private String userId;
	//@Excel(name="家庭成员(是否校友)")
	private String isschool;
	//@Excel(name="家庭成员(工作单位)")
	private String workplace;


	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProfession() {
		return this.profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getRelation() {
		return this.relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsschool() {
		return this.isschool;
	}
	public void setIsschool(String isschool) {
		this.isschool = isschool;
	}
	public String getWorkplace() {
		return this.workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

}
