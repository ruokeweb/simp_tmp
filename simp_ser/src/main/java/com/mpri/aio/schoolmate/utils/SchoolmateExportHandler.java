
package com.mpri.aio.schoolmate.utils;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.schoolmate.model.*;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.vo.SmSchoolmateVo;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.model.SysIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.mpri.aio.schoolmate.model.SmAddress;
//import com.mpri.aio.schoolmate.model.SmContact;
//import com.mpri.aio.schoolmate.model.SmEducation;
//import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * 校友导出数据处理
* <p>Title: SchoolmateImportHandler</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年9月6日
 */
@Service
public class SchoolmateExportHandler{

	
	@Autowired 
	private RedisCacheService redisCacheService;

	
	private Map<String,List<SysDict>> dictCache;
	private Map<String,SysArea> areaBaseCache;
	private Map<String,SettingDepartment> departBaseCache;
	private Map<String,SettingSubject> subjectBaseCache;
	private Map<String,SysIndustry> industryBaseCache;
	
	
	private void InitMaps() {		
		dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		areaBaseCache= (Map<String,SysArea>)redisCacheService.getBaseCache(new SysArea(), InitCache.AREA_BASE_CACHE);
		departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		subjectBaseCache= (Map<String,SettingSubject>)redisCacheService.getBaseCache(new SettingSubject(), InitCache.SETTING_BASE_SUBJECT);		
		industryBaseCache= (Map<String,SysIndustry>)redisCacheService.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE); 
	}
	
	/**
	 * 导出数据转换
	* <p>Title: smhander</p>  
	* <p>Description: </p>  
	* @param list
	* @return
	 */
	public List<SmSchoolmate> handlerExport(List<SmSchoolmate> list) {
		this.InitMaps();
		for (SmSchoolmate smSchoolmate : list) {
			//转换校友基本信息
			smSchoolmate.setSex(setFormatDictValue(smSchoolmate.getSex(),GlobalStr.SEX_TYPECODE));
			smSchoolmate.setType(setFormatDictValue(smSchoolmate.getType(),GlobalStr.SCHOOLEMATE_TYPE));
			smSchoolmate.setCardType(setFormatDictValue(smSchoolmate.getCardType(),GlobalStr.IDCARDTYPE_TYPECODE));
			smSchoolmate.setNation(setFormatDictValue(smSchoolmate.getNation(),GlobalStr.NATION));
			//转换校友地址
			SmAddress smAddress = smSchoolmate.getSmAddress();
			if(smAddress != null) {
	            smAddress.setCity(setFormatAreaValue(smAddress.getCity()));
	            smAddress.setCountry(setFormatAreaValue(smAddress.getCountry()));
	            smAddress.setProvince(setFormatAreaValue(smAddress.getProvince()));
	            smAddress.setDistrict(setFormatAreaValue(smAddress.getDistrict()));
			}
			
			//转换校友联系方式
			SmContact smContact = smSchoolmate.getSmContact();
			if(smContact != null) {
	            smContact.setType(setFormatDictValue(smContact.getType(),GlobalStr.CONTACT_TYPE));			    
			}

//			//转换校友教育经历
			SmEducation smEducation = smSchoolmate.getSmEducation();
			if(smEducation != null) {
	            smEducation.setSchool(setFormatDepartValue(smEducation.getSchool()));
	            smEducation.setCollege(setFormatDepartValue(smEducation.getCollege()));
	            smEducation.setAcademy(setFormatDepartValue(smEducation.getAcademy()));
	            smEducation.setSeries(setFormatDepartValue(smEducation.getSeries()));
	            smEducation.setSpecialty(setFormatDepartValue(smEducation.getSpecialty()));
	            smEducation.setEduRecord(setFormatDictValue(smEducation.getEduRecord(),GlobalStr.EDU_RECORD));
	            smEducation.setDegree(setFormatDictValue(smEducation.getDegree(),GlobalStr.EDU_DEGREE));
	            smEducation.setEduModel(setFormatDictValue(smEducation.getEduModel(),GlobalStr.EDU_MODEL));
	            smEducation.setEduType(setFormatDictValue(smEducation.getEduType(),GlobalStr.EDU_TYPE));
	            smEducation.setSchoollen(setFormatDictValue(smEducation.getSchoollen(),GlobalStr.EDU_SCHOOLEN));
	            smEducation.setDegreeType(setFormatSubjectValue(smEducation.getDegreeType()));        
			}
			//转换校友政治面貌
			SmPolitics smPolitics = smSchoolmate.getSmPolitic();
			if(smPolitics != null) {
				smPolitics.setName(setFormatDictValue(smPolitics.getName(),GlobalStr.POLITICS_NAME));
				smPolitics.setType(setFormatDictValue(smPolitics.getType(),GlobalStr.POLITICS_TYPE));
			}
			//转换校友家庭成员
			SmFamily smFamily = smSchoolmate.getSmFamily();
			if(smFamily != null) {
				smFamily.setRelation(setFormatDictValue(smFamily.getRelation(),GlobalStr.FAMLIY_RELATION));			
				smFamily.setIsschool(setFormatDictValue(smFamily.getIsschool(),GlobalStr.BOOLEAN_TYPE));
				smFamily.setSex(setFormatDictValue(smFamily.getSex(),GlobalStr.SEX_TYPECODE));
			}
			//转换校友职业经历
			SmProfession smProfession = smSchoolmate.getSmProfession(); 
			if(smProfession != null) {
				smProfession.setCountry(setFormatAreaValue(smProfession.getCountry()));
				smProfession.setProvince(setFormatAreaValue(smProfession.getProvince()));
				smProfession.setCity(setFormatAreaValue(smProfession.getCity()));
				smProfession.setDistrict(setFormatAreaValue(smProfession.getDistrict()));
				smProfession.setNature(setFormatDictValue(smProfession.getNature(),GlobalStr.COMPANY_TPYE));
				//行业
				smProfession.setIndustry(setIndustryValue(smProfession.getIndustry()));
				smProfession.setStatus(setFormatDictValue(smProfession.getStatus(),GlobalStr.POLITICS_TYPE));
			}
			//转换校友历史数据
			
			//转换校友荣誉成果
			SmHonor smHonor = smSchoolmate.getSmHonor();
			if(smHonor != null) {
				smHonor.setType(setFormatDictValue(smHonor.getType(),GlobalStr.HONOR_TYPE));
				//行业
				smHonor.setIndustry(setIndustryValue(smHonor.getIndustry()));
			}
			//转换社会兼职
			SmSocial smSocial = smSchoolmate.getSmSocial();
			if(smSocial != null ) {
				smSocial.setStatus(setFormatDictValue(smSocial.getStatus(),GlobalStr.POLITICS_TYPE));
			}
		}
		return list;
	}
	
	/**
	 * 转换个人信息
	 * @return
	 */
	public SmSchoolmate handlerSmInfo(SmSchoolmate smSchoolmate) {
		this.InitMaps();
		//转换校友基本信息
		smSchoolmate.setSex(setFormatDictValue(smSchoolmate.getSex(),GlobalStr.SEX_TYPECODE));
		smSchoolmate.setType(setFormatDictValue(smSchoolmate.getType(),GlobalStr.SCHOOLEMATE_TYPE));
		smSchoolmate.setCardType(setFormatDictValue(smSchoolmate.getCardType(),GlobalStr.IDCARDTYPE_TYPECODE));
		smSchoolmate.setNation(setFormatDictValue(smSchoolmate.getNation(),GlobalStr.NATION));
		//转换校友地址
		List<SmAddress> smAddresses = smSchoolmate.getSmAddresses();
		for (SmAddress smAddress : smAddresses) {
            smAddress.setCity(setFormatAreaValue(smAddress.getCity()));
            smAddress.setCountry(setFormatAreaValue(smAddress.getCountry()));
            smAddress.setProvince(setFormatAreaValue(smAddress.getProvince()));
            smAddress.setDistrict(setFormatAreaValue(smAddress.getDistrict()));
		}		
		//转换校友联系方式
		List<SmContact> smContacts = smSchoolmate.getSmContacts();
		for (SmContact smContact : smContacts) {
			smContact.setType(setFormatDictValue(smContact.getType(),GlobalStr.CONTACT_TYPE));			    
		}

//			//转换校友教育经历
		List<SmEducation> smEducations = smSchoolmate.getSmEducations();
		for (SmEducation smEducation : smEducations) {
            smEducation.setSchool(setFormatDepartValue(smEducation.getSchool()));
            smEducation.setCollege(setFormatDepartValue(smEducation.getCollege()));
            smEducation.setAcademy(setFormatDepartValue(smEducation.getAcademy()));
            smEducation.setSeries(setFormatDepartValue(smEducation.getSeries()));
            smEducation.setSpecialty(setFormatDepartValue(smEducation.getSpecialty()));
            smEducation.setEduRecord(setFormatDictValue(smEducation.getEduRecord(),GlobalStr.EDU_RECORD));
            smEducation.setDegree(setFormatDictValue(smEducation.getDegree(),GlobalStr.EDU_DEGREE));
            smEducation.setEduModel(setFormatDictValue(smEducation.getEduModel(),GlobalStr.EDU_MODEL));
            smEducation.setEduType(setFormatDictValue(smEducation.getEduType(),GlobalStr.EDU_TYPE));
            smEducation.setSchoollen(setFormatDictValue(smEducation.getSchoollen(),GlobalStr.EDU_SCHOOLEN));
            smEducation.setDegreeType(setFormatSubjectValue(smEducation.getDegreeType()));  			
		}

		//转换校友政治面貌
		List<SmPolitics> smPolitics = smSchoolmate.getSmPolitics();
		for (SmPolitics smPolitic : smPolitics) {
			smPolitic.setName(setFormatDictValue(smPolitic.getName(),GlobalStr.POLITICS_NAME));
			smPolitic.setType(setFormatDictValue(smPolitic.getType(),GlobalStr.POLITICS_TYPE));			
		}

		//转换校友家庭成员
		List<SmFamily> smFamilies = smSchoolmate.getSmFamilies();
		for (SmFamily smFamily : smFamilies) {
			smFamily.setRelation(setFormatDictValue(smFamily.getRelation(),GlobalStr.FAMLIY_RELATION));			
			smFamily.setIsschool(setFormatDictValue(smFamily.getIsschool(),GlobalStr.BOOLEAN_TYPE));
			smFamily.setSex(setFormatDictValue(smFamily.getSex(),GlobalStr.SEX_TYPECODE));			
		}

		//转换校友职业经历
		List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		for (SmProfession smProfession : smProfessions) {
			smProfession.setCountry(setFormatAreaValue(smProfession.getCountry()));
			smProfession.setProvince(setFormatAreaValue(smProfession.getProvince()));
			smProfession.setCity(setFormatAreaValue(smProfession.getCity()));
			smProfession.setDistrict(setFormatAreaValue(smProfession.getDistrict()));
			smProfession.setNature(setFormatDictValue(smProfession.getNature(),GlobalStr.COMPANY_TPYE));
			//行业
			smProfession.setIndustry(setIndustryValue(smProfession.getIndustry()));
			smProfession.setStatus(setFormatDictValue(smProfession.getStatus(),GlobalStr.POLITICS_TYPE));			
		}

		//转换校友历史数据
		
		//转换校友荣誉成果
		List<SmHonor> smHonors = smSchoolmate.getSmHonors();
		for (SmHonor smHonor : smHonors) {
			smHonor.setType(setFormatDictValue(smHonor.getType(),GlobalStr.HONOR_TYPE));
			//行业
			smHonor.setIndustry(setIndustryValue(smHonor.getIndustry()));			
		}

		//转换社会兼职
		List<SmSocial> smSocials = smSchoolmate.getSmSocials();
		for (SmSocial smSocial : smSocials) {
			smSocial.setStatus(setFormatDictValue(smSocial.getStatus(),GlobalStr.POLITICS_TYPE));			
		}
		return smSchoolmate;
	}	
	
	private String setFormatDictValue(String value,String typeCode) {
		if(!StringUtil.isEmpty(value)) {
			//key: redis内key的规则：type:value
			List<SysDict> sds=(List<SysDict>)dictCache.get(typeCode);
			for(SysDict dict : sds) {
				if(value.equals(dict.getValue())) {
					return dict.getLabel();
				}
			}
		}
		return "";
	}

	/**
	 * 转换校友信息
	 */
	public SmSchoolmate handlerSchoolMateInfo(SmSchoolmate smSchoolmate) {
		this.InitMaps();
		//转换校友基本信息
		smSchoolmate.setSex(setFormatDictValue(smSchoolmate.getSex(),GlobalStr.SEX_TYPECODE));
		smSchoolmate.setType(setFormatDictValue(smSchoolmate.getType(),GlobalStr.SCHOOLEMATE_TYPE));
		smSchoolmate.setCardType(setFormatDictValue(smSchoolmate.getCardType(),GlobalStr.IDCARDTYPE_TYPECODE));
		smSchoolmate.setNation(setFormatDictValue(smSchoolmate.getNation(),GlobalStr.NATION));
		//转换校友地址
		List<SmAddress> smAddresses = smSchoolmate.getSmAddresses();
		for (SmAddress smAddress : smAddresses) {
			smAddress.setCity(setFormatAreaValue(smAddress.getCity()));
			smAddress.setCountry(setFormatAreaValue(smAddress.getCountry()));
			smAddress.setProvince(setFormatAreaValue(smAddress.getProvince()));
			smAddress.setDistrict(setFormatAreaValue(smAddress.getDistrict()));
		}
		//转换校友联系方式
		List<SmContact> smContacts = smSchoolmate.getSmContacts();
		for (SmContact smContact : smContacts) {
			smContact.setType(setFormatDictValue(smContact.getType(),GlobalStr.CONTACT_TYPE));
		}

//			//转换校友教育经历
		List<SmEducation> smEducations = smSchoolmate.getSmEducations();
		for (SmEducation smEducation : smEducations) {
			smEducation.setSchool(setFormatDepartValue(smEducation.getSchool()));
			smEducation.setCollege(setFormatDepartValue(smEducation.getCollege()));
			smEducation.setAcademy(setFormatDepartValue(smEducation.getAcademy()));
			smEducation.setSeries(setFormatDepartValue(smEducation.getSeries()));
			smEducation.setSpecialty(setFormatDepartValue(smEducation.getSpecialty()));
			smEducation.setEduRecord(setFormatDictValue(smEducation.getEduRecord(),GlobalStr.EDU_RECORD));
			smEducation.setDegree(setFormatDictValue(smEducation.getDegree(),GlobalStr.EDU_DEGREE));
			smEducation.setEduModel(setFormatDictValue(smEducation.getEduModel(),GlobalStr.EDU_MODEL));
			smEducation.setEduType(setFormatDictValue(smEducation.getEduType(),GlobalStr.EDU_TYPE));
			smEducation.setSchoollen(setFormatDictValue(smEducation.getSchoollen(),GlobalStr.EDU_SCHOOLEN));
			smEducation.setDegreeType(setFormatSubjectValue(smEducation.getDegreeType()));
		}

		//转换校友职业经历
		List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		for (SmProfession smProfession : smProfessions) {
			smProfession.setCountry(setFormatAreaValue(smProfession.getCountry()));
			smProfession.setProvince(setFormatAreaValue(smProfession.getProvince()));
			smProfession.setCity(setFormatAreaValue(smProfession.getCity()));
			smProfession.setDistrict(setFormatAreaValue(smProfession.getDistrict()));
			smProfession.setNature(setFormatDictValue(smProfession.getNature(),GlobalStr.COMPANY_TPYE));
			//行业
			smProfession.setIndustry(setIndustryValue(smProfession.getIndustry()));
			smProfession.setStatus(setFormatDictValue(smProfession.getStatus(),GlobalStr.POLITICS_TYPE));
		}

		return smSchoolmate;
	}

	private String setFormatDepartValue(String value) {
		if(!StringUtil.isEmpty(value)) {
			SettingDepartment sd = departBaseCache.get(value);
			if(null != sd) {
				return sd.getName();
			}
		}
		return "";
	}

	private String setFormatSubjectValue(String value) {
		if(!StringUtil.isEmpty(value)) {
			SettingSubject ss = subjectBaseCache.get(value);
			if(null != ss) {
				return ss.getName();
			}
		}
		return "";
	}
	
	private String setFormatAreaValue(String value) {
		if(!StringUtil.isEmpty(value)) {
			SysArea sa=(SysArea)areaBaseCache.get(value);
			if(null != sa) {
				return sa.getName();
			}
		}
		return "";
	}
	
	private String setIndustryValue(String value) {
		if(!StringUtil.isEmpty(value)) {
			SysIndustry industry=(SysIndustry)industryBaseCache.get(value);
			return industry.getName();
		}
		return "";		
	}

	/**
	 * 导出数据转换
	 * <p>Title: smhander</p>
	 * <p>Description: </p>
	 * @param list
	 * @return
	 */
	public List<SmSchoolmateVo> handlerSchoolmateExport(List<SmSchoolmate> list) {
		List<SmSchoolmateVo> smSchoolmateVolist =new ArrayList<>();
		this.InitMaps();
		for (SmSchoolmate smSchoolmate : list) {
			SmSchoolmateVo smSchoolmateVo = new SmSchoolmateVo();

			//转换校友基本信息
			smSchoolmate.setSex(setFormatDictValue(smSchoolmate.getSex(),GlobalStr.SEX_TYPECODE));
			smSchoolmate.setType(setFormatDictValue(smSchoolmate.getType(),GlobalStr.SCHOOLEMATE_TYPE));
			smSchoolmate.setCardType(setFormatDictValue(smSchoolmate.getCardType(),GlobalStr.IDCARDTYPE_TYPECODE));
			smSchoolmate.setNation(setFormatDictValue(smSchoolmate.getNation(),GlobalStr.NATION));

			List<SmContact> smContacts =smSchoolmate.getSmContacts();
			String phoneinfo = "";
			String emailinfo = "";
			if (smContacts != null && smContacts.size() > 0) {
				for (int i = 0; i < smContacts.size(); i++) {
					if ("PHONE".equals(smContacts.get(i).getType())) {
						if ("".equals(phoneinfo.trim())) {
							phoneinfo += smContacts.get(i).getContact();
						} else {
							phoneinfo = phoneinfo + ", "+smContacts.get(i).getContact();
						}
					}
				}
			}
			if (smContacts != null && smContacts.size() > 0) {
				for (int j = 0; j < smContacts.size(); j++) {
					if ("EMAIL".equals(smContacts.get(j).getType())) {
						if ("".equals(emailinfo.trim())) {
							emailinfo += smContacts.get(j).getContact();
						} else {
							emailinfo = emailinfo+ ", "+ smContacts.get(j).getContact() ;
						}
					}
				}
			}
			smSchoolmateVo.setPhotoInfo(phoneinfo);
			smSchoolmateVo.setEmailInfo(emailinfo);

			//转换校友地址
			SmAddress smAddress = smSchoolmate.getSmAddress();
			if(smAddress != null) {
				smAddress.setCity(setFormatAreaValue(smAddress.getCity()));
				smAddress.setCountry(setFormatAreaValue(smAddress.getCountry()));
				smAddress.setProvince(setFormatAreaValue(smAddress.getProvince()));
				smAddress.setDistrict(setFormatAreaValue(smAddress.getDistrict()));
				smAddress.setDetail(smAddress.getDetail());
			}


		//转换校友教育经历
			List<SmEducation> smEducations = smSchoolmate.getSmEducations();
			if ((smEducations != null && smEducations.size() > 0)) {
				smEducations.forEach(smEducation -> {
					smEducation.setSchool(setFormatDepartValue(smEducation.getSchool()));
					smEducation.setCollege(setFormatDepartValue(smEducation.getCollege()));
					smEducation.setSeries(setFormatDepartValue(smEducation.getSeries()));
					smEducation.setSpecialty(setFormatDepartValue(smEducation.getSpecialty()));
					smEducation.setEduRecord(setFormatDictValue(smEducation.getEduRecord(), GlobalStr.EDU_RECORD));
					smEducation.setDegree(setFormatDictValue(smEducation.getDegree(), GlobalStr.EDU_DEGREE));
					smEducation.setEduModel(setFormatDictValue(smEducation.getEduModel(), GlobalStr.EDU_MODEL));
					smEducation.setEduType(setFormatDictValue(smEducation.getEduType(), GlobalStr.EDU_TYPE));
					smEducation.setSchoollen(setFormatDictValue(smEducation.getSchoollen(), GlobalStr.EDU_SCHOOLEN));
					smEducation.setDegreeType(setFormatSubjectValue(smEducation.getDegreeType()));
					smEducation.setAcademy(setFormatDictValue(smEducation.getAcademy(),  GlobalStr.ACADEMY_NAME));
				});
			}

			//转换校友职业经历
			SmProfession smProfession = smSchoolmate.getSmProfession();
			if(smProfession != null) {
				smProfession.setCountry(setFormatAreaValue(smProfession.getCountry()));
				smProfession.setProvince(setFormatAreaValue(smProfession.getProvince()));
				smProfession.setCity(setFormatAreaValue(smProfession.getCity()));
				smProfession.setDistrict(setFormatAreaValue(smProfession.getDistrict()));
				smProfession.setNature(setFormatDictValue(smProfession.getNature(),GlobalStr.COMPANY_TPYE));
				//行业
				smProfession.setIndustry(setIndustryValue(smProfession.getIndustry()));
				smProfession.setStatus(setFormatDictValue(smProfession.getStatus(),GlobalStr.POLITICS_TYPE));
			}
 			smSchoolmateVo.setSmEducationsList(smEducations);
			smSchoolmateVo.setSmSchoolmate(smSchoolmate);
			smSchoolmateVo.setBlackColumn("");
			smSchoolmateVo.setOrder(list.indexOf(smSchoolmate)+1);
			smSchoolmateVolist.add(smSchoolmateVo);

		}
		return smSchoolmateVolist;
	}

}
