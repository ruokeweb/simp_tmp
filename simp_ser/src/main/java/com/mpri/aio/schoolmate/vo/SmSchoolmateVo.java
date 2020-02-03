package com.mpri.aio.schoolmate.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

import java.util.ArrayList;
import java.util.List;
@ExcelTarget("smSchoolmateVo")
public class SmSchoolmateVo {
	
	@Excel(name="序号",width = 15,orderNum = "0")
	private int order;
	
	private int pageNo;
	private int pageSize;
	@ExcelEntity
	private SmSchoolmate smSchoolmate;
	private String  informationType;
	private String  fuzzySearchFiled;
	private String  exportFlag;
	//添加导出excel 字段
	@ExcelCollection(name = "学籍信息",orderNum = "4")
	private List<SmEducation> smEducationsList = new ArrayList<>();
	@Excel(name="手机",orderNum = "14",needMerge = true ,width = 30)
	private String photoInfo;
	@Excel(name="邮箱",orderNum = "15",needMerge = true ,width = 60)
	private String emailInfo;
	@Excel(name= "-",orderNum = "22",needMerge = true ,width = 30,isColumnHidden = true)
	private String blackColumn;

	public String getBlackColumn() {
		return blackColumn;
	}

	public void setBlackColumn(String blackColumn) {
		this.blackColumn = blackColumn;
	}
	public List<SmEducation> getSmEducationsList() {
		return smEducationsList;
	}

	public void setSmEducationsList(List<SmEducation> smEducationsList) {
		this.smEducationsList = smEducationsList;
	}

	private List<SmSchoolmate> likeSmschoolmate;
	
	public List<SmSchoolmate> getLikeSmschoolmate() {
		return likeSmschoolmate;
	}
	public void setLikeSmschoolmate(List<SmSchoolmate> likeSmschoolmate) {
		this.likeSmschoolmate = likeSmschoolmate;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public SmSchoolmate getSmSchoolmate() {
		return smSchoolmate;
	}
	public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
		this.smSchoolmate = smSchoolmate;
	}

	public String getInformationType() {
		return informationType;
	}

	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	public String getPhotoInfo() {
		return photoInfo;
	}

	public void setPhotoInfo(String photoInfo) {
		this.photoInfo = photoInfo;
	}

	public String getEmailInfo() {
		return emailInfo;
	}

	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}

	public String getFuzzySearchFiled() {
		return fuzzySearchFiled;
	}

	public void setFuzzySearchFiled(String fuzzySearchFiled) {
		this.fuzzySearchFiled = fuzzySearchFiled;
	}

	public String getExportFlag() {
		return exportFlag;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
