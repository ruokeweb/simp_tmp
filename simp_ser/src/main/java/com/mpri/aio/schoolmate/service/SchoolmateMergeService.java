package com.mpri.aio.schoolmate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.schoolmate.mapper.SmSchoolmateMapper;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmFamily;
import com.mpri.aio.schoolmate.model.SmHisEducation;
import com.mpri.aio.schoolmate.model.SmHistorydata;
import com.mpri.aio.schoolmate.model.SmHonor;
import com.mpri.aio.schoolmate.model.SmOtherInfo;
import com.mpri.aio.schoolmate.model.SmPolitics;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.model.SmSocial;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;

/**
 * 校友合并
 * 
 * @author Administrator
 *
 */

@Service
public class SchoolmateMergeService {

	@Autowired
	private SmSchoolmateMapper schoolmateMapper;
	@Autowired
	private SmAddressService smAddressService;
	@Autowired
	private SmContactService smContactService;
	@Autowired
	private SmEducationService smEducationService;
	@Autowired
	private SmFamilyService smFamilyService;
	@Autowired
	private SmHistorydataService smHistorydataService;
	@Autowired
	private SmPoliticsService smPoliticsService;
	@Autowired
	private SmProfessionService smProfessionService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SmHonorService smHonorService;
	@Autowired
	private SmSocialService smSocialService;
	@Autowired
	private SmOtherInfoService smOtherInfoService;
	@Autowired
	private SmHisEducationService smHisEducationService;
	
	@Autowired 
	private ActivitiUtils activitiUtils;

	/**
	 * 将 老校友合并到新校友
	 */
	@Transactional(readOnly = false)
	public void mergeSchoolmateInfo(String oldUserId, String newUserId,String processInstanceId) {
		//更新校友信息
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId(oldUserId);
		SmSchoolmate oldSchoomate = schoolmateMapper.loadAllListBy(schoolmate).get(0);
		schoolmate.setUserId(newUserId);
		SmSchoolmate newSchoomate = schoolmateMapper.loadAllListBy(schoolmate).get(0);

		//基本信息处理
		this.mergeSmInfo(oldSchoomate, newSchoomate);
		// 更新联系方式
		this.mergeSmContact(oldUserId, newUserId);
		// 更新地址方式
		this.mergeSmAddress(oldUserId, newUserId);
		// 更新教育经历
		this.mergeSmEducation(oldUserId, newUserId);
		// 更新职业方式
		this.mergeSmProfession(oldUserId, newUserId);
		// 更新历史数据
		this.mergeSmHistory(oldUserId, newUserId);
		// 家庭成员
		this.mergeSmFamily(oldUserId, newUserId);
		// 更新校友信息
		this.mergeSchoolmate(oldUserId);
		// 更新用户信息
		this.mergeSysUser(oldUserId);
		
		//更新工作流
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("res", GlobalStr.ACTIVITI_PASS);
		activitiUtils.setVarible(map, processInstanceId, GlobalStr.SUPER_ADMIN);
	}

	
	@Transactional(readOnly = false)
	public void mergeSchoolmateInfo(String oldUserId, String newUserId) {
		//更新校友信息
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId(oldUserId);
		SmSchoolmate oldSchoomate = schoolmateMapper.loadAllListBy(schoolmate).get(0);
		schoolmate.setUserId(newUserId);
		SmSchoolmate newSchoomate = schoolmateMapper.loadAllListBy(schoolmate).get(0);

		//基本信息处理
		this.mergeSmInfo(oldSchoomate, newSchoomate);
		// 更新联系方式
		this.mergeSmContact(oldUserId, newUserId);
		// 更新地址方式
		this.mergeSmAddress(oldUserId, newUserId);
		// 更新教育经历
		this.mergeSmEducation(oldUserId, newUserId);
		// 更新职业方式
		this.mergeSmProfession(oldUserId, newUserId);
		// 更新历史数据
		this.mergeSmHistory(oldUserId, newUserId);
		// 荣誉成功
		this.mergeSmHonors(oldUserId, newUserId);
		// 社会兼职
		this.mergeSmSocial(oldUserId, newUserId);
		// 其他教育经历
		this.mergeSmHisEducation(oldUserId, newUserId);
		// 其他信息
		this.mergeSmOtherInfos(oldUserId, newUserId);
		// 更新校友信息
		this.mergeSchoolmate(oldUserId);
		// 更新用户信息
		this.mergeSysUser(oldUserId);
		
	}
	
