package com.mpri.aio.schoolmate.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.mine.mapper.AppSmBaseInfoMapper;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.common.utils.PinyinUtil;
import com.mpri.aio.common.utils.PinyinUtil.Type;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.message.vo.MesGroupSchoolmates;
import com.mpri.aio.schoolmate.mapper.SmContactMapper;
import com.mpri.aio.schoolmate.mapper.SmEducationMapper;
import com.mpri.aio.schoolmate.mapper.SmSchoolmateMapper;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmExperience;
import com.mpri.aio.schoolmate.model.SmFamily;
import com.mpri.aio.schoolmate.model.SmHisEducation;
import com.mpri.aio.schoolmate.model.SmHistorydata;
import com.mpri.aio.schoolmate.model.SmHonor;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.model.SmOtherInfo;
import com.mpri.aio.schoolmate.model.SmPolitics;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.model.SmSocial;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.schoolmate.utils.SchoolmateImportHandler;
import com.mpri.aio.schoolmate.vo.ImpNewInfo;
import com.mpri.aio.schoolmate.vo.ImpSchoolmate;
import com.mpri.aio.schoolmate.vo.ImpWorkInfo;
import com.mpri.aio.schoolmate.vo.ImportSchoolmateEnum;
import com.mpri.aio.settings.mapper.SettingCardMapper;
import com.mpri.aio.settings.model.SettingCard;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingUserCard;
import com.mpri.aio.settings.service.SettingUserCardService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.mapper.SysUserMapper;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.untils.export.ExcelExportUtils;
import com.mpri.aio.webber.vo.DonSchoolmateInfoVo;
import com.mpri.aio.webber.vo.SchoolmateInfoVo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * @Description: 校友基本信息——Service
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:29:11 CST 2019
 * @Version: v_1.0
 *
 */
@Service
public class SmSchoolmateService extends CrudService<SmSchoolmateMapper, SmSchoolmate> {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private SmAddressService smAddressService;
	@Autowired
	private SmContactService smContactService;
	@Autowired
	private SmEducationService smEducationService;
	@Autowired
	private SmExperienceService smExperienceService;
	@Autowired
	private SmFamilyService smFamilyService;
	@Autowired
	private SmHistorydataService smHistorydataService;
	@Autowired
	private SmHonorService smHonorService;
	@Autowired
	private SmPoliticsService smPoliticsService;
	@Autowired
	private SmProfessionService smProfessionService;
	@Autowired
	private SmSocialService smSocialService;
	@Autowired
	private SmOtherInfoService smOtherInfoService;
	@Autowired
	private SmHisEducationService smHisEducationService;
	@Autowired
	private SmWishService smWishService;
	@Autowired
	private SchoolmateMergeService schoolmateMergeService;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SmSchoolmateMapper smSchoolmateMapper;

	@Autowired
	private SmContactMapper smContactMapper;

	@Autowired
	private SmEducationMapper smEducationMapper;
	@Autowired
	private SchoolmateImportHandler importHandler;

	private Map<String, SettingDepartment> departBaseCache;
	
	
	@Autowired
	private AppSmBaseInfoMapper appSmBaseInfoMapper;
	
	
	@Autowired
	private SettingCardMapper settingCardMapper;
	
	@Autowired
	private SettingUserCardService settingUserCardService;

