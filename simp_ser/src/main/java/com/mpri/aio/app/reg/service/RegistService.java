package com.mpri.aio.app.reg.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.reg.mapper.RegistMapper;
import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.utils.CovertDataUtils;
import com.mpri.aio.app.reg.utils.InfoUtils;
import com.mpri.aio.app.reg.vo.ActivitiSmVo;
import com.mpri.aio.app.reg.vo.MultipleLinkageVo;
import com.mpri.aio.app.utils.service.BaiduFaceService;
import com.mpri.aio.app.wxdata.mapper.WxdataTemplateSendMapper;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.common.utils.PinyinUtil;
import com.mpri.aio.common.utils.PinyinUtil.Type;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.service.SmEducationService;
import com.mpri.aio.schoolmate.service.SmProfessionService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.utils.InviteCodeUtils;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.utils.RedisUtil;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.system.utils.SMSUtils;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 注册登录Service
 * 
 * @author syp
 *
 */
@Service
public class RegistService {

	@Autowired
	private RegistMapper registMapper;

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private BaiduFaceService baiduFaceService;
	
	@Autowired
	private FileService fileService;

//	@Value("${sms.template_regdit}")
	private String template_regdit;

//	@Value("${sms.template_backPwd}")
	private String template_backPwd;
	
//	@Value("${sms.template_changeBind}")
	private String template_changeBind;

	@Autowired
	private SMSUtils sMSUtils;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SmSchoolmateService smSchoolmateService;

	@Autowired
	private SmContactService smContactService;

	@Autowired
	private SmEducationService smEducationService;

	@Autowired
	private ActivitiUtils activitiUtils;

	@Autowired
	private SysUserService sysService;
	
	@Autowired
	private SysLoginExpandService sysLoginExpandService;

	@Autowired
	private WxdataTemplateSendMapper wxdataTemplateSendMapper;
	
	@Autowired
	private SmProfessionService smProfessionService;
	
	@Autowired
	private RedisUtil redisUtil;
		
	public final static String regitType = "_regit"; //注册
			
	public final static String backSecretCodeType = "_backPwd"; //找回密码
	
	public final static String changUsernameType= "_changUsername"; //解绑手机号
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	
	
	/*验证过期时长*/
	private static final long EXPIRE_TIME = 180;