	/**
	 * 合并校友
	 */
	@Transactional(readOnly = false)
	public void mergeSchoomates(List<SmSchoolmate> branchSm,SmSchoolmate mainSm) {
		for (SmSchoolmate schoolmate : branchSm) {
			//基本信息处理
			this.mergeSmInfo(schoolmate, mainSm);
			// 更新联系方式
			this.mergeSmContact(schoolmate.getUserId(), mainSm.getUserId());
			// 更新地址方式
			this.mergeSmAddress(schoolmate.getUserId(), mainSm.getUserId());
			// 更新教育经历
			this.mergeSmEducation(schoolmate.getUserId(), mainSm.getUserId());
			// 更新职业方式
			this.mergeSmProfession(schoolmate.getUserId(), mainSm.getUserId());
			// 更新历史数据
			this.mergeSmHistory(schoolmate.getUserId(),mainSm.getUserId());
			// 删除校友信息
			this.mergeSchoolmate(schoolmate.getUserId());
			// 删除用户信息
			this.mergeSysUser(schoolmate.getUserId());
		}
	}
	
	/**
	 *更新校友主信息
	 */
	@Transactional(readOnly = false)
	public SmSchoolmate mergeSmInfo(SmSchoolmate branchSm,SmSchoolmate mainSm) {
		if(StringUtil.isNotEmpty(branchSm.getNation())) { //民族
			mainSm.setNation(branchSm.getNation());
		}
		if(null != branchSm.getBirthday()) { //生日
			mainSm.setBirthday(branchSm.getBirthday());
		}
		if(StringUtil.isNotEmpty(branchSm.getCardType())) { //证件类型
			mainSm.setCardType(branchSm.getCardType());
		}
		if(StringUtil.isNotEmpty(branchSm.getCardNum())) { //证件号码
			mainSm.setCardNum(branchSm.getCardNum());
		}
		if(StringUtil.isNotEmpty(branchSm.getOpenid())) { //Openid
			mainSm.setOpenid(branchSm.getOpenid());
		}
		if(StringUtil.isNotEmpty(branchSm.getSex())) { //性别
			mainSm.setSex(branchSm.getSex());
		}
		if(StringUtil.isNotEmpty(branchSm.getEgName())) { //英文
			mainSm.setEgName(branchSm.getEgName());
		}
		if(StringUtil.isNotEmpty(branchSm.getPinyin())) { //拼音
			mainSm.setPinyin(branchSm.getPinyin());
		}
		if((StringUtil.isNotEmpty(branchSm.getType()))||"TEACHER".equals(branchSm.getType())) { //type
			mainSm.setType(branchSm.getType());
		}
		if(StringUtil.isNotEmpty(branchSm.getRemark())) { //拼音
			mainSm.setRemark(branchSm.getRemark());
		}
		if(StringUtil.isNotEmpty(branchSm.getNation())) { //民族
			mainSm.setNation(branchSm.getNation());
		}
		if(StringUtil.isNotEmpty(branchSm.getPolitics())) { //政治面貌
			mainSm.setPolitics(branchSm.getPolitics());
		}
		if(StringUtil.isNotEmpty(branchSm.getTruePhoto())) { //政治面貌
			mainSm.setTruePhoto(branchSm.getTruePhoto());
		}
		schoolmateMapper.update(mainSm);
		return mainSm;
	}
	
	
	/**
	 * 导入时去重合并
	 */
	@Transactional(readOnly = false)
	public void mergeImportSm(SmSchoolmate mainSm,SmSchoolmate ImportSm) {
			//基本信息处理
			this.mergeSmInfo(ImportSm, mainSm);
			
			List<SmContact> contacts = ImportSm.getSmContacts();
			List<SmEducation> educations = ImportSm.getSmEducations();
			List<SmAddress> addresses = ImportSm.getSmAddresses();
			List<SmProfession> professions = ImportSm.getSmProfessions();
			
			for (SmContact con : contacts) {
				con.setUserId(mainSm.getUserId());
				con.setIsDefault(GlobalStr.NOT_DEFAULT);
				smContactService.save(con);
			}
			for (SmEducation edu : educations) {
				edu.setUserId(mainSm.getUserId());
				edu.setIsDefault(GlobalStr.NOT_DEFAULT);
				smEducationService.save(edu);
			}
			for (SmAddress add : addresses) {
				add.setUserId(mainSm.getUserId());
				smAddressService.save(add);
			}
			for (SmProfession pro : professions) {
				pro.setType(GlobalStr.NOT_DEFAULT);
				pro.setUserId(mainSm.getUserId());
				//如果导入的为现任职 则修改其他为曾任职
				pro = smProfessionService.save(pro);
				if(StringUtil.isNotEmpty(pro.getStatus()) && GlobalStr.PROFESSION_STATUS_JOIN_IN.equals(pro.getStatus())) {
					//更新其他为曾任职
					smProfessionService.updateProStatus(pro);
				}
			}
	}

	/**
	 * 更新联系方式
	 * 
	 * @param oldUserId
	 * @param newUserId
	 */
	public void mergeSmContact(String oldUserId, String newUserId) {
		// 获取旧数据
		SmContact contact = new SmContact();
		contact.setUserId(oldUserId);
		List<SmContact> list = smContactService.loadAllListBy(contact);
		for (SmContact con : list) {
			con.setUserId(newUserId);
			con.setIsDefault(GlobalStr.NOT_DEFAULT);
			smContactService.save(con);
		}
	}