	private void InitMaps() {
		departBaseCache= (Map<String, SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
	}
	/**
	 * 保存校友及相关联信息
	 */
	@Transactional(readOnly = false)
	public SmSchoolmate saveSmAndUser(SmSchoolmate schoolmate) {
		SysUser sysUser = this.saveSysUser(schoolmate);
		SmContact smContact = schoolmate.getSmContact();
//		SmAddress smAddress = schoolmate.getSmAddress();
		// 新增籍贯
		if (StringUtil.isEmpty(schoolmate.getId())) {
			// 保存校友联系方式
			if (smContact.getContact().length() == 32) {
				// 没有联系方式的校友(目前未做处理)
			} else {
				smContact.setUserId(sysUser.getId());
				if (smContact.getContact().contains("@")) {
					smContact.setType(GlobalStr.EMAIL);
				} else {
					smContact.setType(GlobalStr.PHONE);
				}
				smContact.setIsDefault(GlobalStr.IS_DEFAULT);
				smContactService.save(smContact);
				schoolmate.setSmContact(smContact);
			}
			schoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
		}
		schoolmate.setUserId(sysUser.getId());
		this.save(schoolmate);
		this.saveUserCard(schoolmate);
		return schoolmate;
	}


	/**
	 * 删除校友功能
	 */
	@Transactional(readOnly = false)
	public void deleteSmInfo(SmSchoolmate smSchoolmate) {
		String userId  = smSchoolmate.getUserId();

		//删除主信息
		this.deleteOldSm(smSchoolmate);
		//删除用户
		SysUser user = new SysUser();
		user.setId(userId);
		user = sysUserService.get(user);
		//删除用户信息
		sysUserService.deleteUser(user);
		//删除校友会校友信
		AsAssociationUser asAssociationUser = new AsAssociationUser();
		asAssociationUser.setUserId(userId);
		mapper.deleteSysAs(asAssociationUser);
		//删除地址
		SmAddress smAddress = new SmAddress();
		smAddress.setUserId(userId);
		smAddressService.deleteByUserId(smAddress);
		//删除联系方式
		SmContact contact = new SmContact();
		contact.setUserId(userId);
		smContactService.deleteByUserId(contact);
		//删除 教育经历
		SmEducation education = new SmEducation();
		education.setUserId(userId);
		smEducationService.deleteByUserId(education);
		// 删除 校园经历
//		SmExperience experience = new SmExperience();
//		education.setUserId(userId);
//		smExperienceService.deleteByUserId(experience);
		//删除家庭成员
//		SmFamily smFamily = new SmFamily();
//		smFamily.setUserId(userId);
//		smFamilyService.deleteByUserId(smFamily);
		// 删除 其他教育经历
		SmHisEducation smHisEducation = new SmHisEducation();
		smHisEducation.setUserId(userId);
		smHisEducationService.deleteByUserId(smHisEducation);
		// 删除 历史数据
		SmHistorydata historydata = new SmHistorydata();
		historydata.setUserId(userId);
		smHistorydataService.deleteByUserId(historydata);
		//删除荣誉成果
		SmHonor honor = new SmHonor();
		honor.setUserId(userId);
		smHonorService.deleteByUserId(honor);
		// 删除 其他信息
		SmOtherInfo info = new SmOtherInfo();
		info.setUserId(userId);
		smOtherInfoService.deleteByUserId(info);
		//删除 政治面貌
		SmPolitics smPolitics = new SmPolitics();
		smPolitics.setUserId(userId);
		smPoliticsService.deleteByUserId(smPolitics);
		// 删除 职业经历
		SmProfession profession = new SmProfession();
		profession.setUserId(userId);
		smProfessionService.deleteByUserId(profession);
		//社会经历
		SmSocial smSocial = new SmSocial();
		smSocial.setUserId(userId);
		smSocialService.deleteByUserId(smSocial);
		//删除祝福
		SmWish swWish = new SmWish();
		swWish.setUserId(userId);
		smWishService.deleteByUserId(swWish);
	}
	
	/**
	 * 删除用户时删除校友数据
	 */
	@CacheEvict(value = "userCache", key ="#sysUser.username")
	@Transactional(readOnly = false)
	public void deleteSmByUser(SysUser sysUser) {
		String userId = sysUser.getId();		
		//删除校友会校友信
		AsAssociationUser asAssociationUser = new AsAssociationUser();
		asAssociationUser.setUserId(userId);
		mapper.deleteSysAs(asAssociationUser);
		//删除地址
		SmAddress smAddress = new SmAddress();
		smAddress.setUserId(userId);
		smAddressService.deleteByUserId(smAddress);
		//删除联系方式
		SmContact contact = new SmContact();
		contact.setUserId(userId);
		smContactService.deleteByUserId(contact);
		//删除 教育经历
		SmEducation education = new SmEducation();
		education.setUserId(userId);
		smEducationService.deleteByUserId(education);
		// 删除 校园经历
//		SmExperience experience = new SmExperience();
//		education.setUserId(userId);
//		smExperienceService.deleteByUserId(experience);
		//删除家庭成员
//		SmFamily smFamily = new SmFamily();
//		smFamily.setUserId(userId);
//		smFamilyService.deleteByUserId(smFamily);
		// 删除 其他教育经历
		SmHisEducation smHisEducation = new SmHisEducation();
		smHisEducation.setUserId(userId);
		smHisEducationService.deleteByUserId(smHisEducation);
		// 删除 历史数据
		SmHistorydata historydata = new SmHistorydata();
		historydata.setUserId(userId);
		smHistorydataService.deleteByUserId(historydata);
		//删除荣誉成果
		SmHonor honor = new SmHonor();
		honor.setUserId(userId);
		smHonorService.deleteByUserId(honor);
		// 删除 其他信息
		SmOtherInfo info = new SmOtherInfo();
		info.setUserId(userId);
		smOtherInfoService.deleteByUserId(info);
		//删除 政治面貌
		SmPolitics smPolitics = new SmPolitics();
		smPolitics.setUserId(userId);
		smPoliticsService.deleteByUserId(smPolitics);
		// 删除 职业经历
		SmProfession profession = new SmProfession();
		profession.setUserId(userId);
		smProfessionService.deleteByUserId(profession);
		//社会经历
		SmSocial smSocial = new SmSocial();
		smSocial.setUserId(userId);
		smSocialService.deleteByUserId(smSocial);
		//删除祝福
		SmWish swWish = new SmWish();
		swWish.setUserId(userId);
		smWishService.deleteByUserId(swWish);
		//删除用户信息
		sysUserService.deleteUser(sysUser);
		//删除主信息
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setUserId(userId);
		this.deleteOldSm(smSchoolmate);
	}
	
	/**
	 * 通过校友id获取校友基本信息(籍贯和用户名（即联系方式）)
	 */
	public SmSchoolmate getSmInfoById(SmSchoolmate schoolmate) {
		return mapper.getSmInfoById(schoolmate);
	}

	/**
	 * 新增校友时
	 * <p>
	 * Title: saveSysUser
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param schoolmate
	 * @return
	 */
	@Transactional(readOnly = false)
	public SysUser saveSysUser(SmSchoolmate schoolmate) {
		SysUser sysUser = new SysUser();
		// 编辑
		if (!StringUtil.isEmpty(schoolmate.getUserId())) {
			sysUser.setId(schoolmate.getUserId());
			sysUser = sysUserService.get(sysUser);
			sysUser.setVirtualName(schoolmate.getName());
			if (GlobalStr.ID_CARD.equals(schoolmate.getCardType())) {
				sysUser.setIdcard(schoolmate.getCardNum());
			}
			sysUserService.save(sysUser);
		} else {
			sysUser.setSafecode(IdGen.uuid());
			// 保存用户
			if (GlobalStr.DEFAULT_CARDTYPE.equalsIgnoreCase(schoolmate.getCardType())) {
				sysUser.setPassword(initPwd(schoolmate.getCardNum()));
			} else {
				sysUser.setPassword(initPwd(null));
			}
			ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
			// 加盐炒三次safecode=salt
			String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
			sysUser.setPassword(result);
			sysUser.setUserType(GlobalStr.DEFAULT_USER_TYPE);
			if (schoolmate.getSmContact() == null || StringUtils.isEmpty(schoolmate.getSmContact().getContact())) {
				sysUser.setUsername(IdGen.uuid());
			} else {
				sysUser.setUsername(schoolmate.getSmContact().getContact());
				// 保存手机号
				if (schoolmate.getSmContact().getContact().length() != 32) {
					sysUser.setMobile(schoolmate.getSmContact().getContact());
				}
			}
			sysUser.setVirtualName(schoolmate.getName());
			if (GlobalStr.ID_CARD.equals(schoolmate.getCardType())) {
				sysUser.setIdcard(schoolmate.getCardNum());
			}
			sysUserService.save(sysUser);
			// 插入默认校友会
			sysUserService.insertDfAs(sysUser);
		}

		return sysUser;
	}

	/**
	 * 获取加盐的次数
	 */
	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		return Integer.parseInt(list.get(0).getValue());
	}

