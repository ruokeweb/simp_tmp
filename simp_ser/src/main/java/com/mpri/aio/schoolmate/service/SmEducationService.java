package com.mpri.aio.schoolmate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmEducationMapper;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.service.SettingDepartmentService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysOrg;
import com.mpri.aio.system.service.SysUserService;

import tk.mybatis.mapper.util.StringUtil;

 /**   
 *  
 * @Description:  校友卡教育经历——Service
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Feb 19 15:45:55 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class SmEducationService extends CrudService<SmEducationMapper, SmEducation>  {
	@Autowired
	private SettingDepartmentService settingDepartmentService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	public void deleteByUserId(SmEducation smEducation) {
		mapper.deleteByUserId(smEducation);
	}
	
	/**
	 *  根据所选机构ID 
	 */
	public List<SettingDepartment> getParentOrgs (String orgId) {
		//
		SettingDepartment department = new SettingDepartment();
		//获取所有机构
		List<SettingDepartment>  allList = settingDepartmentService.loadAllListBy(department);
		department.setId(orgId);
		List<SettingDepartment>  selectDepartments = new ArrayList<SettingDepartment>();

		department = settingDepartmentService.get(department);

		//获取所有父级组
		String [] Ids = department.getParentIds().split(",");
		
		for(int i=0;i<Ids.length;i++) {
			for(SettingDepartment d : allList) {
				if(d.getId().equals(Ids[i])) {
					selectDepartments.add(d);
				}
			}
		}
		selectDepartments.add(department);
		return selectDepartments;
	}
	
	/**
	 * 将机构数据转变为校友教育经历数据
	* <p>Title: saveOrgs</p>  
	* <p>Description: </p>  
	* @param sysOrgs
	* @param smEducation
	* @return
	 */
	public SmEducation saveDepartment(SmEducation smEducation) {
		List<SettingDepartment> departments = getParentOrgs(smEducation.getDepartmentId());
		for (SettingDepartment department : departments) {
			if(GlobalStr.SCHOOL.equals(department.getType())) {
				smEducation.setSchool(department.getId());
			}else if(GlobalStr.BIG_COLLEGE.equals(department.getType())) {
				smEducation.setAcademy(department.getId());
			}else if(GlobalStr.COLLEGE.equals(department.getType())) {
				smEducation.setCollege(department.getId());
			}else if(GlobalStr.DEPARTMENT.equals(department.getType())) {
				smEducation.setSeries(department.getId());
			}else if(GlobalStr.MAJOR.equals(department.getType())) {
				smEducation.setSpecialty(department.getId());
			}else if(GlobalStr.CLASS.equals(department.getType())) {
				smEducation.setClasses(department.getId());
			}
		}
		return this.save(smEducation);
	}
	
	
	public SmEducation saveDepartByLevel(SmEducation smEducation) {
		SmEducation edu = new SmEducation();
		if(StringUtils.isNotEmpty(smEducation.getSchool())) {			
			SettingDepartment dep = this.getDepartLevel(smEducation.getSchool());
			edu = this.setValByLevel(dep, edu);
		}else if(StringUtils.isNotEmpty(smEducation.getCollege())) {
			SettingDepartment dep = this.getDepartLevel(smEducation.getCollege());
			edu = this.setValByLevel(dep, edu);
		}else if(StringUtils.isNotEmpty(smEducation.getSeries())) {
			SettingDepartment dep = this.getDepartLevel(smEducation.getSeries());
			edu = this.setValByLevel(dep, edu);
		}else if(StringUtils.isNotEmpty(smEducation.getSpecialty())) {
			SettingDepartment dep = this.getDepartLevel(smEducation.getSpecialty());
			edu = this.setValByLevel(dep, edu);
		}
		smEducation = this.setDepFrom(edu, smEducation);
		this.save(smEducation);
		return smEducation;
	}
	
	private SmEducation setDepFrom(SmEducation source,SmEducation target) {
		if(StringUtils.isNotEmpty(source.getSchool())) {			
			target.setSchool(source.getSchool());
		}else if(StringUtils.isNotEmpty(source.getCollege())) {
			target.setCollege(source.getCollege());
		}else if(StringUtils.isNotEmpty(source.getSeries())) {
			target.setSeries(source.getSeries());
		}else if(StringUtils.isNotEmpty(source.getSpecialty())) {
			target.setSpecialty(source.getSpecialty());
		}
		return target;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return 级别
	 */
	private SettingDepartment getDepartLevel(String id) {
		Map<String, SettingDepartment> map = (Map<String, SettingDepartment>) redisCacheService.getBaseCache(new SettingDepartment(),
				InitCache.DEPART_BASE_CACHE);
		if(StringUtils.isNotEmpty(id)) {
			SettingDepartment dep = map.get(id);
			return dep;
		}
		return null;
	}
	
	/**
	 * 设置到对应的机构字段
	 */
	private SmEducation setValByLevel(SettingDepartment department,SmEducation smEducation) {
		if(GlobalStr.SCHOOL.equals(department.getType())) {
			smEducation.setSchool(department.getId());
		}else if(GlobalStr.COLLEGE.equals(department.getType())) {
			smEducation.setCollege(department.getId());
		}else if(GlobalStr.DEPARTMENT.equals(department.getType())) {
			smEducation.setSeries(department.getId());
		}else if(GlobalStr.MAJOR.equals(department.getType())) {
			smEducation.setSpecialty(department.getId());
		}
		return smEducation;
	}
	
	/**
	 * 设置机构ＩＤ
	* <p>Title: setDepartmentId</p>  
	* <p>Description: </p>  
	* @param smEducations
	* @return
	 */
	public List<SmEducation> setAllOrgId (List<SmEducation> smEducations){
		for (SmEducation smEducation : smEducations) {
//			if(!StringUtil.isEmpty(smEducation.getClassName())) {
//				smEducation.setDepartmentId(smEducation.getClassName());
//				smEducation.setDepartmentName(getOrgNameById(smEducation.getClassName()));
//			}else 
			if(!StringUtil.isEmpty(smEducation.getSpecialty())) {
				smEducation.setDepartmentId(smEducation.getSpecialty());
				smEducation.setDepartmentName(getOrgNameById(smEducation.getSpecialty()));
			}else if(!StringUtil.isEmpty(smEducation.getSeries())) {
				smEducation.setDepartmentId(smEducation.getSeries());
				smEducation.setDepartmentName(getOrgNameById(smEducation.getSeries()));
			}else if(!StringUtil.isEmpty(smEducation.getCollege())) {
				smEducation.setDepartmentId(smEducation.getCollege());
				smEducation.setDepartmentName(getOrgNameById(smEducation.getCollege()));
			}else if(!StringUtil.isEmpty(smEducation.getAcademy())) {
				smEducation.setDepartmentId(smEducation.getAcademy());
				smEducation.setDepartmentName(getOrgNameById(smEducation.getAcademy()));
			}else if(!StringUtil.isEmpty(smEducation.getSchool())) {
				smEducation.setDepartmentId(smEducation.getSchool());
				smEducation.setDepartmentName(getOrgNameById(smEducation.getSchool()));
			}
		}
		return smEducations;
	}
	
	/**
	 * 获取机构名称
	* <p>Title: setDepartmentName</p>  
	* <p>Description: </p>  
	* @param orgId
	* @return
	 */
	public String getOrgNameById(String orgId) {
		SettingDepartment department = new SettingDepartment();
		department.setId(orgId);
		return settingDepartmentService.get(department).getName();
	}
	
	
//	/**
//	 * 同时同步机构至
//	* <p>Title: syncUserOrg</p>  
//	* <p>Description: </p>  
//	* @param userId
//	* @param smEducation
//	 */
//	public void syncUserOrg(String userId,String userName,SmEducation smEducation) {
//		SysUser sysUser = new SysUser();
//		sysUser.setId(userId);
//		sysUser.setUsername(userName);
//		if(StringUtil.isNotEmpty(smEducation.getSpecialty())) {
//			sysUser.setDepartmentId(smEducation.getSpecialty());
//		}else if(StringUtil.isNotEmpty(smEducation.getSeries())) {
//			sysUser.setDepartmentId(smEducation.getSeries());
//		}else if(StringUtil.isNotEmpty(smEducation.getAcademy())){
//			sysUser.setDepartmentId(smEducation.getAcademy());
//		}else if(StringUtil.isNotEmpty(smEducation.getCollege())) {
//			sysUser.setDepartmentId(smEducation.getCollege());
//		}else if(StringUtil.isNotEmpty(smEducation.getSchool())) {
//			sysUser.setDepartmentId(smEducation.getSchool());
//		}else {
//			sysUser.setDepartmentId(GlobalStr.DEFAULT_ORG);
//		}
//		sysUserService.save(sysUser);
//	}
	
	/**
	 *  设置默认
	* <p>Title: setDefault</p>  
	* <p>Description: </p>  
	* @param userId
	* @param smEducation
	 */
	@Transactional(readOnly= false)
	public void setDefault(SmEducation smEducation){
		smEducation.setIsDefault(GlobalStr.NOT_DEFAULT);
		mapper.clearDefault(smEducation);
		smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
		mapper.setDefault(smEducation);
		//同时同步到用户
//		this.syncUserOrg(userId,userName, smEducation);
	}
	
	
	/**
	 * 获取有效的记录数
	* <p>Title: getCount</p>  
	* <p>Description: </p>  
	* @param smEducation
	* @return
	 */
	public int getCount(SmEducation smEducation) {
		return mapper.getCount(smEducation);
	}

	 /**
	  * 获取 教育经历大于两条的userid
	  * @return
	  */
	 public List<SmEducation> getUserId() {
		 return this.mapper.getUserId();
	 }
 }