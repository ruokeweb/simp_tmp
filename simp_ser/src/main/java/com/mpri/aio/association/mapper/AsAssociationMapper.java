package com.mpri.aio.association.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.model.SysUser;

/**
 * 
 * @Description: 校友会——DAO
 * @Author: syp
 * @project sm
 * @CreateDate: Thu Jan 24 16:49:29 CST 2019
 * @Version: v_1.0
 * 
 */
@Mapper
public interface AsAssociationMapper extends CrudMapper<AsAssociation> {
	void addSysAs(SysUserAsso sysUserAsso);

	void deleteSysAs(SysUserAsso sysUserAsso);

	List<AsAssociation> loadSysAs(@Param("entity") AsAssociation asAssociation);

	Boolean selectSysAs(SysUserAsso sysUserAsso);
	/**
	 * 获取当前用户所在的校友会ID集合
	 */
	List<String> findAsIdByUser(String userId);
	
	/**
	 * 获取当前用户所在的校友会集合
	 */
	List<AsAssociation> findAsByUser(String userId);
	
	/**
	 * 插入某个校友所在的所有校友会
	 */
	void insertAsUsers (SysUser sysUser);

    int loadChildsListBy(@Param("entity") AsAssociation asAssociation);
}