	/**
	 * 更新地址方式
	 */
	public void mergeSmAddress(String oldUserId, String newUserId) {
		SmAddress address = new SmAddress();
		address.setUserId(oldUserId);
		List<SmAddress> list = smAddressService.loadAllListBy(address);
		for (SmAddress add : list) {
			add.setUserId(newUserId);
			smAddressService.save(add);
		}
	}

	/**
	 * 更新职业经历
	 */
	public void mergeSmProfession(String oldUserId, String newUserId) {
		SmProfession profession = new SmProfession();
		profession.setUserId(oldUserId);
		List<SmProfession> list = smProfessionService.loadAllListBy(profession);
		for (SmProfession pro : list) {
			pro.setType(GlobalStr.NOT_DEFAULT);
			pro.setUserId(newUserId);
			smProfessionService.save(pro);
		}
	}

	/**
	 * 更新家庭成员
	 */
	public void mergeSmFamily(String oldUserId, String newUserId) {
		SmFamily family = new SmFamily();
		family.setUserId(oldUserId);
		List<SmFamily> list = smFamilyService.loadAllListBy(family);
		for (SmFamily fam : list) {
			fam.setUserId(newUserId);
			smFamilyService.save(fam);
		}
	}

	/**
	 * 更新教育经历
	 */
	public void mergeSmEducation(String oldUserId, String newUserId) {
		SmEducation education = new SmEducation();
		education.setUserId(oldUserId);
		List<SmEducation> list = smEducationService.loadAllListBy(education);
		for (SmEducation edu : list) {
			edu.setUserId(newUserId);
			edu.setIsDefault(GlobalStr.NOT_DEFAULT);
			smEducationService.save(edu);
		}
	}

	/**
	 * 更新历史数据
	 */
	public void mergeSmHistory(String oldUserId, String newUserId) {
		SmHistorydata history = new SmHistorydata();
		history.setUserId(oldUserId);
		List<SmHistorydata> list = smHistorydataService.loadAllListBy(history);
		for (SmHistorydata his : list) {
			his.setUserId(newUserId);
			smHistorydataService.save(his);
		}
	}

	/**
	 * 更新政治面貌
	 */
	public void mergeSmPolitics(String oldUserId, String newUserId) {
		SmPolitics politics = new SmPolitics();
		politics.setUserId(oldUserId);
		List<SmPolitics> list = smPoliticsService.loadAllListBy(politics);
		for (SmPolitics politic : list) {
			politic.setUserId(newUserId);
			smPoliticsService.save(politic);
		}
	}
	
	/**
	 * 更新荣誉成果
	 */
	public void mergeSmHonors(String oldUserId, String newUserId) {
		SmHonor honor = new SmHonor();
		honor.setUserId(oldUserId);
		List<SmHonor> list = smHonorService.loadAllListBy(honor);
		for (SmHonor h : list) {
			h.setUserId(newUserId);
			smHonorService.save(h);
		}
	}
	
	/**
	 * 更新社会兼职
	 */
	public void mergeSmSocial(String oldUserId, String newUserId) {
		SmSocial smSocial = new SmSocial();
		smSocial.setUserId(oldUserId);
		List<SmSocial> list = smSocialService.loadAllListBy(smSocial);
		for (SmSocial social : list) {
			social.setUserId(newUserId);
			smSocialService.save(social);
		}
	}
	
	/**
	 * 更新其他信息
	 */
	public void mergeSmOtherInfos(String oldUserId, String newUserId) {
		SmOtherInfo otherInfo = new SmOtherInfo();
		otherInfo.setUserId(oldUserId);
		List<SmOtherInfo> list = smOtherInfoService.loadAllListBy(otherInfo);
		for (SmOtherInfo o : list) {
			o.setUserId(newUserId);
			smOtherInfoService.save(o);
		}
	}
	
	
	/**
	 * 更新其他教育经历
	 */
	public void mergeSmHisEducation(String oldUserId, String newUserId) {
		SmHisEducation smHisEducation = new SmHisEducation();
		smHisEducation.setUserId(oldUserId);
		List<SmHisEducation> list = smHisEducationService.loadAllListBy(smHisEducation);
		for (SmHisEducation sEducation : list) {
			sEducation.setUserId(newUserId);
			smHisEducationService.save(sEducation);
		}
	}
	
	/**
	 * 更新校友
	 */
	public void mergeSchoolmate(String oldUserId) {
		SmSchoolmate oldSm = new SmSchoolmate();
		oldSm.setUserId(oldUserId);
		schoolmateMapper.deleteOldSm(oldSm);
	}

	/**
	 * 更新用户
	 */
	public void mergeSysUser(String oldUserId) {
		SysUser oldUser = new SysUser();
		oldUser.setId(oldUserId);
		sysUserService.delete(oldUser);
	}
	
	
	

}
