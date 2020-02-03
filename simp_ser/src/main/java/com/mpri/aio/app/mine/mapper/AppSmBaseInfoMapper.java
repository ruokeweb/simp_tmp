package com.mpri.aio.app.mine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.model.SysUser;

/**
 * 校友基本信息mapper
 * @author syp
 *
 */
@Mapper
public interface AppSmBaseInfoMapper {
	
	/**
	 * 更新虚拟头像
	 * @param sysUser
	 */
	void updateVirtualPhoto(SysUser sysUser);
	
	/**
	 * 更新虚拟名称
	 * @param sysUser
	 */
	void updateVirtualName(SysUser sysUser);
	
	/**
	 * 更新密码
	 */
	void updatePassword(SysUser sysUser);
	
	/**
	 * 获取校友信息
	 */
	SmSchoolmate getSmInfoByUserId(SmSchoolmate schoolmate);
	
	
	/**
	 * 根据用户id获取校友会列表
	 */
	List<AsAssociation> loadAsByUserId(@Param("userId") String userId);
	
	
	/**
	 * 根据userId 获取校友等级
	 */
	SmSchoolmate getLevelByUserId(SmSchoolmate schoolmate);
}
