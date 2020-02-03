package com.mpri.aio.app.reg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.model.SysUser;

/**
 * 个人信息
 * @author syp
 *
 */
@Mapper
public interface RegistMapper {

	/**
	 * 通过用户名查看当前用户是否存在
	 */
	SysUser getUserByUserName(SysUser sysUser);
	
	/**
	 * 获取校友基本数据
	 */
	SmSchoolmate getSchoolmateBaseInfo(SmSchoolmate schoolmate);
	
	/**
	 * 判断当前用户是否存在
	 */
	Boolean existThisUser(String username);
	/**
	 * 判断当前用户是否是系统用户
	 */
	Boolean existSysUser(String username);
	
	/**
	 * 智能审核
	 */
	List<SmSchoolmate> findSmByAuthInfo(SmSchoolmate schoolmate);
	
	/**
	 * 更新用户名
	 */
	@Transactional(readOnly = false)
	void updataUsernameById(SysUser sysUser);
	
	/**
	 * 取消联系方式所有默认
	 */
	@Transactional(readOnly = false)
	void updataSmconIsNotDef(SmContact smContact);
	
	/**
	 * 更新校友卡状态
	 */
	void updateSmCardStatus(SmSchoolmate schoolmate);
	
	/**
	 * 确认信息时获取原来的信息
	 */
	SmSchoolmate getConfirmSmInfo(SmSchoolmate schoolmate);
	
	/**
	 * 更新校友头像
	 */
	@Transactional(readOnly = false)
	void updateSmTruePhoto(SmSchoolmate schoolmate);
	
	/**
	 * 获取校友卡状态
	 */
	SmSchoolmate getCardStatusByUserId(SmSchoolmate schoolmate);
	
	/**
	 * 取消所有职业经历默认
	 * @param smProfession
	 */
	void updateSmProIsNotDef(SmProfession smProfession);
	
	/**
	 * 取消所有院系专业默认
	 * @param smEducation
	 */
	void updateSmEduIsNotDef(SmEducation smEducation);
}
