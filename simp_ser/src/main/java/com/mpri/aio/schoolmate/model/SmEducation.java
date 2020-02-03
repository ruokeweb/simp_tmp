package com.mpri.aio.schoolmate.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

 /**   
 *  
 * @Description:  校友卡教育经历
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Tue Feb 19 15:45:55 CST 2019
 * @Version:      v_2.0
 *    
 */
@ExcelTarget("smEducation")
public class SmEducation extends DataEntity<SmEducation> {

	private static final long serialVersionUID = 1550562334047L;
	//@Excel(name="学科")
	private String degreeType;
	//@Excel(name="临时专业")
	private String tempSpecialty;
	//@Excel(name="教育模式")
	private String eduModel;
	//@Excel(name="学校")
	private String school;
	private String type;
	@Excel(name="学历",orderNum = "7")
	private String eduRecord;
	//@Excel(name="学制")
	private String schoollen;
	private String userId;
	/*书院*/
	@Excel(name="书院名称",orderNum = "13")
	private String academy;
	//@Excel(name="培养方式")
	private String eduType;
	@Excel(name="系",orderNum = "10",width = 25)
	private String series;
	@Excel(name="班级",orderNum = "12")
	private String classes;
	@Excel(name="毕业年份",format="yyyy-MM-dd",orderNum = "5")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date enddate;
	@Excel(name="专业名称",orderNum = "11" ,width = 25)
	private String specialty;
	@Excel(name="学院名称",orderNum = "9",width = 25)
	private String college;
	@Excel(name="入学年份",format="yyyy-MM-dd",orderNum = "5")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date startdate;
	//@Excel(name="学号")
	private String studentNo;
	@Excel(name="学位",orderNum = "8")
	private String degree;
	private String isDefault;
	//@Excel(name="临时系")
	private String tempSeries;
	
	private String departmentId;
	private String departmentName;
	//学科名称
	private String subjectName;
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

	 public String getDegreeType() {
		return this.degreeType;
	}
	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}	
	public String getTempSpecialty() {
		return this.tempSpecialty;
	}
	public void setTempSpecialty(String tempSpecialty) {
		this.tempSpecialty = tempSpecialty;
	}	
	public String getEduModel() {
		return this.eduModel;
	}
	public void setEduModel(String eduModel) {
		this.eduModel = eduModel;
	}	
	public String getSchool() {
		return this.school;
	}
	public void setSchool(String school) {
		this.school = school;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getEduRecord() {
		return this.eduRecord;
	}
	public void setEduRecord(String eduRecord) {
		this.eduRecord = eduRecord;
	}	
	public String getSchoollen() {
		return this.schoollen;
	}
	public void setSchoollen(String schoollen) {
		this.schoollen = schoollen;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getAcademy() {
		return this.academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}	
	public String getEduType() {
		return this.eduType;
	}
	public void setEduType(String eduType) {
		this.eduType = eduType;
	}	
	public String getSeries() {
		return this.series;
	}
	public void setSeries(String series) {
		this.series = series;
	}	
	public String getClasses() {
		return this.classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}	
	public Date getEnddate() {
		return this.enddate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}	
	public String getSpecialty() {
		return this.specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}	
	public String getCollege() {
		return this.college;
	}
	public void setCollege(String college) {
		this.college = college;
	}	
	public Date getStartdate() {
		return this.startdate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}	
	public String getStudentNo() {
		return this.studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}	
	public String getDegree() {
		return this.degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}	
	public String getIsDefault() {
		return this.isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}	
	public String getTempSeries() {
		return this.tempSeries;
	}
	public void setTempSeries(String tempSeries) {
		this.tempSeries = tempSeries;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}	

	
}
