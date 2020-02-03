package com.mpri.aio.schoolmate.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.app.pay.model.WeiChatInfo;
import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.system.model.SysUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  
 * @Description:  校友基本信息
 * @Author:       syp
 * @project       exchange_datasource   
 * @CreateDate:   Mon Jan 28 15:29:11 CST 2019
 * @Version:      v_1.0
 *    
 */
@ExcelTarget("smSchoolmate")
public class SmSchoolmate extends DataEntity<SmSchoolmate> {

	private static final long serialVersionUID = 1548660545686L;

	private Integer complete;
	private String markIds;
	private String posLat;
	private String marks;
	private String posLong;
	private BigDecimal donFee;
	/*政治面貌*/
	private String politics;
	@NotNull
	@Excel(name ="姓名",needMerge = true,orderNum = "1")
	private String name;
	@Excel(name ="出生日期",format="yyyy-MM-dd",orderNum = "3",needMerge = true,width = 20)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8" ) 
	private Date birthday;
	private String egName;
	//@Excel(name ="民族")
	private String nation;
	@Excel(name ="性别",orderNum = "2",needMerge = true)
	private String sex;
	private String cardId;
	private String openid;
	private String status;
	//@Excel(name ="证件号")
	private String cardNum;
	private String pinyin;
	private String truePhoto;
	private String cardStatus;
	private String isShow;
	private Integer point;
	//@Excel(name ="校友类型")
	private String type;
	//@Excel(name ="证件类型")
	private String cardType;
	private Integer level;
	private String posCity;
	private String userId;
	//校友会id
	private String orgId; 
	
	//标签
	private List<SmMark> markList;


	private SmEducation smEducation;
	@ExcelEntity 
	private SmContact smContact;
	@ExcelEntity 
	private SmAddress smAddress;
	@ExcelEntity 
	private SmProfession smProfession;
	//@ExcelEntity
	private SmExperience smExperience;
	//@ExcelEntity
	private SmHonor smHonor;
	//@ExcelEntity
	private SmSocial smSocial;
	//@ExcelEntity
	private SmPolitics smPolitic;
	//@ExcelEntity
	private SmHistorydata smHistorydata;
	//@ExcelEntity
	private SmFamily smFamily;
	
	private SysUser sysUser;
	
	private WeiChatInfo weiChatInfo;

	private SmOther smOther;

	private boolean LAY_CHECKED=false;
	
	/**
	 * 登录扩展
	 */
	private SysLoginExpand sysLoginExpand;
	
	/**
	 * 教育经历
	 */
	private List<SmEducation> smEducations = new ArrayList<>();

	/**
	 * 家庭成员
	 */
	private List<SmFamily> smFamilies = new ArrayList<>();

	/**
	 * 联系方式
	 */
	private List<SmContact> smContacts = new ArrayList<>();

	/**
	 * 通讯地址
	 */
	private List<SmAddress> smAddresses = new ArrayList<>();
	
	/**
	 * 校园经历
	 */
	private List<SmExperience> smExperiences = new ArrayList<>();
	
	/**
	 * 荣誉成果
	 */
	private List<SmHonor> smHonors = new ArrayList<>();
	
	/**
	 * 历史数据
	 */
	private List<SmHistorydata> smHistorydatas = new ArrayList<>();

	/**
	 * 其他信息
	 */
	private List<SmOther> smOtherdatas = new ArrayList<>();

	/**
	 * 职业经历
	 */
	private List<SmProfession> smProfessions = new ArrayList<>();
	
	
	/**
	 * 社会兼职
	 */
	private List<SmSocial> smSocials = new ArrayList<>();
	
	/**
	 * 政治面貌
	 */
	private List<SmPolitics>  smPolitics = new ArrayList<>();