	/**
	 * 初始化密码(冗余代码)
	 * <p>
	 * Title: initPwd
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param idCard
	 * @return
	 */
	private String initPwd(String idCard) {
		if (null != idCard && idCard.length() > 6) {
			return idCard.substring(idCard.length() - 6);
		} else {
			return GlobalStr.DEFAULT_PWD;
		}
	}

	// 数据交换事务(交大老库)
	@Transactional(readOnly = false)
	public void saveSm(@RequestBody SmSchoolmate smSchoolmate) throws BadHanyuPinyinOutputFormatCombination {
		// 1.创建用户对象
		SysUser sysUser = new SysUser();
		// 设置为老用户
		sysUser.setPassword(initPwd(null));
		sysUser.setUserSource(GlobalStr.USER_SOURCE_HISTORY_DATA);
		sysUser.setUserType(GlobalStr.NORMAL);
		sysUser.setSafecode(IdGen.uuid());
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
		// 加盐炒三次safecode=salt
		String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
		sysUser.setPassword(result);
		sysUser.setVirtualName(smSchoolmate.getName());
		sysUser.setUsername(IdGen.uuid());
		// 2.设置userName
//			for(SmContact con: smSchoolmate.getSmContacts()) {
//				if(GlobalStr.PHONE.equals(con.getType())) {
////					sysUser.setUsername(con.getContact());
////					sysUser.setVirtualName(con.getContact());
//					con.setIsDefault(GlobalStr.IS_DEFAULT);
//					break;
//				}
//			}
//			if(StringUtil.isEmpty(sysUser.getUsername()) && StringUtil.isEmpty(sysUser.getVirtualName())) {
//				sysUser.setUsername(IdGen.uuid());
////				sysUser.setVirtualName(smSchoolmate.getName());
//			}
		sysUser = sysUserService.save(sysUser);
		// 保存用户到校友总会下面
		sysUserService.insertDfAs(sysUser);
		this.saveSmAddresses(smSchoolmate, sysUser.getId());
		this.saveSmContacts(smSchoolmate, sysUser.getId());
		this.saveSmEducations(smSchoolmate, sysUser.getId());
		this.saveSmExperiences(smSchoolmate, sysUser.getId());
		this.saveSmFamilys(smSchoolmate, sysUser.getId());
		this.saveSmHonors(smSchoolmate, sysUser.getId());
		this.saveSmHistorydatas(smSchoolmate, sysUser.getId());
		this.saveSmProfessions(smSchoolmate, sysUser.getId());
		this.saveSmSocials(smSchoolmate, sysUser.getId());
		this.SmPolitics(smSchoolmate, sysUser.getId());
		// 校友类型
		smSchoolmate.setUserId(sysUser.getId());
		// 设置卡状态
		smSchoolmate.setCardStatus(GlobalStr.DEFAULT_CARD_STATUS);
		PinyinUtil pu = new PinyinUtil();
		smSchoolmate.setPinyin(pu.toPinYin(smSchoolmate.getName(), "",Type.LOWERCASE));
		this.save(smSchoolmate);
	}

