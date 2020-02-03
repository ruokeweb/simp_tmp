package com.mpri.aio.schoolmate.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 *
 * @Description:  校友标签管理
 * @Author:       LZQ
 * @project       AIO
 * @CreateDate:   Thu Aug 22 11:05:42 CST 2019
 * @Version:      v_1.0
 *
 */
@ExcelTarget("impNewInfo")
public class ImpNewInfo   {
	
	@Excel(name="序号",orderNum = "0")
	private String order;
	
    //姓名
	@NotNull
	@Excel(name ="姓名",orderNum = "1")
    private String name;
    //性别
	@Excel(name ="性别",orderNum = "2")
    private String sex;
    // 证件类型
	@Excel(name ="证件类型",orderNum = "3")
    private String cardType;
    // 证件号码
	@Excel(name ="证件号码",orderNum = "4")
    private String cardNum;
    //出生日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="出生日期",format="yyyy-MM-dd",orderNum = "5")
    private Date birthday;
    //民族
    @Excel(name ="民族",orderNum = "6")
    private String nation;
    //籍贯
    @Excel(name ="籍贯",orderNum = "7")
    private String nativePlace;
    //学院名称
    @Excel(name ="学院名称",orderNum = "8")
    private String eduCollege;
    
    //专业名称
//    @Excel(name ="专业名称",orderNum = "8")
    private String eduSpecialty;
    // 班级名称
    @Excel(name ="班级名称",orderNum = "9")
    private String eduClasses;
    // 学号
    @Excel(name ="学号",orderNum = "10")
    private String eduStudentNo;
    // 学历
    @Excel(name ="学历",orderNum = "11") 
    private String eduRecord;
    // 学位
    @Excel(name ="学位",orderNum = "12")
    private String eduDegree;
    // 	入学时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" )
    @Excel(name ="入学时间",format="yyyy-MM-dd",orderNum = "13")
    private Date eduStartdate;    // 毕业时间
    // 书院名称
    @Excel(name ="书院名称",orderNum = "14")
    private String eduAcademy;
    // 考生省份
    @Excel(name ="考生省份",orderNum = "15")
    private String province;
    // 毕业中学
    @Excel(name ="毕业中学",orderNum = "16")
    private String middleSchool;
    // 考生家庭地址
    @Excel(name ="考生家庭地址",orderNum = "17")
    private String familyAddress;
    // 考生邮政编码
    @Excel(name ="考生邮政编码",orderNum = "18")
    private String zipcode;
    // 考生联系电话
    @Excel(name ="考生联系电话",orderNum = "19")
    private String phone;
    // 邮箱
    @Excel(name ="邮箱",orderNum = "20",isHyperlink =false)
    private String email;

    
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
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

    public Date getEduStartdate() {
        return eduStartdate;
    }

    public void setEduStartdate(Date eduStartdate) {
        this.eduStartdate = eduStartdate;
    }

    public String getEduAcademy() {
        return eduAcademy;
    }

    public void setEduAcademy(String eduAcademy) {
        this.eduAcademy = eduAcademy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMiddleSchool() {
        return middleSchool;
    }

    public void setMiddleSchool(String middleSchool) {
        this.middleSchool = middleSchool;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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
}
