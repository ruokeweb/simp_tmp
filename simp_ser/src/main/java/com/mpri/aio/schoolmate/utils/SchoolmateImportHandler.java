package com.mpri.aio.schoolmate.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.vo.ImpNewInfo;
import com.mpri.aio.schoolmate.vo.ImpSchoolmate;
import com.mpri.aio.schoolmate.vo.ImpWorkInfo;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

/**
 * 校友筛选数据处理
 * <p>Title: SchoolmateInfoHander</p>
 * <p>Description: </p>
 * @author syp
 * @date 2018年9月6日
 */
@Service
public class SchoolmateImportHandler {
	@Autowired
	private RedisCacheService redisCacheService;

	private  Map<String,List<SysDict>> dictCache;
//	private  Map<String,SysArea> areaBaseCache;
	private  Map<String, List<SysArea>> areaCache;
//	private  Map<String,SettingDepartment> departBaseCache;
	private  Map<String,SettingSubject> subjectBaseCache;
	private  Map<String,SysIndustry> sysIndustryBaseCache;
	private  Map<String, List<SettingDepartment>> departCache;

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;

	private void InitMaps() {
		dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
//		areaBaseCache= (Map<String,SysArea>)redisCacheService.getBaseCache(new SysArea(), InitCache.AREA_BASE_CACHE);
//		departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		subjectBaseCache= (Map<String,SettingSubject>)redisCacheService.getBaseCache(new SettingSubject(), InitCache.SETTING_BASE_SUBJECT);
		sysIndustryBaseCache= (Map<String,SysIndustry>)redisCacheService.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE);
		departCache  = (Map<String, List<SettingDepartment>>)redisCacheService.getCache(new SettingDepartment(),InitCache.DEPART_CACHE);
		areaCache = (Map<String,List<SysArea>>)redisCacheService.getCache(new SysArea(), InitCache.AREA_CACHE);
	}

	/**
	 *  导入数据转换
	 * <p>Title: ImportHandler</p>
	 * <p>Description: </p>
	 * @param importList
	 * @return
	 */
	public List<SmSchoolmate> covertToObj(List<SmSchoolmate> importList){
		InitMaps();
		for(SmSchoolmate sm : importList) {
			//转换校友基本信息
			sm.setSex(getDictCode(sm.getSex(),GlobalStr.SEX_TYPECODE));
			sm.setType(getDictCode(sm.getType(),GlobalStr.SCHOOLEMATE_TYPE));
			sm.setCardType(getDictCode(sm.getCardType(), GlobalStr.IDCARDTYPE_TYPECODE));
			sm.setNation(getDictCode(sm.getNation(), GlobalStr.NATION));
			//转换校友联系方式
			SmContact smContact = sm.getSmContact();
			if(smContact != null) {
				smContact.setType(getDictCode(smContact.getType(),GlobalStr.CONTACT_TYPE));
			}
			//转换校友教育经历
			SmEducation smEducation = sm.getSmEducation();
			if(smEducation != null) {
				smEducation.setSchool(getDepartIdByDepartName(smEducation.getSchool(),GlobalStr.DEFAULT_ORG));
				smEducation.setCollege(getDepartIdByDepartName(smEducation.getCollege(),smEducation.getSchool()));
				smEducation.setAcademy(getDictCode(smEducation.getAcademy(),GlobalStr.ACADEMY_NAME));
				smEducation.setSeries(getDepartIdByDepartName(smEducation.getSeries(),smEducation.getCollege()));
				smEducation.setSpecialty(getDepartIdByDepartName(smEducation.getSpecialty(),smEducation.getSeries()));
				smEducation.setEduRecord(getDictCode(smEducation.getEduRecord(),GlobalStr.EDU_RECORD));
				smEducation.setDegree(getDictCode(smEducation.getDegree(),GlobalStr.EDU_DEGREE));
				smEducation.setEduModel(getDictCode(smEducation.getEduModel(),GlobalStr.EDU_MODEL));
				smEducation.setEduType(getDictCode(smEducation.getEduType(),GlobalStr.EDU_TYPE));
				smEducation.setSchoollen(getDictCode(smEducation.getSchoollen(),GlobalStr.EDU_SCHOOLEN));
				smEducation.setDegreeType(getSubjectIdBySubjectName(smEducation.getDegreeType()));
			}
			//转换校友职业经历
			SmProfession smProfession = sm.getSmProfession();
			if(smProfession != null) {
				smProfession.setCountry(getAreaIdByAreaName(smProfession.getCountry(),GlobalStr.ROOT_AREA));
				smProfession.setProvince(getAreaIdByAreaName(smProfession.getProvince(),smProfession.getCountry()));
				smProfession.setCity(getAreaIdByAreaName(smProfession.getCity(),smProfession.getProvince()));
				smProfession.setDistrict(getAreaIdByAreaName(smProfession.getDistrict(),smProfession.getCity()));
				smProfession.setNature(getDictCode(smProfession.getNature(),GlobalStr.COMPANY_TPYE));
				//行业
			}
			//转换校友地址
			SmAddress smAddress = sm.getSmAddress();
			if(smAddress != null) {
				smAddress.setCity(getAreaIdByAreaName(smAddress.getCity(),smAddress.getProvince()));
				smAddress.setCountry(getAreaIdByAreaName(smAddress.getCountry(),GlobalStr.ROOT_AREA));
				smAddress.setProvince(getAreaIdByAreaName(smAddress.getProvince(),smAddress.getCountry()));
				smAddress.setDistrict(getAreaIdByAreaName(smAddress.getDistrict(),smAddress.getCity()));
			}

		}
		return importList;
	}

	/**
	 * 校友日常信息导入
	 * @param importList
	 * @return
	 */
	public List<SmSchoolmate> covertToDailyHandler(List<ImpSchoolmate> importList){
		InitMaps();
		List<SmSchoolmate> smSchoolmates = new ArrayList<>();
		for(ImpSchoolmate impSchoolmate : importList) {
			SmSchoolmate smSchoolmate = new SmSchoolmate();
			//姓名	出生日期	证件类型	证件号
			smSchoolmate.setName(impSchoolmate.getName());
			smSchoolmate.setBirthday(impSchoolmate.getBirthday());
			smSchoolmate.setCardType(getDictCode(impSchoolmate.getCardType(), GlobalStr.IDCARDTYPE_TYPECODE));
			smSchoolmate.setCardNum(impSchoolmate.getCardNum());
            smSchoolmate.setType(GlobalStr.DEFAULT_SCHOOLMATE_TYPE);

			//学校	学院	系	专业	班级	学号	入学时间	毕业时间	学历	学位
			List<SmEducation> edulist = new ArrayList<SmEducation>();
			SmEducation smEducation = new SmEducation();
			//判斷教育经历是否为空
			if(StringUtil.isNotEmpty(impSchoolmate.getEduSchool()) || StringUtil.isNotEmpty(impSchoolmate.getEduCollege()) || StringUtil.isNotEmpty(impSchoolmate.getEduSeries())
					|| StringUtil.isNotEmpty(impSchoolmate.getEduSpecialty()) || StringUtil.isNotEmpty(impSchoolmate.getEduClasses())
					|| StringUtil.isNotEmpty(impSchoolmate.getEduStudentNo()) || null != impSchoolmate.getEduStartdate()
					|| null != impSchoolmate.getEduEnddate() || StringUtil.isNotEmpty(impSchoolmate.getEduDegree()) 
					|| StringUtil.isNotEmpty(impSchoolmate.getEduRecord())){

				smEducation.setSchool(getDepartIdByDepartName(impSchoolmate.getEduSchool(),GlobalStr.DEFAULT_ORG));
				smEducation.setCollege(getDepartIdByDepartName(impSchoolmate.getEduCollege(),smEducation.getSchool()));
				smEducation.setSeries(getDepartIdByDepartName(impSchoolmate.getEduSeries(),smEducation.getCollege()));
				smEducation.setSpecialty(getDepartIdByDepartName(impSchoolmate.getEduSpecialty(),smEducation.getSeries()));
				smEducation.setClasses(impSchoolmate.getEduClasses());
				smEducation.setStudentNo(impSchoolmate.getEduStudentNo());
				smEducation.setStartdate(impSchoolmate.getEduStartdate());
				smEducation.setEnddate(impSchoolmate.getEduEnddate());
				smEducation.setDegree(getDictCode(impSchoolmate.getEduDegree(),GlobalStr.EDU_DEGREE));
				smEducation.setEduRecord(getDictCode(impSchoolmate.getEduRecord(),GlobalStr.EDU_RECORD));
				edulist.add(smEducation);
			}
//			}

			//单位名称	职务	任职状态
			List<SmProfession> proList = new ArrayList<SmProfession>();
			if(StringUtil.isNotEmpty(impSchoolmate.getProWorkplace()) || 
					StringUtil.isNotEmpty(impSchoolmate.getProPosition()) || StringUtil.isNotEmpty(impSchoolmate.getProStatus())){
				SmProfession smProfession = new SmProfession();
				smProfession.setWorkplace(impSchoolmate.getProWorkplace());
				smProfession.setStatus(getDictCode(impSchoolmate.getProStatus(),GlobalStr.PROFESSION_STATUS));
				smProfession.setPosition(impSchoolmate.getProPosition());
				proList.add(smProfession);
			}

			//详细通讯地址
			List<SmAddress> addList = new ArrayList<SmAddress>();
			if(StringUtil.isNotEmpty(impSchoolmate.getAddress())){
				SmAddress smAddress = new SmAddress();
				smAddress = this.decodeAddress(impSchoolmate.getAddress(), smAddress);
                smAddress.setType(GlobalStr.IS_FIRSTADDRESS);	
				addList.add(smAddress);
			}

			List<SmContact> smContacts = new ArrayList<>();
			//手机	邮箱	QQ	微信
			if (StringUtil.isNotEmpty(impSchoolmate.getPhone()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_PHONE);
				smContact.setContact(impSchoolmate.getPhone());
				smContacts.add(smContact);
			}
			if (StringUtil.isNotEmpty(impSchoolmate.getWechat()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_WECHAT);
				smContact.setContact(impSchoolmate.getWechat());
				smContacts.add(smContact);
			}
			if (StringUtil.isNotEmpty(impSchoolmate.getEmail()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
				smContact.setContact(impSchoolmate.getEmail());
				smContacts.add(smContact);
			}
			if (StringUtil.isNotEmpty(impSchoolmate.getQq()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_QQ);
				smContact.setContact(impSchoolmate.getQq());
				smContacts.add(smContact);
			}
			smSchoolmate.setSmEducations(edulist);
			smSchoolmate.setSmAddresses(addList);
			smSchoolmate.setSmProfessions(proList);
			smSchoolmate.setSmContacts(smContacts);
			smSchoolmates.add(smSchoolmate);
		} 
		return smSchoolmates;
	}

	/**
	 * 校友新生信息导入
	 * @param importList
	 * @return 
	 */
	public List<SmSchoolmate> covertToNewpupilHandler(List<ImpNewInfo> importList){
		InitMaps();
		List<SmSchoolmate> smSchoolmates = new ArrayList<>();
		for(ImpNewInfo impNewInfo : importList) {
			SmSchoolmate smSchoolmate = new SmSchoolmate();
			//姓名	性别	证件类型	证件号码	出生日期	民族
			smSchoolmate.setName(impNewInfo.getName());
			smSchoolmate.setSex(getDictCode(impNewInfo.getSex(),GlobalStr.SEX_TYPECODE));
			smSchoolmate.setBirthday(impNewInfo.getBirthday());
			smSchoolmate.setCardType(getDictCode(impNewInfo.getCardType(), GlobalStr.IDCARDTYPE_TYPECODE));
			smSchoolmate.setCardNum(impNewInfo.getCardNum());
			smSchoolmate.setNation (getDictCode(impNewInfo.getNation(),GlobalStr.NATION));
            smSchoolmate.setType(GlobalStr.STUDENT_SCHOOLMATE_TYPE);
			//籍贯
			List<SmAddress> addList = new ArrayList<SmAddress>();
			if(!StringUtil.isEmpty(impNewInfo.getNativePlace())){
				SmAddress smAddress = new SmAddress();
				smAddress = this.decodeAddress(impNewInfo.getNativePlace(), smAddress);
				smAddress.setType(GlobalStr.IS_NATION_PLACE);
				addList.add(smAddress);
			}

			//学院名称	专业名称	班级名称	学号	学历	学位	入学时间	书院名称
			List<SmEducation>  edulist = new ArrayList<SmEducation>();
			SmEducation  smEducation= new SmEducation();
			if(StringUtil.isNotEmpty(impNewInfo.getEduCollege()) || StringUtil.isNotEmpty(impNewInfo.getEduClasses()) || StringUtil.isNotEmpty(impNewInfo.getEduStudentNo())
					|| StringUtil.isNotEmpty(impNewInfo.getEduDegree()) || StringUtil.isNotEmpty(impNewInfo.getEduRecord())
					|| null != impNewInfo.getEduStartdate() || StringUtil.isNotEmpty(impNewInfo.getEduAcademy())) {
				smEducation.setSchool(getDepartIdByDepartName(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.school_name),
						GlobalStr.DEFAULT_ORG));
				smEducation.setCollege(getDepartIdByDepartName(impNewInfo.getEduCollege(),smEducation.getSchool()));
	//			smEducation.setSpecialty(getDepartIdByDepartName(impNewInfo.getEduSpecialty()));
				smEducation.setClasses(impNewInfo.getEduClasses());
				smEducation.setStudentNo(impNewInfo.getEduStudentNo());
				smEducation.setDegree(getDictCode(impNewInfo.getEduDegree(),GlobalStr.EDU_DEGREE));
				smEducation.setEduRecord(getDictCode(impNewInfo.getEduRecord(),GlobalStr.EDU_RECORD));
				smEducation.setStartdate(impNewInfo.getEduStartdate());
				smEducation.setAcademy(getDictCode(impNewInfo.getEduAcademy(),GlobalStr.ACADEMY_NAME));
				edulist.add(smEducation);
			}	
			
			//考生省份	毕业中学 考生家庭地址	考生邮政编码
			if(StringUtil.isNotEmpty(impNewInfo.getProvince()) || StringUtil.isNotEmpty(impNewInfo.getMiddleSchool())
					|| StringUtil.isNotEmpty(impNewInfo.getFamilyAddress())|| StringUtil.isNotEmpty(impNewInfo.getZipcode())){
				SmAddress smAddress = new SmAddress();
				smAddress.setCountry(getAreaIdByAreaName(GlobalStr.IMPORT_DEF_COUNTRY,GlobalStr.ROOT_AREA));
				smAddress.setDetail((impNewInfo.getFamilyAddress()));
				smAddress.setRemark(impNewInfo.getMiddleSchool());
				smAddress.setProvince(getAreaIdByAreaName(impNewInfo.getProvince(),smAddress.getCountry()));
				smAddress.setZipcode(impNewInfo.getZipcode());
				smAddress.setType(GlobalStr.IS_NATIVEADDRESS);
				addList.add(smAddress);
			}


			//考生联系电话	邮箱
			List<SmContact> smContacts = new ArrayList<>();
			if (!StringUtil.isEmpty(impNewInfo.getPhone())){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_PHONE);
				smContact.setContact(impNewInfo.getPhone());
				smContacts.add(smContact);
			}
			if ( !StringUtil.isEmpty(impNewInfo.getEmail()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
				smContact.setContact(impNewInfo.getEmail());
				smContacts.add(smContact);
			}

			smSchoolmate.setSmEducations(edulist);
			smSchoolmate.setSmAddresses(addList);
			smSchoolmate.setSmContacts(smContacts);
			smSchoolmates.add(smSchoolmate);
		}
		return smSchoolmates;
	}

	/**
	 * 校友  work信息导入
	 * @param importList
	 * @return
	 */
	public List<SmSchoolmate> covertToEmployHandler(List<ImpWorkInfo> importList){
		InitMaps();
		List<SmSchoolmate> smSchoolmates = new ArrayList<>();
		for(ImpWorkInfo impWorkInfo : importList) {
			SmSchoolmate smSchoolmate = new SmSchoolmate();
			//姓名	性别	证件类型	证件号码	出生日期	民族	政治面貌
			smSchoolmate.setName(impWorkInfo.getName());
			smSchoolmate.setSex(getDictCode(impWorkInfo.getSex(),GlobalStr.SEX_TYPECODE));
			smSchoolmate.setCardType(getDictCode(impWorkInfo.getCardType(), GlobalStr.IDCARDTYPE_TYPECODE));
			smSchoolmate.setCardNum(impWorkInfo.getCardNum());
			smSchoolmate.setBirthday(impWorkInfo.getBirthday());
			smSchoolmate.setNation (getDictCode(impWorkInfo.getNation(),GlobalStr.NATION));
			smSchoolmate.setPolitics(getDictCode(impWorkInfo.getPolitics(),GlobalStr.POLITICS_NAME));
            smSchoolmate.setType(GlobalStr.DEFAULT_SCHOOLMATE_TYPE);
            
			//学院	专业	班级	学号	学历	学位	学制	书院	培养方式	入学时间	毕业时间
			List<SmEducation> edulist = new ArrayList<SmEducation>();
			SmEducation smEducation = new SmEducation();
			if(StringUtil.isNotEmpty(impWorkInfo.getEduCollege()) || StringUtil.isNotEmpty(impWorkInfo.getEduSeries()) || StringUtil.isNotEmpty(impWorkInfo.getEduSpecialty())
					|| StringUtil.isNotEmpty(impWorkInfo.getEduClasses()) || StringUtil.isNotEmpty(impWorkInfo.getEduStudentNo())
					|| StringUtil.isNotEmpty(impWorkInfo.getEduDegree()) || StringUtil.isNotEmpty(impWorkInfo.getEduRecord())
					|| StringUtil.isNotEmpty(impWorkInfo.getEduSchoolLen()) || StringUtil.isNotEmpty(impWorkInfo.getEduAcademy())
					|| StringUtil.isNotEmpty(impWorkInfo.getEduType()) || null != impWorkInfo.getEduStartdate()
					|| null != impWorkInfo.getEduEnddate()) {
				smEducation.setSchool(getDepartIdByDepartName(
						runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.school_name),GlobalStr.DEFAULT_ORG));
				smEducation.setCollege(getDepartIdByDepartName(impWorkInfo.getEduCollege(),smEducation.getSchool()));
				smEducation.setSeries(getDepartIdByDepartName(impWorkInfo.getEduSeries(),smEducation.getCollege()));
				smEducation.setSpecialty(getDepartIdByDepartName(impWorkInfo.getEduSpecialty(),smEducation.getSeries()));
				smEducation.setClasses(impWorkInfo.getEduClasses());
				smEducation.setStudentNo(impWorkInfo.getEduStudentNo());
				smEducation.setDegree(getDictCode(impWorkInfo.getEduDegree(),GlobalStr.EDU_DEGREE));
				smEducation.setEduRecord(getDictCode(impWorkInfo.getEduRecord(),GlobalStr.EDU_RECORD));
				smEducation.setSchoollen(getDictCode(impWorkInfo.getEduSchoolLen(),GlobalStr.EDU_SCHOOLEN));
				smEducation.setAcademy(getDictCode(impWorkInfo.getEduAcademy(),GlobalStr.ACADEMY_NAME));
				smEducation.setEduType(getDictCode(impWorkInfo.getEduType(),GlobalStr.EDU_TYPE));
				smEducation.setStartdate(impWorkInfo.getEduStartdate());
				smEducation.setEnddate(impWorkInfo.getEduEnddate());
				edulist.add(smEducation);
			}

			//单位名称	单位所在地	单位性质	单位行业
			List<SmProfession> proList = new ArrayList<SmProfession>();
			if(StringUtil.isNotEmpty(impWorkInfo.getProWorkplace()) || StringUtil.isNotEmpty(impWorkInfo.getIndustry())
					|| StringUtil.isNotEmpty(impWorkInfo.getProDetail()) || StringUtil.isNotEmpty(impWorkInfo.getNature())){
				SmProfession smProfession = new SmProfession();
				smProfession.setWorkplace(impWorkInfo.getProWorkplace());
				smProfession.setDetail(impWorkInfo.getProDetail());
				smProfession.setIndustry(getIndustryIdByIndustryName(impWorkInfo.getIndustry()));
				smProfession.setNature(getDictCode(impWorkInfo.getNature(),GlobalStr.COMPANY_TPYE)); 
				proList.add(smProfession);
			}

			List<SmContact> smContacts = new ArrayList<>();
			//手机	EMAIL	qq	微信
			if ( !StringUtil.isEmpty(impWorkInfo.getPhone()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_PHONE);
				smContact.setContact(impWorkInfo.getPhone());
				smContacts.add(smContact);
			}
			if ( !StringUtil.isEmpty(impWorkInfo.getEmail()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
			 	smContact.setContact(impWorkInfo.getEmail());
				smContacts.add(smContact);
			}
			if ( !StringUtil.isEmpty(impWorkInfo.getQq()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_QQ);
				smContact.setContact(impWorkInfo.getQq());
				smContacts.add(smContact);
			} 
			if ( !StringUtil.isEmpty(impWorkInfo.getWechat()) ){
				SmContact smContact = new SmContact();
				smContact.setType(GlobalStr.CONTACT_TYPE_WECHAT);
				smContact.setContact(impWorkInfo.getWechat());
				smContacts.add(smContact);
			}

			smSchoolmate.setSmEducations(edulist);
			smSchoolmate.setSmContacts(smContacts);
			smSchoolmate.setSmProfessions(proList);
			smSchoolmates.add(smSchoolmate);
		}
		return smSchoolmates;
	}

	private String getDictCode(String label,String typeCode) {
		if(!StringUtil.isEmpty(label)) {
			List<SysDict> sysDicts  = dictCache.get(typeCode);
			for(SysDict dict : sysDicts) {
				if(label.equals(dict.getLabel())) {
					return dict.getValue();
				}
			}
		}
		return "";
	}

	private String getAreaIdByAreaName(String name,String parentId) {		
		if(!StringUtil.isEmpty(parentId) && !StringUtil.isEmpty(name)) {
			List<SysArea> list = areaCache.get(parentId);
			for (SysArea area : list) {
				if(name.equals(area.getName())){
					return area.getId();
				}
			}
		}else if(!StringUtil.isEmpty(name)) {
			for (Map.Entry<String, List<SysArea>> entry : areaCache.entrySet()) {
				List<SysArea> list = entry.getValue();
				for (SysArea area : list) {
					if(name.equals(area.getName())){
						return area.getId();
					}
				}
			}
		}
		return "";
	}

	private String getDepartIdByDepartName(String name,String parentId) {
		if(!StringUtil.isEmpty(parentId) && !StringUtil.isEmpty(name)) {
			List<SettingDepartment> list = departCache.get(parentId);
			for (SettingDepartment settingDepartment : list) {
				if(name.equals(settingDepartment.getName())){
					return settingDepartment.getId();
				}
			}
		}
//		else if(!StringUtil.isEmpty(name)) {
//			for (Map.Entry<String, List<SettingDepartment>> entry : departCache.entrySet()) {
//				List<SettingDepartment> list = entry.getValue();
//				for (SettingDepartment settingDepartment : list) {
//					if(name.equals(settingDepartment.getName())){
//						return settingDepartment.getId();
//					}
//				}
//			}
//		}
		return "";
	}

	private String getSubjectIdBySubjectName(String name) {
		if(!StringUtil.isEmpty(name)) {
			for (Map.Entry<String, SettingSubject> entry : subjectBaseCache.entrySet()) {
				if(name.equals(entry.getValue().getName())){
					return entry.getValue().getId();
				}
			}
		}
		return "";
	}
	
	private String getIndustryIdByIndustryName(String name) {
		if(!StringUtil.isEmpty(name)) {
			for (Map.Entry<String, SysIndustry> entry : sysIndustryBaseCache.entrySet()) {
				if(name.equals(entry.getValue().getName())){
					return entry.getValue().getId();
				}
			}
		}
		return "";
	}
	/**
	 * 处理地址
	 * @param address
	 * @return
	 */
	private SmAddress decodeAddress(String address,SmAddress smAddress) {
		//目前只处理中国
		smAddress.setCountry(GlobalStr.IMPORT_DEF_COUNTRY);
		if(StringUtil.isNotEmpty(address)) {
			//拆分
			String[] addrs  = address.split("/");
			if(addrs.length > 0) {
				if(addrs.length == 4) {
					smAddress.setProvince(addrs[0]);
					smAddress.setCity(addrs[1]);
					smAddress.setDistrict(addrs[2]);
					smAddress.setDetail(addrs[0]+ addrs[1]+ addrs[2] +addrs[3]);
				}else if(addrs.length == 3) {
					smAddress.setProvince(addrs[0]);
					smAddress.setCity(addrs[1]);
					smAddress.setDistrict(addrs[2]);
				}else if(addrs.length == 2) {
					smAddress.setProvince(addrs[0]);
					smAddress.setCity(addrs[1]);
				}else {
					smAddress.setDetail(address);
				}
			}else {
				smAddress.setProvince(address);
			}
		}
		smAddress.setCountry(getAreaIdByAreaName(smAddress.getCountry(),GlobalStr.ROOT_AREA));
		smAddress.setProvince(getAreaIdByAreaName(smAddress.getProvince(),smAddress.getCountry()));
		smAddress.setCity(getAreaIdByAreaName(smAddress.getCity(),smAddress.getProvince()));
		smAddress.setDistrict(getAreaIdByAreaName(smAddress.getDistrict(),smAddress.getCity()));
		return smAddress;
	}
	
	public static void main(String[] args) {
//		SmAddress smAddress = new SmAddress();
//		smAddress = 		decodeAddress("陕西省西安市雁锦业路10号",smAddress);
//		System.err.println(smAddress);
	}
}