	/**
	 * 保存所有地址
	 */
	public void saveSmAddresses(SmSchoolmate smSchoolmate, String userId) {
		List<SmAddress> smAddresses = smSchoolmate.getSmAddresses();
		for (SmAddress add : smAddresses) {
			add.setUserId(userId);
			smAddressService.save(add);
		}
	}

	/**
	 * 保存所有联系方式
	 */
	public void saveSmContacts(SmSchoolmate smSchoolmate, String userId) {
		List<SmContact> smContacts = smSchoolmate.getSmContacts();
		for (SmContact contact : smContacts) {
			contact.setUserId(userId);
			smContactService.save(contact);
		}
	}

	/**
	 * 保存所有教育经历
	 */
	public void saveSmEducations(SmSchoolmate smSchoolmate, String userId) {
		List<SmEducation> smEducations = smSchoolmate.getSmEducations();
		for (SmEducation edu : smEducations) {
			edu.setUserId(userId);
			smEducationService.save(edu);
		}
	}

	/**
	 * 保存所有校园经历
	 */
	public void saveSmExperiences(SmSchoolmate smSchoolmate, String userId) {
		List<SmExperience> smExperiences = smSchoolmate.getSmExperiences();
		for (SmExperience experience : smExperiences) {
			experience.setUserId(userId);
			smExperienceService.save(experience);
		}
	}

	/**
	 * 保存所有家庭成员
	 */
	public void saveSmFamilys(SmSchoolmate smSchoolmate, String userId) {
		List<SmFamily> smFamilies = smSchoolmate.getSmFamilies();
		for (SmFamily family : smFamilies) {
			family.setUserId(userId);
			smFamilyService.save(family);
		}
	}

