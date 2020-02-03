package com.mpri.aio.schoolmate.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ExcelTarget("impSchoolmate")
public class ImpSchoolmate {
	
	@Excel(name="序号",orderNum = "0")
	private String order;
	
    //姓名
    @NotNull
    @Excel(name ="姓名",orderNum = "1")
    private String name;
    //出生日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="出生日期" ,format="yyyy-MM-dd",orderNum = "2")
    private Date birthday;
    // 证件类型
    @Excel(name ="证件类型",orderNum = "3")
    private String cardType;
    // 证件号
    @Excel(name ="证件号",orderNum = "4",numFormat="")
    private String cardNum;
    // 学校
    @Excel(name ="学校",orderNum = "5")
    private String eduSchool;
    // 学院
    @Excel(name ="学院",orderNum = "6")
    private String eduCollege;
    // 系
    @Excel(name ="系",orderNum = "7")
    private String eduSeries;
    // 专业
    @Excel(name ="专业",orderNum = "8")
    private String eduSpecialty;
    // 班级
    @Excel(name ="班级",orderNum = "9")
    private String eduClasses;
    // 学号
    @Excel(name ="学号",orderNum = "10")
    private String eduStudentNo;
    // 入学时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="入学时间" ,format="yyyy-MM-dd",orderNum = "11")
    private Date eduStartdate;
    // 毕业时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="毕业时间" ,format="yyyy-MM-dd",orderNum = "12")
    private Date eduEnddate;
    // 学历
    @Excel(name ="学历",orderNum = "13")
    private String eduRecord;
    // 学位
    @Excel(name ="学位",orderNum = "14")
    private String eduDegree;
    // 手机
    @Excel(name ="手机",orderNum = "15")
    private String phone;
    // 邮箱
    @Excel(name ="邮箱",orderNum = "16",isHyperlink =false)
    private String email;
    // QQ
    @Excel(name ="QQ",orderNum = "17")
    private String qq;
    // 微信
    @Excel(name ="微信",orderNum = "18")
    private String wechat;
    // 单位名称
    @Excel(name ="单位名称",orderNum = "19")
    private String proWorkplace;
    // 职务
    @Excel(name ="职务",orderNum = "20")
    private String proPosition;
    // 任职状态
    @Excel(name ="任职状态",orderNum = "21")
    private String proStatus;
    // 详细通讯地址
    @Excel(name ="详细通讯地址",orderNum = "22")
    private String address;

    
    
    public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEduSchool() {
        return eduSchool;
    }

    public void setEduSchool(String eduSchool) {
        this.eduSchool = eduSchool;
    }

    public String getEduCollege() {
        return eduCollege;
    }

    public void setEduCollege(String eduCollege) {
        this.eduCollege = eduCollege;
    }

    public String getEduSeries() {
        return eduSeries;
    }

    public void setEduSeries(String eduSeries) {
        this.eduSeries = eduSeries;
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

    public String getProWorkplace() {
        return proWorkplace;
    }

    public void setProWorkplace(String proWorkplace) {
        this.proWorkplace = proWorkplace;
    }

    public String getProPosition() {
        return proPosition;
    }

    public void setProPosition(String proPosition) {
        this.proPosition = proPosition;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
