package com.mpri.aio.settings.vo;


 /**   
 *  
 * @Description:  系和专业合并实体类
 * @Author:       zdl
 * @project       sm   
 * @CreateDate:   2019/04/18
 * @Version:      v_1.0
 *    
 */
public class  DepartmentMerge{
	private String id;
	
	private String schoolName;//学校名称
	
	private String schoolId;//学校id
	
	private String collegeName;//学院名称
	
	private String collegeId;//学院id
	
	private String seriesName;//系名称
	
	private String seriesId;//系id
	
	private String tempSeries;//备用系名称
	
	private String specialtyName;//专业名称
	
	private String specialtyId;//专业id
	
	private String tempSpecialty;//备用专业
	
	private int num;// 机构下的人数
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public String getTempSeries() {
		return tempSeries;
	}

	public void setTempSeries(String tempSeries) {
		this.tempSeries = tempSeries;
	}

	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}

	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}

	public String getTempSpecialty() {
		return tempSpecialty;
	}

	public void setTempSpecialty(String tempSpecialty) {
		this.tempSpecialty = tempSpecialty;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	

}