	/**
	 * 保存所有荣誉
	 */
	public void saveSmHonors(SmSchoolmate smSchoolmate, String userId) {
		List<SmHonor> smHonors = smSchoolmate.getSmHonors();
		for (SmHonor honor : smHonors) {
			honor.setUserId(userId);
			smHonorService.save(honor);
		}
	}

	/**
	 * 保存所有历史信息
	 */
	public void saveSmHistorydatas(SmSchoolmate smSchoolmate, String userId) {
		List<SmHistorydata> smHistorydatas = smSchoolmate.getSmHistorydatas();
		for (SmHistorydata history : smHistorydatas) {
			history.setUserId(userId);
			smHistorydataService.save(history);
		}
	}

	/**
	 * 保存所有职业经历
	 */
	public void saveSmProfessions(SmSchoolmate smSchoolmate, String userId) {
		List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
		for (SmProfession profession : smProfessions) {
			profession.setUserId(userId);
			smProfessionService.save(profession);
		}
	}

	/**
	 * 保存所有社会兼职
	 */
	public void saveSmSocials(SmSchoolmate smSchoolmate, String userId) {
		List<SmSocial> smSocials = smSchoolmate.getSmSocials();
		for (SmSocial social : smSocials) {
			social.setUserId(userId);
			smSocialService.save(social);
		}
	}

	/**
	 * 保存所有政治面貌
	 */
	public void SmPolitics(SmSchoolmate smSchoolmate, String userId) {
		List<SmPolitics> smPolitics = smSchoolmate.getSmPolitics();
		for (SmPolitics politic : smPolitics) {
			politic.setUserId(userId);
			smPoliticsService.save(politic);
		}
	}

	/**
	 * 获取导出的List
	 */
	public List<SmSchoolmate> ExportList(SmSchoolmate schoolmate) {
		return mapper.ExportList(schoolmate);
	}

	public List<SmSchoolmate> ExportListByCondition(SmSchoolmate smSchoolmate,String fuzzySearchFiled) {
		List<SmSchoolmate> smSchoolmates = mapper.ExportListByCondition(smSchoolmate, fuzzySearchFiled);
		List<SmSchoolmate> result  = handlerSmSchoolmates(smSchoolmates);
		return result;
	}

	private List<SmSchoolmate> handlerSmSchoolmates(List<SmSchoolmate> smSchoolmates) {
		for (SmSchoolmate smSchoolmate : smSchoolmates) {
			List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
			if(null != smProfessions && smProfessions.size()> 1){
				for (int i = 0; i < smProfessions.size(); i++) {
					if("JOIN_IN".equals(smProfessions.get(i).getStatus())){
						smSchoolmate.setSmProfession(smProfessions.get(i));
						break;
					}
				}
			}
		}
		return  smSchoolmates;
	}


	/**
	 * ss 循环导入
	 * <p>
	 * Title: importSmTempData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@Transactional(readOnly = false)
	public void importSmDatas(List<SmSchoolmate> list) throws BadHanyuPinyinOutputFormatCombination{
		for (SmSchoolmate smSchoolmate : list) {
			// 查询是否有原库中是否有该校友
			if(smSchoolmate.getSmEducations().size() > 0) {
				smSchoolmate.setSmEducation(smSchoolmate.getSmEducations().get(0));
			}
			List<SmSchoolmate> smList = this.getListCompare(smSchoolmate);
			if (smList.size() > 0) {
				//以最新的一条为主信息
				SmSchoolmate main = smList.get(0);
				//合并
				schoolmateMergeService.mergeImportSm(main, smSchoolmate);
			} else {
				this.saveSm(smSchoolmate);
			}
		}
	}

	/**
	 * 对比获取重复数据
	 */
	public List<SmSchoolmate> getListCompare(SmSchoolmate schoolmate) {
		return mapper.getListCompare(schoolmate);
	}

	/**
	 * 获取校友列表
	 */
	public List<SmSchoolmate> loadSmsByMark(SmMark smMark) {
		return mapper.loadSmsByMark(smMark);
	}

	@Transactional(readOnly = false)
	public void saveUserMark(SmSchoolmate smSchoolmate) {
		mapper.deleteUserMark(smSchoolmate);
		if (smSchoolmate.getMarkList() != null && smSchoolmate.getMarkList().size() > 0) {
			mapper.insertUserMark(smSchoolmate);
		}
		this.save(smSchoolmate);
	}

