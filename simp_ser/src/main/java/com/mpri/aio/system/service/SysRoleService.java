package com.mpri.aio.system.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.common.page.PageIo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.mapper.SysRoleMapper;
import com.mpri.aio.system.model.SysRole;
import com.mpri.aio.system.model.SysUser;
/**
 * 
 * @author Cary
 * @Date  2018年7月31日
 */
@Service
public class SysRoleService  extends CrudService<SysRoleMapper, SysRole>{
	
	/**
	 * 根据用户id获取角色集合
	 * @param username
	 * @return
	 */
//	@Cacheable(value = "roleCache", key = "#id")
	public List<SysRole> loadRoleByUser(String id) {
		SysUser sysUser=new SysUser();
		sysUser.setId(id);
		return mapper.loadRoleByUser(sysUser);
	}
	
	/**
	 * 
	 * <p>Title: insertRoleMenu</p>  
	 * <p>Description: </p>
	 * @param sysRole
	 */
	@Transactional(readOnly = false)
	public void saveRoleMenu(SysRole sysRole) {
		mapper.deleteRoleMenu(sysRole);
		mapper.insertRoleMenu(sysRole);
	}
	
	/**
	 * 
	 * <p>Title: deleteRoleMenu</p>  
	 * <p>Description: </p>  
	 * @param sysRole
	 */
	@Transactional(readOnly = false)
	public void deleteRoleMenu(SysRole sysRole) {
		mapper.deleteRoleMenu(sysRole);
	}

	/**
	 * 去除超级管理员
	 * @param pageNo
	 * @param pageSize
	 * @param sysRole
	 * @return
	 */
	public PageIo<SysRole> loadByPageNotSupper(int pageNo, int pageSize, SysRole sysRole) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SysRole> pageList = this.mapper.loadByPageNotSupper(sysRole);
		PageIo<SysRole> pageInfo = new PageIo(pageList);
		return pageInfo;
	}
}