	public SysUser getUserByUserName(String username) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		return registMapper.getUserByUserName(sysUser);
	}

	/**
	 * 获取加盐的次数
	 */
	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		return Integer.parseInt(list.get(0).getValue());
	}

	/**
	 * 获取需要认证的人数
	 */
	public int getProveNum() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE,
				"prove.num");
		return Integer.parseInt(list.get(0).getValue());
	}

	/**
	 * 获取校友基础数据
	 * 
	 * @param schoolmate
	 * @return
	 */
	public SmSchoolmate getSchoolmateBaseInfo(SmSchoolmate schoolmate) {
		SmEducation edu = new SmEducation();
		edu.setIsDefault(GlobalStr.IS_DEFAULT);
		SmProfession profession = new SmProfession();
		profession.setType(GlobalStr.IS_DEFAULT);
		schoolmate.setSmEducation(edu);
		schoolmate.setSmProfession(profession);
		return registMapper.getSchoolmateBaseInfo(schoolmate);
	}

	
	
	/**
	 * 判断当前用户是否存在
	 */
	public Boolean existThisUser(String username) {
		return registMapper.existThisUser(username);
	}
	/**
	 * 判断当前用户是否是系统用户
	 */
	public Boolean existSysUser(String username) {
		return registMapper.existSysUser(username);
	}

	/**
	 * @Desc 发送验证码注册
	 * @param toSend
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String sendRegistCaptCha(String toSend){
		// 验证码
		String code = InviteCodeUtils.getShortNum();
		ArrayList<String> params = new ArrayList<String>();
		params.add(toSend);
		params.add(code);
		redisUtil.set(toSend+regitType, code,EXPIRE_TIME);
//		this.putMap(toSend, code, regitCaptChaMap);
		try {
			return sMSUtils.sendSms(toSend, params, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_template_regdit));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * @Desc 发送解绑手机号验证
	 * @param toSend
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String sendChangePhoneCaptCha(String toSend){
		// 验证码
		String code = InviteCodeUtils.getShortNum();
		ArrayList<String> params = new ArrayList<String>();
		params.add(toSend);
		params.add(code);
		redisUtil.set(toSend+changUsernameType, code,EXPIRE_TIME);
//		this.putMap(toSend, code, changUsernameCaptChaMap);
		try {
			return sMSUtils.sendSms(toSend, params, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_template_changeBind));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * @Desc 忘记密码的验证码
	 * @return
	 */
	public String sendBackPwdCaptCha(String toSend){
		// 验证码
		String code = InviteCodeUtils.getShortNum();
		ArrayList<String> params = new ArrayList<String>();
		params.add(toSend);
		params.add(code);
		redisUtil.set(toSend+backSecretCodeType,code,EXPIRE_TIME);
//		this.putMap(toSend, code, backPwdCaptChaMap);
		try {
			return sMSUtils.sendSms(toSend, params, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_template_backPwd));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/*
	 * 构建department四联数据
	 */
	public MultipleLinkageVo getMultipleLinkageDepartments() {
		List<MultipleLinkageVo> mls = new ArrayList<MultipleLinkageVo>();
		Map<String, SettingDepartment> dapartMap = redisCacheService.getBaseCache(new SettingDepartment(),
				InitCache.DEPART_BASE_CACHE);
		dapartMap.forEach((k, v) -> mls.add(new MultipleLinkageVo(v.getId(), v.getParentId(), v.getName())));
		MultipleLinkageVo res = CovertDataUtils.setMultipleLinkageData(mls, "root");
		return res;
	}

	/*
	 * 构建学科四联数据
	 */
	public MultipleLinkageVo getMultipleLinkageSubject() {
		List<MultipleLinkageVo> mls = new ArrayList<MultipleLinkageVo>();
		Map<String, SettingSubject> dapartMap = redisCacheService.getBaseCache(new SettingSubject(),
				InitCache.SETTING_BASE_SUBJECT);
		dapartMap.forEach((k, v) -> mls.add(new MultipleLinkageVo(v.getId(), v.getParentId(), v.getName())));
		MultipleLinkageVo res = CovertDataUtils.setMultipleLinkageData(mls, "root");
		return res;
	}

	/*
	 * 构建地址四联数据
	 */
	public MultipleLinkageVo getMultipleLinkageAddress() {
		List<MultipleLinkageVo> mls = new ArrayList<MultipleLinkageVo>();
		Map<String, SysArea> dapartMap = redisCacheService.getBaseCache(new SettingSubject(),
				InitCache.AREA_BASE_CACHE);
		dapartMap.forEach((k, v) -> mls.add(new MultipleLinkageVo(v.getId(), v.getParentId(), v.getName())));
		MultipleLinkageVo res = CovertDataUtils.setMultipleLinkageData(mls, "root");
		List<MultipleLinkageVo> childs = res.getChilds();
		Collections.sort(childs);
		res.setChilds(childs);
		return res;
	}

	/*
	 * 构建行业四联数据
	 */
	public MultipleLinkageVo getMultipleLinkageIndustry() {
		List<MultipleLinkageVo> mls = new ArrayList<MultipleLinkageVo>();
		Map<String, SysIndustry> dapartMap = redisCacheService.getBaseCache(new SysIndustry(),
				InitCache.SYS_BASE_INDUSTRY_CACHE);
		dapartMap.forEach((k, v) -> mls.add(new MultipleLinkageVo(v.getId(), v.getParentId(), v.getName())));
		MultipleLinkageVo multipleLinkageVo = new MultipleLinkageVo();
		multipleLinkageVo.setId("root");
		multipleLinkageVo.setParentId("0");
		mls.add(multipleLinkageVo);
		mls.add(multipleLinkageVo);
		MultipleLinkageVo res = CovertDataUtils.setMultipleLinkageData(mls, "0");
		return res;
	}

	/**
	 * 判断验证码是否正确
	 * <p>
	 * Title: judgeCode
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param username
	 * @param code
	 * @return
	 */
	public Boolean judgeCode(String username, String code, String type) {
		if(!redisUtil.hasKey(username+type)) {
			return false;
		}else{
			Object obj = redisUtil.get(username+type);
			if(null != obj) {
				if(code.equals(String.valueOf(obj))) {
					return true;
				}
			}
			return false;
		}
		
//		String res = codeMap.get(username);
//		if (null != res && res.equals(code)) {
//			return true;
//		}
//		return false;
	}

	/**
	 * 注册时保存校友用户信息及相关信息
	 *
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@Transactional(readOnly = false)
	public ActivitiSmVo registSaveInfo(SmSchoolmate schoolmate) throws BadHanyuPinyinOutputFormatCombination {
		// 智能审核
		SysUser sysUser = new SysUser();
		// 发起注册工作流
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmSchoolmate> schoolmates = this.findSmByAuthInfo(schoolmate);
		ActivitiSmVo vo = new ActivitiSmVo();
		if (schoolmates.size() > 0) {
			SmSchoolmate oldSm = schoolmates.get(0);
			//更新校友
			if(StringUtil.isNotEmpty(schoolmate.getTruePhoto())) {
				oldSm.setTruePhoto(schoolmate.getTruePhoto());
			}
			oldSm.setCardType(schoolmate.getCardType());
			oldSm.setCardNum(schoolmate.getCardNum());
			oldSm.setSex(schoolmate.getSex());
			oldSm = smSchoolmateService.save(oldSm);
			// 数据库有值
			sysUser = schoolmates.get(0).getSysUser();
			// 更新其他数据
			ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
			// 加盐炒三次safecode=salt
			String result = new Md5Hash(schoolmate.getSysUser().getPassword(), salt, getSaltTimes()).toString();
			sysUser.setPassword(result);
			sysUser.setMobile(schoolmate.getSysUser().getUsername());
			sysUser = sysUserService.save(sysUser);
			// 更新username
			sysUser.setUsername(schoolmate.getSysUser().getUsername());
			this.updateUsernameById(sysUser);
			oldSm.setSysUser(sysUser);
			
			// 插入一条新的联系方式
			SmContact smContact = new SmContact();
			smContact.setUserId(sysUser.getId());
			// 移除其他默认联系方式
			this.updateSmconIsNotDef(smContact);
			smContact.setType(GlobalStr.PHONE);
			smContact.setContact(sysUser.getUsername());
			smContact.setIsDefault(GlobalStr.IS_DEFAULT);
			smContact = smContactService.save(smContact);
			oldSm.setSmContact(smContact);
			
			//设置院系专业
			SmEducation smEducation = schoolmate.getSmEducation();
			if(null != smEducation) {
				smEducation.setUserId(sysUser.getId());
				this.updateSmEduIsNotDef(smEducation);
				// 转换入学日期
				Calendar c = Calendar.getInstance();
				c.setTime(smEducation.getStartdate()); // 设置时间
				c.add(Calendar.MONTH, 8);
				smEducation.setStartdate(c.getTime());
				smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
				smEducation = smEducationService.save(smEducation);	
				oldSm.setSmContact(smContact);
			}		
			
			//设置默认的职业经历
			SmProfession smProfession = schoolmate.getSmProfession();
			if(null != smProfession) {
				smProfession.setUserId(sysUser.getId());
				this.updateSmProIsNotDef(smProfession);
				smProfession.setType(GlobalStr.IS_DEFAULT);
				smProfession = smProfessionService.save(smProfession);
				oldSm.setSmProfession(smProfession);
			}			
			//获取第三方登录扩展
			SysLoginExpand sysLoginExpand = schoolmate.getSysLoginExpand();
			sysLoginExpand.setUserId(sysUser.getId());
			sysLoginExpandService.save(sysLoginExpand);
			// 工作流参数
			map.put("userId", sysUser.getId());
			map.put("userName", sysUser.getUsername());
			map.put("res", GlobalStr.ACTIVITI_PASS);
			
			vo.setSchoolmate(oldSm);
		} else {
			// 保存用户信息
			sysUser.setSafecode(IdGen.uuid());
			sysUser.setUserSource(GlobalStr.USER_SOURCE_NEW_CARD);
			sysUser.setVirtualPhoto(StringUtil.isEmpty(schoolmate.getWeiChatInfo().getAvatarUrl()) ? null
					: schoolmate.getWeiChatInfo().getAvatarUrl());
			sysUser.setVirtualName(StringUtil.isEmpty(schoolmate.getWeiChatInfo().getNickName()) ? schoolmate.getName()
					: schoolmate.getWeiChatInfo().getNickName());
			sysUser.setPassword(schoolmate.getSysUser().getPassword());
			sysUser.setUsername(schoolmate.getSysUser().getUsername());
			sysUser.setMobile(schoolmate.getSysUser().getUsername());
			if (GlobalStr.ID_CARD.equals(schoolmate.getCardType())) {
				sysUser.setIdcard(schoolmate.getCardNum());
			}
			ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
			// 加盐炒三次safecode=salt
			String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
			sysUser.setPassword(result);
			sysUser.setUserType(GlobalStr.DEFAULT_USER_TYPE);
			sysUser = sysUserService.save(sysUser);
			
			
			// 插入默认校友会
			sysUserService.insertDfAs(sysUser);
			// 保存校友信息
			PinyinUtil pu = new PinyinUtil();
			schoolmate.setPinyin(pu.toPinYin(schoolmate.getName(), "", Type.LOWERCASE));
			schoolmate.setUserId(sysUser.getId());
			schoolmate.setCardStatus(GlobalStr.NO_APPLY_CARD_STATUS);
			if (GlobalStr.ID_CARD.equalsIgnoreCase(schoolmate.getCardType())) {
				schoolmate.setBirthday(InfoUtils.getBirthByIdCard(schoolmate.getCardNum()));
			}
			schoolmate = smSchoolmateService.save(schoolmate);
			schoolmate.setSysUser(sysUser);
			
			// 保存教育经历
			SmEducation smEducation = schoolmate.getSmEducation();
			if(null != smEducation) {
				// 转换入学日期
				Calendar c = Calendar.getInstance();
				c.setTime(smEducation.getStartdate()); // 设置时间
				c.add(Calendar.MONTH, 8);
				smEducation.setStartdate(c.getTime());
				smEducation.setUserId(sysUser.getId());
				smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
				smEducation = smEducationService.save(smEducation);	
				schoolmate.setSmEducation(smEducation);
			}
			//保存职业经历
			SmProfession smProfession = schoolmate.getSmProfession();
			if(null != smProfession) {
				smProfession.setUserId(sysUser.getId());
				smProfession.setType(GlobalStr.IS_DEFAULT);
				smProfession = smProfessionService.save(smProfession);
				schoolmate.setSmProfession(smProfession);
			}
			
			// 保存手机号码
			SmContact smContact = new SmContact();
			smContact.setUserId(sysUser.getId());
			smContact.setType(GlobalStr.PHONE);
			smContact.setContact(sysUser.getUsername());
			smContact.setIsDefault(GlobalStr.IS_DEFAULT);
			smContact = smContactService.save(smContact);
			schoolmate.setSmContact(smContact);
			
			//获取第三方登录扩展
			SysLoginExpand sysLoginExpand = schoolmate.getSysLoginExpand();
			sysLoginExpand.setUserId(sysUser.getId());
			sysLoginExpandService.save(sysLoginExpand);
			// 工作流参数
			map.put("userId", sysUser.getId());
			map.put("userName", sysUser.getUsername());
			map.put("res", GlobalStr.ACTIVITI_HOLD);
			
			vo.setSchoolmate(schoolmate);
		}
		// 开始一个新流程
		String processInstanceId = activitiUtils.StartProcess(GlobalStr.REG_WORKFLOW_NAME, map);
		// 执行提交审核
		activitiUtils.setVarible(map, processInstanceId, sysUser.getId());
		// 智能审核
		activitiUtils.setVarible(map, processInstanceId, GlobalStr.ACTIVITI_SYS_ASSIGNEE);
		// 获取当前认证人所在的节点
 		List<Task> list = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, sysUser.getId());
		
		if (list.size() == 0) {
			vo.setTask(null);
			return vo;
		} else {
			vo.setTask(list.get(0));
			return vo;
		}
	}

	/**
	 * 人脸
	 * @param schoolmate
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@Transactional(readOnly = false)
	public Task registFacePass(SmSchoolmate schoolmate) throws BadHanyuPinyinOutputFormatCombination {
		// 智能审核
		SysUser sysUser = new SysUser();
		sysUser.setId(schoolmate.getSysUser().getId());
		sysUser = sysUserService.get(sysUser);
		// 发起注册工作流
		Map<String, Object> map = new HashMap<String, Object>();
		// 保存用户信息
		sysUser.setSafecode(IdGen.uuid());
		sysUser.setUserSource(GlobalStr.USER_SOURCE_NEW_CARD);
//		sysUser.setVirtualPhoto(StringUtil.isEmpty(schoolmate.getWeiChatInfo().getAvatarUrl()) ? null
//				: schoolmate.getWeiChatInfo().getAvatarUrl());
		sysUser.setVirtualName(StringUtil.isEmpty(schoolmate.getWeiChatInfo().getNickName()) ? schoolmate.getName()
				: schoolmate.getWeiChatInfo().getNickName());
		sysUser.setPassword(schoolmate.getSysUser().getPassword());
		sysUser.setUsername(schoolmate.getSysUser().getUsername());
		sysUser.setMobile(schoolmate.getSysUser().getUsername());
		if (GlobalStr.ID_CARD.equals(schoolmate.getCardType())) {
			sysUser.setIdcard(schoolmate.getCardNum());
		}
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
		// 加盐炒三次safecode=salt
		String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
		sysUser.setPassword(result);
		sysUser.setUserType(GlobalStr.DEFAULT_USER_TYPE);
		sysUser = sysUserService.save(sysUser);
		// 插入默认校友会
//		sysUserService.insertDfAs(sysUser);
		// 保存校友信息
		PinyinUtil pu = new PinyinUtil();
		schoolmate.setPinyin(pu.toPinYin(schoolmate.getName(), "", Type.LOWERCASE));
		schoolmate.setUserId(sysUser.getId());
		schoolmate.setCardStatus(GlobalStr.NO_APPLY_CARD_STATUS);
		if (GlobalStr.ID_CARD.equalsIgnoreCase(schoolmate.getCardType())) {
			schoolmate.setBirthday(InfoUtils.getBirthByIdCard(schoolmate.getCardNum()));
		}
		smSchoolmateService.save(schoolmate);
		// 保存教育经历
		SmEducation smEducation = schoolmate.getSmEducation();
		if(null != smEducation) {
			// 转换入学日期
			Calendar c = Calendar.getInstance();
			c.setTime(smEducation.getStartdate()); // 设置时间
			c.add(Calendar.MONTH, 8);
			smEducation.setStartdate(c.getTime());
			smEducation.setUserId(sysUser.getId());
			smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
			smEducationService.save(smEducation);	
		}
		//保存职业经历
		SmProfession smProfession = schoolmate.getSmProfession();
		if(null != smProfession) {
			smProfessionService.save(smProfession);	
		}
		
		// 保存手机号码
		// 插入一条新的联系方式
		SmContact smContact = new SmContact();
		smContact.setUserId(sysUser.getId());
		// 移除其他默认联系方式
		this.updateSmconIsNotDef(smContact);
		smContact.setType(GlobalStr.PHONE);
		smContact.setContact(sysUser.getUsername());
		smContact.setIsDefault(GlobalStr.IS_DEFAULT);
		smContactService.save(smContact);
		//获取第三方登录扩展
		SysLoginExpand sysLoginExpand = schoolmate.getSysLoginExpand();
		sysLoginExpand.setUserId(sysUser.getId());
		sysLoginExpandService.save(sysLoginExpand);
		// 工作流参数
		map.put("userId", sysUser.getId());
		map.put("userName", sysUser.getUsername());
		map.put("res", GlobalStr.ACTIVITI_PASS);
		// 开始一个新流程
		String processInstanceId = activitiUtils.StartProcess(GlobalStr.REG_WORKFLOW_NAME, map);
		// 执行提交审核
		activitiUtils.setVarible(map, processInstanceId, sysUser.getId());
		// 智能审核
		activitiUtils.setVarible(map, processInstanceId, GlobalStr.ACTIVITI_SYS_ASSIGNEE);
		// 获取当前认证人所在的节点
		List<Task> list = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, sysUser.getId());
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}		
	}
	
	/**
	 * 更改密码
	 */
	@Transactional(readOnly = false)
	public void updateBackpwd(String username, String passward) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser = sysUserService.getPwdByUsername(sysUser);
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
		String result = new Md5Hash(passward, salt, getSaltTimes()).toString();
		sysUser.setPassword(result);
		sysUserService.save(sysUser);
		redisUtil.del(username+backSecretCodeType);
	}

	/**
	 * 再次提交认证
	 * 
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@Transactional(readOnly = false)
	public void secondSubmit(SmSchoolmate schoolmate) throws BadHanyuPinyinOutputFormatCombination {
		// 智能审核
		SysUser sysUser = new SysUser();

		Map<String, Object> map = new HashMap<String, Object>();
		List<SmSchoolmate> schoolmates = this.findSmByAuthInfo(schoolmate);
		// 更新用户
		sysUser.setId(schoolmate.getUserId());
		sysUser = sysUserService.get(sysUser);
		sysUser.setVirtualName(schoolmate.getName());
		if (GlobalStr.ID_CARD.equals(schoolmate.getCardType())) {
			sysUser.setIdcard(schoolmate.getCardNum());
		}
		sysUser = sysUserService.save(sysUser);
		// 更新校友
		PinyinUtil pu = new PinyinUtil();
		schoolmate.setPinyin(pu.toPinYin(schoolmate.getName(), "", Type.LOWERCASE));
		if (GlobalStr.ID_CARD.equalsIgnoreCase(schoolmate.getCardType())) {
			schoolmate.setBirthday(InfoUtils.getBirthByIdCard(schoolmate.getCardNum()));
		}
		smSchoolmateService.save(schoolmate);
		// 更新教育经历
		SmEducation smEducation = schoolmate.getSmEducation();
		// 转换入学日期
		Calendar c = Calendar.getInstance();
		c.setTime(smEducation.getStartdate()); // 设置时间
		c.add(Calendar.MONTH, 8);
		smEducation.setUserId(sysUser.getId());
		smEducation.setStartdate(c.getTime());
		smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
		smEducationService.save(smEducation);

		if (schoolmates.size() > 0) {
			// 工作流参数
			map.put("userId", sysUser.getId());
			map.put("userName", sysUser.getUsername());
			map.put("res", GlobalStr.ACTIVITI_PASS);
		} else {
			// 工作流参数
			map.put("userId", sysUser.getId());
			map.put("userName", sysUser.getUsername());
			map.put("res", GlobalStr.ACTIVITI_HOLD);
		}
		// 获取当前认证人所在的节点
		List<Task> list = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, sysUser.getId());
		if (list.size() != 0) {
			Task task = list.get(0);
			// 执行表单补填
			// 执行提交审核
			activitiUtils.setVarible(map, task.getProcessInstanceId(), sysUser.getId());
			// 执行提交审核
			activitiUtils.setVarible(map, task.getProcessInstanceId(), sysUser.getId());
			// 智能审核
			activitiUtils.setVarible(map, task.getProcessInstanceId(), GlobalStr.ACTIVITI_SYS_ASSIGNEE);
		}
	}

	/**
	 * 执行完整个工作流
	 */
	@Transactional(readOnly = false)
	public void updateCardStatusWorkFlow(SmSchoolmate schoolmate) {
		// 获取流程实例Id
		// 获取当前认证人所在的节点
		List<Task> list = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, schoolmate.getUserId());
		if (list.size() == 0) {
			return;
		} else {
			Task task = list.get(0);
			// 执行流程
			Map<String, Object> map = new HashMap<String, Object>();
			activitiUtils.setVarible(map, task.getProcessInstanceId(), schoolmate.getUserId());
			schoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
			this.updateSmCardStatus(schoolmate);
		}
	}

	/**
	 * 认证够3人后，此人完成认证，成为校友
	 */
	@Transactional(readOnly = false)
	public void updateCardStatusProve(String userId, String username) {
		List<HistoricTaskInstance> historicTaskInstances = activitiUtils.findHistoryTask(userId);
		if (historicTaskInstances.size() != 0) {
			for (HistoricTaskInstance pi : historicTaskInstances) {
				String processInstanceId = pi.getProcessInstanceId();
				// 执行剩余流程
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", username);
				map.put("res", GlobalStr.ACTIVITI_PASS);
				map.put("resultCon", "");
				// 执行管理员审核
				Boolean flag = activitiUtils.setVarible(map, processInstanceId, GlobalStr.SUPER_ADMIN);
				if (flag) {
					// 执行自己领卡动作
					activitiUtils.setVarible(map, processInstanceId, userId);
					SmSchoolmate schoolmate = new SmSchoolmate();
					schoolmate.setUserId(userId);
					schoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
					registMapper.updateSmCardStatus(schoolmate);
				}
			}
		}
	}

	/**
	 * 更改校友卡状态
	 */
	public void updateSmCardStatus(SmSchoolmate schoolmate) {
		registMapper.updateSmCardStatus(schoolmate);
	}

	public List<SmSchoolmate> findSmByAuthInfo(SmSchoolmate schoolmate) {
		return registMapper.findSmByAuthInfo(schoolmate);
	}

	/**
	 * 解绑手机号
	 */
	public void updateUsernameById(SysUser sysUser) {
		registMapper.updataUsernameById(sysUser);
	}

	/**
	 * 取消联系方式所有默认
	 */
	public void updateSmconIsNotDef(SmContact smContact) {
		registMapper.updataSmconIsNotDef(smContact);
	}
	
	
	/**
	 * 取消院系专业所有默认
	 */
	public void updateSmEduIsNotDef(SmEducation smEducation) {
		registMapper.updateSmEduIsNotDef(smEducation);
	}
	
	
	/**
	 * 取消职业经历所有默认
	 */
	public void updateSmProIsNotDef(SmProfession smProfession) {
		registMapper.updateSmProIsNotDef(smProfession);
	}

	/**
	 * 确认信息时获取原来的信息
	 */
	public SmSchoolmate getConfirmSmInfo(SmSchoolmate schoolmate) {
		return registMapper.getConfirmSmInfo(schoolmate);
	}

	/**
	 * 更新校友真实头像
	 * 
	 * @param schoolmate
	 */
	public void updateSmTruePhoto(SmSchoolmate schoolmate) {
		registMapper.updateSmTruePhoto(schoolmate);
	}

	/* 更新map */
	public void putMap(String username, String code, Map<String, String> codeMap) {
		codeMap.put(username, code);
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				codeMap.remove(username);
				timer.cancel();
			}
		}, EXPIRE_TIME);
	}

	/* 更新map */
	public void removeMap(String username, Map<String, String> codeMap) {
		codeMap.remove(username);
	}

	/**
	 * 获取校友卡状态
	 * @param schoolmate
	 * @return
	 */
	public SmSchoolmate getCardStatusByUserId(SmSchoolmate schoolmate) {
		return registMapper.getCardStatusByUserId(schoolmate);
	}
	
	/**
	 * 删除老数据
	 */
	@Transactional
	public void deleteOldSmInfo(SmSchoolmate schoolmate) {
		String userId = schoolmate.getUserId();
		smSchoolmateService.delete(schoolmate);
		SysUser user = new SysUser();
		user.setId(userId);
		sysService.delete(user);
	}
	
	/**
	 * 人脸搜索——获取校友基本信息
	 */
	public String getSchoolmateByFace(String fileId,String groupIdList) {
		Optional<File> file = fileService.getFileById(fileId);
		String res = baiduFaceService.searchFaceOfByte(file.get().getContent().getData(), groupIdList, null);
		JSONObject resJson = JSONObject.parseObject(res);
		JSONObject result  = resJson.getJSONObject("result");
		if(result == null ) {
			//人脸搜索失败
			return null;
		}
		JSONArray user_list = result.getJSONArray("user_list");
		String  user_id =  ((JSONObject) user_list.get(0)).getString("user_id").toString();
		return user_id;		
	}
}
