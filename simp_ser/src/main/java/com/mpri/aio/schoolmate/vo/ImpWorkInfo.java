package com.mpri.aio.schoolmate.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ExcelTarget("impWorkInfo")
public class ImpWorkInfo {
	
	@Excel(name="序号",orderNum = "0")
	private String order;
    //姓名
    @NotNull
    @Excel(name = "姓名",orderNum = "1")
    private String name;
    //性别
    @Excel(name = "性别",orderNum = "2")
    private String sex;
    // 证件类型
    @Excel(name = "证件类型",orderNum = "3")
    private String cardType;
    // 证件号
    @Excel(name = "证件号码",orderNum = "4",numFormat="")
    private String cardNum;
    //出生日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="出生日期" ,format="yyyy-MM-dd",orderNum = "5")
    private Date birthday;
    //民族
    @Excel(name = "民族",orderNum = "6")
    private String nation;
    // 政治面貌
    @Excel(name = "政治面貌",orderNum = "7")
    private String politics;
    // 学院
    @Excel(name = "学院",orderNum = "8")
    private String eduCollege;
    //系
    @Excel(name = "系",orderNum = "9")
    private String eduSeries;
    //专业名称
    @Excel(name = "专业",orderNum = "10")
    private String eduSpecialty;
    // 班级
    @Excel(name = "班级",orderNum = "11")
    private String eduClasses;
    // 学号
    @Excel(name = "学号",orderNum = "12")
    private String eduStudentNo;
    // 学历
    @Excel(name = "学历",orderNum = "13")
    private String eduRecord;
    // 学位
    @Excel(name = "学位",orderNum = "14")
    private String eduDegree;
    // 学制
    @Excel(name = "学制",orderNum = "15")
    private String eduSchoolLen;
    // 书院
    @Excel(name = "书院",orderNum = "16")
    private String eduAcademy;
    // 培养方式
    @Excel(name = "培养方式",orderNum = "17")
    private String eduType;
    // 入学时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="入学时间" ,format="yyyy-MM-dd",orderNum = "18")
    private Date eduStartdate;
    // 毕业时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="毕业时间" ,format="yyyy-MM-dd",orderNum = "19")
    private Date eduEnddate;
    // 单位名称
    @Excel(name = "单位名称",orderNum = "20")
    private String proWorkplace;
    // 单位所在地
    @Excel(name = "单位所在地",orderNum = "21")
    private String proDetail;
    // 单位性质
    @Excel(name = "单位性质",orderNum = "22")
    private String nature;
    // 单位行业
    @Excel(name = "单位行业",orderNum = "23")
    private String industry;
    // 手机
    @Excel(name = "手机",orderNum = "24")
    private String phone;
    // 邮箱
    @Excel(name = "EMAIL",orderNum = "25",isHyperlink =false)
    private String email;
    // QQ
    @Excel(name = "qq",orderNum = "26")
    private String qq;
    // 微信
    @Excel(name = "微信",orderNum = "27")
    private String wechat;

    

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getEduSeries() {
		return eduSeries;
	}

	public void setEduSeries(String eduSeries) {
		this.eduSeries = eduSeries;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getEduCollege() {
        return eduCollege;
    }

    public void setEduCollege(String eduCollege) {
        this.eduCollege = eduCollege;
    }

    public String getEduSpecialty() {
        return eduSpecialty;
    }

    public void setEduSpecialty(String eduSpecialty) {
        this.eduSpecialty = eduSpecialty;
    }

    public String getEduClasses() {
        return eduClasses;
    }

    public void setEduClasses(String eduClasses) {
        this.eduClasses = eduClasses;
    }

    public String getEduStudentNo() {
        return eduStudentNo;
    }

    public void setEduStudentNo(String eduStudentNo) {
        this.eduStudentNo = eduStudentNo;
    }

    public String getEduRecord() {
        return eduRecord;
    }

    public void setEduRecord(String eduRecord) {
        this.eduRecord = eduRecord;
    }

    public String getEduDegree() {
        return eduDegree;
    }

    public void setEduDegree(String eduDegree) {
        this.eduDegree = eduDegree;
    }

    public String getEduSchoolLen() {
        return eduSchoolLen;
    }

    public void setEduSchoolLen(String eduSchoolLen) {
        this.eduSchoolLen = eduSchoolLen;
    }

    public String getEduAcademy() {
        return eduAcademy;
    }

    public void setEduAcademy(String eduAcademy) {
        this.eduAcademy = eduAcademy;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }

    public Date getEduStartdate() {
        return eduStartdate;
    }

    public void setEduStartdate(Date eduStartdate) {
        this.eduStartdate = eduStartdate;
    }

    public Date getEduEnddate() {
        return eduEnddate;
    }

    public void setEduEnddate(Date eduEnddate) {
        this.eduEnddate = eduEnddate;
    }

    public String getProWorkplace() {
        return proWorkplace;
    }

    public void setProWorkplace(String proWorkplace) {
        this.proWorkplace = proWorkplace;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