	/**
	 * 通过name查询
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param smSchoolmate
	 * @return
	 */
	public PageIo<SmSchoolmate> listByName(int pageNo, int pageSize, SmSchoolmate smSchoolmate) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SmSchoolmate> pageList = this.mapper.listByName(smSchoolmate);
		PageIo<SmSchoolmate> pageInfo = new PageIo(pageList);
		return pageInfo;
	}

	public PageIo<SmSchoolmate> listOnlyByName(int pageNo, int pageSize, SmSchoolmate smSchoolmate) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SmSchoolmate> pageList = this.mapper.listOnlyByName(smSchoolmate);
		PageIo<SmSchoolmate> pageInfo = new PageIo(pageList);
		return pageInfo;
	}

	/**
	 * 不带分页获取数据（带多条件）
	 */
	public List<SmSchoolmate> loadListBy(SmSchoolmate schoolmate) {
		return mapper.loadListBy(schoolmate);
	}

	/**
	 * 通过生日查询
	 * <p>
	 * Title: loadListByBirth
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param smSchoolmate
	 * @return
	 */
	public List<SmSchoolmate> loadListByBirth(SmSchoolmate smSchoolmate) {
		return mapper.loadListByBirth(smSchoolmate);
	}

	/**
	 * @Desc 根据id 获取校友所有信息
	 * @param smSchoolmate
	 * @return
	 */
	public SmSchoolmate getUserINfoById( SmSchoolmate smSchoolmate) {
		return mapper.getUserINfoById(smSchoolmate);
	}

	/**
	 * 更新完整度
	 * <p>
	 * Title: updateComplete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param smSchoolmate
	 */
	@Transactional(readOnly = false)
	public void updateComplete(SmSchoolmate smSchoolmate) {
		mapper.updateComplete(smSchoolmate);
	}

	/**
	 * 可能是他（校友）
	 */
	public List<SmSchoolmate> getListLike(SmSchoolmate schoolmate) {
		return mapper.getListLike(schoolmate);
	}

	public void deleteOldSm(SmSchoolmate schoolmate) {
		mapper.deleteOldSm(schoolmate);
	}

	/**
	 * 校友数据全字段模糊查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = false)
//	public PageIo<SmSchoolmate> loadByPageAll(int pageNo, int pageSize, SmSchoolmate schoolmate) {
	public PageIo<SmSchoolmate> loadByPageAll(int pageNo,  int pageSize,String selectStr,String type) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SmSchoolmate> pageList = this.mapper.loadByPageAll(selectStr,type);
//        Page<SmSchoolmate> pageList = this.mapper.loadByPageAll(schoolmate);
		PageIo<SmSchoolmate> pageInfo = new PageIo(pageList);
		return pageInfo;
	}
	/**
	 * 不带分页获取群组条件（带多条件）
	 */
	public List<MesGroupSchoolmates> loadListByCondition(SmSchoolmate schoolmate) {
		return mapper.loadListByCondition(schoolmate);
	}

	/**校友信息 单位信息统计*/
	public List<SmSchoolmate> loadListByStaticCondition(SmSchoolmate schoolmate) {
		return mapper.loadListByStaticCondition(schoolmate);
	}


	/**
	 * 根据传入到类型获取导入信息
	 */
	public <T> ExcelImportResult<T> getListByImportType(String type,File file) {
		//设置校验
		ImportParams importParams = new ImportParams();
		importParams.setNeedVerify(true);
		importParams.setKeyIndex(0);
		switch (type) {
			case ImportSchoolmateEnum.DAILY:
				return ExcelImportUtil.importExcelMore(
						file, ImpSchoolmate.class, importParams);
			case ImportSchoolmateEnum.EMPLOY:
				return ExcelImportUtil.importExcelMore(
						file, ImpWorkInfo.class, importParams);
			case ImportSchoolmateEnum.NEWPUPIL:
				return ExcelImportUtil.importExcelMore(
						file, ImpNewInfo.class, importParams);
			default:
				break;
		}
		return null;
	}

	/**
	 * 转换数据为校友对象
	 * @param <T>
	 * @param type
	 * @param list
	 * @return
	 */
	public <T> List<SmSchoolmate> covertByImportType(String type, List<T> list) {
		switch (type) {
			case ImportSchoolmateEnum.DAILY:
				//执行转换
				return importHandler.covertToDailyHandler((List<ImpSchoolmate>)list);
			case ImportSchoolmateEnum.EMPLOY:
				//执行转换
				return importHandler.covertToEmployHandler((List<ImpWorkInfo>)list);
			case ImportSchoolmateEnum.NEWPUPIL:
				//执行转换
				return importHandler.covertToNewpupilHandler((List<ImpNewInfo>)list);
			default:
				break;
		}
		return null;
	}

	/**
	 * 导出导入失败的数据
	 */
	public void exportErroDatas(String type,String filePath,List list) {
		switch (type) {
			case ImportSchoolmateEnum.DAILY:
				ExcelExportUtils.defaultExport(list, ImpSchoolmate.class, filePath, new ExportParams());
				return;
			case ImportSchoolmateEnum.EMPLOY:
				ExcelExportUtils.defaultExport(list, ImpWorkInfo.class, filePath, new ExportParams());
				return;
			case ImportSchoolmateEnum.NEWPUPIL:
				ExcelExportUtils.defaultExport(list, ImpNewInfo.class, filePath, new ExportParams());
				return;
			default:
				break;
		}
	}

	/**
	 * 获取 相同 校友数据 ---交大数据合并
	 * @param nowSchoolmate
	 * @return
	 */
	public List<SmSchoolmate> getListCompareByUserId(SmSchoolmate nowSchoolmate) {
		return mapper.getListCompareByUserId(nowSchoolmate);
	}

	/**
	 * 获取所有校友数据 包含教育经历---交大数据合并
	 * @param smSchoolmate
	 * @return
	 */
	public SmSchoolmate getUserEduInfo(SmSchoolmate smSchoolmate) {
		return mapper.getUserEduInfo(smSchoolmate);
	}

	/**
	 * 获取有身份证的
	 * @param smSchoolmate
	 * @return
	 */
	public List<SmSchoolmate> getListByCardNum(SmSchoolmate smSchoolmate ) {
		return this.mapper.getListByCardNum(smSchoolmate);
	}

	/**
	 * 获取校友信息分页 ---在校友网组件中使用
	 * @param pageNo
	 * @param pageSize
	 * @param smSchoolmate
	 * @return
	 */
	public PageIo<SchoolmateInfoVo> getSchoolmateInfo(int pageNo, int pageSize, SmSchoolmate smSchoolmate) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SchoolmateInfoVo> pageList = this.mapper.getSchoolmateInfo(smSchoolmate);
		PageIo<SchoolmateInfoVo> pageInfo = new PageIo(pageList);
		return pageInfo;
	}

	/**
	 * 通过手机号查询校友--在组件中使用
	 * @param smContact
	 * @return
	 */
	public SchoolmateInfoVo getSchoolmateByPhone(SmContact smContact) {
		return this.mapper.getSchoolmateByPhone(smContact);
	}

	/**
	 * 校友注册
	 * @param donSchoolmateInfoVo
	 */
	@Transactional(readOnly = false)
	public Map<String ,Object> saveSchoolmate(DonSchoolmateInfoVo donSchoolmateInfoVo) {
		Map<String ,Object> map =new HashMap<>();
		this.InitMaps();
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		SysUser sysUser = new SysUser();
		// 设置用户
		sysUser.preInsert();
		if(StringUtil.isEmpty(donSchoolmateInfoVo.getPassword())){
			sysUser.setPassword(initPwd(null));
		}else{
			sysUser.setPassword(donSchoolmateInfoVo.getPassword());
		}
		sysUser.setUserSource(GlobalStr.USER_SOURCE_NEW_CARD);
		sysUser.setUserType(GlobalStr.NORMAL);
		sysUser.setSafecode(IdGen.uuid());
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
		// 加盐炒三次safecode=salt
		String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
		sysUser.setPassword(result);
		sysUser.setVirtualName(donSchoolmateInfoVo.getName());
		if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getPhone())){
			sysUser.setUsername(donSchoolmateInfoVo.getPhone());
			sysUser.setMobile(donSchoolmateInfoVo.getPhone());
		}else{
			sysUser.setUsername(IdGen.uuid());
		}
		sysUser.setEmail(donSchoolmateInfoVo.getEmail());
		sysUserMapper.insert(sysUser);
		// 保存用户到校友总会下面
		sysUserMapper.insertDfAs(sysUser);
		smSchoolmate.setSex(donSchoolmateInfoVo.getSex());
		smSchoolmate.setUserId(sysUser.getId());
		smSchoolmate.preInsert();
		smSchoolmate.setName(donSchoolmateInfoVo.getName());
		smSchoolmate.setType(GlobalStr.DEFAULT_SCHOOLMATE_TYPE);
		smSchoolmate.setCardType(GlobalStr.ID_CARD);
		smSchoolmateMapper.insert(smSchoolmate);
		if (StringUtil.isNotEmpty(donSchoolmateInfoVo.getPhone())){
			SmContact smContact2 = new SmContact();
			smContact2.preInsert();
			smContact2.setType(GlobalStr.PHONE);
			smContact2.setContact(donSchoolmateInfoVo.getPhone());
			smContact2.setUserId(sysUser.getId());
			smContactMapper.insert(smContact2);
		}
		if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getEmail())){
			SmContact smContact1 = new SmContact();
			smContact1.preInsert();
			smContact1.setType(GlobalStr.EMAIL);
			smContact1.setContact(donSchoolmateInfoVo.getEmail());
			smContact1.setUserId(sysUser.getId());
			smContactMapper.insert(smContact1);
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SmEducation smEducation = new SmEducation();
		smEducation.preInsert();
		smEducation.setUserId(sysUser.getId());
		try {
			smEducation.setStartdate(format.parse(donSchoolmateInfoVo.getStartdate()));
			smEducation.setEnddate(format.parse(donSchoolmateInfoVo.getEnddate()));
		}catch (Exception e){

		}
		smEducation.setSchool(departBaseCache.get(donSchoolmateInfoVo.getCollege()).getParentId());
		if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getCollege())){
			smEducation.setCollege(donSchoolmateInfoVo.getCollege());
		}
		if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getSeries())){
			smEducation.setSeries(donSchoolmateInfoVo.getSeries());
		}
		if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getSpecialty())){
			smEducation.setSpecialty(donSchoolmateInfoVo.getSpecialty());
		}
		smEducation.setClasses(donSchoolmateInfoVo.getClasses());
		smEducation.setStudentNo(donSchoolmateInfoVo.getStudentNo());
		smEducationMapper.insert(smEducation);
		return map;
	}

    /**
     * 通过userid获取schoolmate
     * @return
     */
    public SmSchoolmate getByUserId (SmSchoolmate smSchoolmate){

        return this.mapper.getByUserId(smSchoolmate);
    }
    
	 /**
	  * 更新拼音
	  */
    @Transactional(readOnly = false)
    public void updatePinyin(SmSchoolmate smSchoolmate) {
    	mapper.updatePinyin(smSchoolmate);
    }
    
	 /**
	  * 导出用户联系方式
	  */
	 public List<SmContact> ExportContactListByCondition(SmSchoolmate schoolmate,String  fuzzySearchFiled){
		 return mapper.ExportContactListByCondition(schoolmate, fuzzySearchFiled);
	 }

	/**
	 * 插入校友卡中间表
	 */
	public void saveUserCard(SmSchoolmate schoolmate) {
		String userId = schoolmate.getUserId();
		if(StringUtil.isNotEmpty(userId)) {
	    	List<AsAssociation> list = appSmBaseInfoMapper.loadAsByUserId(userId);
	    	for (AsAssociation asAssociation : list) {
	    		SettingCard card = new SettingCard();
	    		//此处先用默认的级数来写
	    		card.setStartLevel(schoolmate.getLevel());
	    		card.setEndLevel(schoolmate.getLevel());
	    		card.setAsId(asAssociation.getId()); 
	    		List<SettingCard> cardList =  settingCardMapper.getSettingCardByLevel(card);
	    		for (SettingCard settingCard : cardList) {
	    			SettingUserCard settingUserCard = new SettingUserCard();
	    			settingUserCard.setCardId(settingCard.getId());
	    			settingUserCard.setUserId(userId);
	    			//卡号暂无算法(先放入时间)
	    			settingUserCard.setCardNo(String.valueOf(new Date().getTime()));
	    			
	    			settingUserCardService.save(settingUserCard);
				}
			}
		}
	}
}
