package com.mpri.aio.message.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.mapper.MesGroupMapper;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.model.MesGroupCondition;
import com.mpri.aio.message.vo.MesGroupConditionEnum;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.common.GlobalStr;

import tk.mybatis.mapper.util.StringUtil;

 /**   
 *  
 * @Description:  信息群组——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:33:03 CST 2018
 * @Version:      v_1.02
 *    
 */
@Service
public class MesGroupService extends CrudService<MesGroupMapper, MesGroup>  {

	@Autowired
	private MesGroupConditionService conditionService;
	
	
	public List<MesGroup> loadConditionListBy(MesGroup mesGroup){
		return mapper.loadConditionListBy(mesGroup);
	}
	
	
	/**
	 * 获取消息群组缓存
	* <p>Title: getMesGroupInfo</p>  
	* <p>Description: </p>  
	* @return
	 */
	public List<MesGroup> getMesGroupInfo() {
		MesGroup mesGroup = new MesGroup();
		MesGroupCondition mesGroupCondition = new MesGroupCondition();
		List<MesGroup> groups = this.loadAllListBy(mesGroup);
		List<MesGroupCondition> conditions = conditionService.loadAllListBy(mesGroupCondition);
		
		groups.forEach((MesGroup mg) -> {
			conditions.forEach((MesGroupCondition mgc) ->{  
				if(mg.getId().equals(mgc.getGroupId())) {
					mg.getMesGroupConditions().add(mgc);
				}
			});
		});		
		
		return groups;
	}
	
	
	/**
	 * 查看是某个组的成员
	 */
	public List<MesGroup> gerMesGroupBy(SmSchoolmate smSchoolmate,List<MesGroup> mesGroupsCache) {
		List<MesGroup> resMeGroups = new ArrayList<MesGroup>();
		for (MesGroup mesgroup : mesGroupsCache) {
			List<MesGroupCondition> conditions = mesgroup.getMesGroupConditions();
			Boolean orgFlag = true; //机构判断
			Boolean sexFlag = true; //性别判断
			//Boolean typeFlag = true; //校友类型判断
			//新增群组判断条件
			Boolean provinceFlag = true;
			Boolean countryFlag = true;
			Boolean cityFlag = true;
			Boolean schoolMateMarkFlag = true;
			Boolean startdateFlag = true;
			Boolean enddateFlag = true;
			for (MesGroupCondition condition : conditions) {
				//判断机构
				if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.DEPARTMENT.getCode())) {
					orgFlag = this.judgeOrg(smSchoolmate, condition.getValue());
				//判断校友类型
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.SEX.getCode())) {
					sexFlag = this.judgeSex(smSchoolmate, condition.getValue());
				//判断性别
				}/*else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.TYPE.getCode())) {
					typeFlag = this.judgeType(smSchoolmate, condition.getValue());
					//新增判断条件
					//判断省份
				}*/ else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.CITY.getCode())) {
					cityFlag = this.judgeCity(smSchoolmate, condition.getValue());
					//校友标签
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.PROVINCE.getCode())) {
					provinceFlag = this.judgeProvince(smSchoolmate, condition.getValue());
					//判断国家
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.COUNTRY.getCode())) {
					countryFlag = this.judgeCountry(smSchoolmate, condition.getValue());
					//校友标签
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.SCHOOLMATEMARK.getCode())) {
					schoolMateMarkFlag = this.judgeSchollMateMark(smSchoolmate, condition.getValue());
					//判断入学年份
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.STARTDATE.getCode())) {
					startdateFlag = this.judgeStartdate(smSchoolmate, condition.getValue());
					//判断毕业年份
				}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.ENDDATE.getCode())) {
					enddateFlag = this.judgeEnddate(smSchoolmate, condition.getValue());
				 }
			}
			//判断是否属于这个组
			if(orgFlag && sexFlag && provinceFlag && countryFlag && schoolMateMarkFlag && startdateFlag && enddateFlag ) {
				resMeGroups.add(mesgroup);
			}
		}
		return resMeGroups;
	}


	 /**
	  * 判断机构
	  * 2019-02-11校友项目更新改版
	  */
	 private Boolean judgeOrg(SmSchoolmate smSchoolmate,String departmentId) {
		 List<SmEducation> educations = smSchoolmate.getSmEducations();
		 if(educations.size() == 0 && GlobalStr.DEFAULT_ORG.equals(departmentId)) {
			 return true;
		 }else{
			 for (SmEducation smEducation : educations) {
				 if(StringUtil.isNotEmpty(smEducation.getSpecialty()) && departmentId.equals(smEducation.getSpecialty())) {
					 return true;
				 }else if(StringUtil.isNotEmpty(smEducation.getSeries()) && departmentId.equals(smEducation.getSeries())) {
					 return true;
				 }else if(StringUtil.isNotEmpty(smEducation.getCollege()) && departmentId.equals(smEducation.getCollege())) {
					 return true;
				 }else if(StringUtil.isNotEmpty(smEducation.getSchool()) && departmentId.equals(smEducation.getSchool())) {
					 return true;
				 }
			 }
		 }
		 return false;
	 }

	/**
	 * 判断性别
	 */
	public Boolean judgeSex(SmSchoolmate smSchoolmate,String sex) {
		if(StringUtil.isNotEmpty(smSchoolmate.getSex()) && sex.equals(smSchoolmate.getSex())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断校友类型
	 */
	public Boolean judgeType(SmSchoolmate smSchoolmate,String type) {
		if(StringUtil.isNotEmpty(smSchoolmate.getType()) && type.equals(smSchoolmate.getType())) {
			return true;
		}
		return false;
	}

	 private Boolean judgeEnddate(SmSchoolmate smSchoolmate, String value) {
		 List<SmEducation> smEducations = smSchoolmate.getSmEducations();
		 if (smEducations != null && smEducations.size() > 0) {
			 for (SmEducation smEducation : smEducations) {
				 Calendar calendar = Calendar.getInstance();
				 String[] datas = value.split("-");
				 if (datas!=null && datas.length == 2 && smEducation.getEnddate() != null) {
					 calendar.setTime(smEducation.getEnddate());
					 return (calendar.get(Calendar.YEAR) >= Integer.valueOf(datas[0].trim())) && (calendar.get(Calendar.YEAR) <= Integer.valueOf(datas[1].trim()));
				 }
				 return false;
			 }
		 }
		 return false;
	 }

	 //日期区间判断
	 private Boolean judgeStartdate(SmSchoolmate smSchoolmate, String value) {
		 List<SmEducation> smEducations = smSchoolmate.getSmEducations();
		 if (smEducations != null && smEducations.size() > 0) {
			 for (SmEducation smEducation : smEducations) {
				 Calendar calendar = Calendar.getInstance();
				 String[] datas = value.split("-");
				 if (datas!=null && datas.length == 2 && smEducation.getStartdate() != null) {
					 try {
						 calendar.setTime(smEducation.getStartdate());
						 return (calendar.get(Calendar.YEAR) >= Integer.valueOf(datas[0].trim())) && (calendar.get(Calendar.YEAR) <= Integer.valueOf(datas[1].trim()));
					 } catch (Exception e) {
						 return false;
					 }
				 }
				 return false;			 }
		 }
		 return false;
	 }

	 private Boolean judgeSchollMateMark(SmSchoolmate smSchoolmate, String value) {
		 if(StringUtil.isNotEmpty(smSchoolmate.getMarkIds()) && value.equals(smSchoolmate.getMarkIds())) {
			 return true;
		 }
		 return false;
	 }


	 private Boolean judgeCountry(SmSchoolmate smSchoolmate, String value) {
		 List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		 if (smProfessions!=null && smProfessions.size()>0) {
			 for (SmProfession smProfession : smProfessions) {
				 if(StringUtil.isNotEmpty(smProfession.getCountry()) && value.equals(smProfession.getCountry())) {
					return true;
				}
			 }
		 }
		return false;
	 }

	 private Boolean judgeProvince(SmSchoolmate smSchoolmate, String value) {
		 List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		 if (smProfessions!=null && smProfessions.size()>0) {
			 for (SmProfession smProfession : smProfessions) {
				 if(StringUtil.isNotEmpty(smProfession.getProvince()) && value.equals(smProfession.getProvince())) {
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 private Boolean judgeCity(SmSchoolmate smSchoolmate, String value) {
		 List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		 if (smProfessions!=null && smProfessions.size()>0) {
			 for (SmProfession smProfession : smProfessions) {
				 if(StringUtil.isNotEmpty(smProfession.getCity()) && value.equals(smProfession.getCity())) {
					 return true;
				 }
			 }
		 }
		 return false;
	 }



}