	/**
	 * 帮助认证的人
	 */
	private List<SmSchoolmate> proveSchoolmates = new ArrayList<SmSchoolmate>(); 
	
	
	/**
	 * 报表查询时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(pattern = "yyyy-MM")
	public Date chartStartDate;
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(pattern = "yyyy-MM")
	public Date chartEndDate;

	/**
	 * 报表查询时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date chartStartDayDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date chartEndDayDate;
	
	public SmOther getSmOther() {
		return smOther;
	}
	public void setSmOther(SmOther smOther) {
		this.smOther = smOther;
	}
	public List<SmOther> getSmOtherdatas() {
		return smOtherdatas;
	}
	public void setSmOtherdatas(List<SmOther> smOtherdatas) {
		this.smOtherdatas = smOtherdatas;
	}
	public Date getChartStartDate() {
		return chartStartDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM")
	public void setChartStartDate(Date chartStartDate) {
		this.chartStartDate = chartStartDate;
	}
	public Date getChartEndDate() {
		return chartEndDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM")
	public void setChartEndDate(Date chartEndDate) {
		this.chartEndDate = chartEndDate;
	}

	public Date getChartStartDayDate() {
		return chartStartDayDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setChartStartDayDate(Date chartStartDayDate) {
		this.chartStartDayDate = chartStartDayDate;
	}

	public Date getChartEndDayDate() {
		return chartEndDayDate;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setChartEndDayDate(Date chartEndDayDate) {
		this.chartEndDayDate = chartEndDayDate;
	}

	public Integer getComplete() {
		return this.complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public String getMarkIds() {
		return this.markIds;
	}
	public void setMarkIds(String markIds) {
		this.markIds = markIds;
	}
	public String getPosLat() {
		return this.posLat;
	}
	public void setPosLat(String posLat) {
		this.posLat = posLat;
	}
	public String getMarks() {
		return this.marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getPosLong() {
		return this.posLong;
	}
	public void setPosLong(String posLong) {
		this.posLong = posLong;
	}
	public BigDecimal getDonFee() {
		return this.donFee;
	}
	public void setDonFee(BigDecimal donFee) {
		this.donFee = donFee;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd",iso = ISO.DATE)
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEgName() {
		return this.egName;
	}
	public void setEgName(String egName) {
		this.egName = egName;
	}
	public String getNation() {
		return this.nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCardId() {
		return this.cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getOpenid() {
		return this.openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardNum() {
		return this.cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getPinyin() {
		return this.pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getTruePhoto() {
		return this.truePhoto;
	}
	public void setTruePhoto(String truePhoto) {
		this.truePhoto = truePhoto;
	}
	public String getCardStatus() {
		return this.cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getIsShow() {
		return this.isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public Integer getPoint() {
		return this.point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardType() {
		return this.cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Integer getLevel() {
		return this.level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPosCity() {
		return this.posCity;
	}
	public void setPosCity(String posCity) {
		this.posCity = posCity;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<SmEducation> getSmEducations() {
		return smEducations;
	}
	public void setSmEducations(List<SmEducation> smEducations) {
		this.smEducations = smEducations;
	}
	public List<SmFamily> getSmFamilies() {
		return smFamilies;
	}
	public void setSmFamilies(List<SmFamily> smFamilies) {
		this.smFamilies = smFamilies;
	}
	public List<SmContact> getSmContacts() {
		return smContacts;
	}
	public void setSmContacts(List<SmContact> smContacts) {
		this.smContacts = smContacts;
	}
	public List<SmAddress> getSmAddresses() {
		return smAddresses;
	}
	public void setSmAddresses(List<SmAddress> smAddresses) {
		this.smAddresses = smAddresses;
	}
	public List<SmExperience> getSmExperiences() {
		return smExperiences;
	}
	public void setSmExperiences(List<SmExperience> smExperiences) {
		this.smExperiences = smExperiences;
	}
	public List<SmHonor> getSmHonors() {
		return smHonors;
	}
	public void setSmHonors(List<SmHonor> smHonors) {
		this.smHonors = smHonors;
	}
	public List<SmHistorydata> getSmHistorydatas() {
		return smHistorydatas;
	}
	public void setSmHistorydatas(List<SmHistorydata> smHistorydatas) {
		this.smHistorydatas = smHistorydatas;
	}
	public List<SmProfession> getSmProfessions() {
		return smProfessions;
	}
	public void setSmProfessions(List<SmProfession> smProfessions) {
		this.smProfessions = smProfessions;
	}
	public List<SmSocial> getSmSocials() {
		return smSocials;
	}
	public void setSmSocials(List<SmSocial> smSocials) {
		this.smSocials = smSocials;
	}
	public List<SmPolitics> getSmPolitics() {
		return smPolitics;
	}
	public void setSmPolitics(List<SmPolitics> smPolitics) {
		this.smPolitics = smPolitics;
	}
	public SmEducation getSmEducation() {
		return smEducation;
	}
	public void setSmEducation(SmEducation smEducation) {
		this.smEducation = smEducation;
	}
	public SmContact getSmContact() {
		return smContact;
	}
	public void setSmContact(SmContact smContact) {
		this.smContact = smContact;
	}
	public SmAddress getSmAddress() {
		return smAddress;
	}
	public void setSmAddress(SmAddress smAddress) {
		this.smAddress = smAddress;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public SmProfession getSmProfession() {
		return smProfession;
	}
	public void setSmProfession(SmProfession smProfession) {
		this.smProfession = smProfession;
	}
	public SmExperience getSmExperience() {
		return smExperience;
	}
	public void setSmExperience(SmExperience smExperience) {
		this.smExperience = smExperience;
	}
	public SmHonor getSmHonor() {
		return smHonor;
	}
	public void setSmHonor(SmHonor smHonor) {
		this.smHonor = smHonor;
	}
	public SmSocial getSmSocial() {
		return smSocial;
	}
	public void setSmSocial(SmSocial smSocial) {
		this.smSocial = smSocial;
	}
	public SmPolitics getSmPolitic() {
		return smPolitic;
	}
	public void setSmPolitic(SmPolitics smPolitic) {
		this.smPolitic = smPolitic;
	}
	public SmHistorydata getSmHistorydata() {
		return smHistorydata;
	}
	public void setSmHistorydata(SmHistorydata smHistorydata) {
		this.smHistorydata = smHistorydata;
	}
	public SmFamily getSmFamily() {
		return smFamily;
	}
	public void setSmFamily(SmFamily smFamily) {
		this.smFamily = smFamily;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public List<SmMark> getMarkList() {
		return markList;
	}
	public void setMarkList(List<SmMark> markList) {
		this.markList = markList;
	}
	public boolean isLAY_CHECKED() {
		return LAY_CHECKED;
	}
	public void setLAY_CHECKED(boolean lAY_CHECKED) {
		LAY_CHECKED = lAY_CHECKED;
	}
	public List<SmSchoolmate> getProveSchoolmates() {
		return proveSchoolmates;
	}
	public void setProveSchoolmates(List<SmSchoolmate> proveSchoolmates) {
		this.proveSchoolmates = proveSchoolmates;
	}
	public WeiChatInfo getWeiChatInfo() {
		return weiChatInfo;
	}
	public void setWeiChatInfo(WeiChatInfo weiChatInfo) {
		this.weiChatInfo = weiChatInfo;
	}
	public SysLoginExpand getSysLoginExpand() {
		return sysLoginExpand;
	}
	public void setSysLoginExpand(SysLoginExpand sysLoginExpand) {
		this.sysLoginExpand = sysLoginExpand;
	}
	public String getPolitics() {
		return politics;
	}
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	
}
