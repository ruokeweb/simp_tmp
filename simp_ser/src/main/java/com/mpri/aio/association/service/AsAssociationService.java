package com.mpri.aio.association.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.association.mapper.AsAssociationMapper;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.utils.DatasCovert;
import com.mpri.aio.schoolmate.vo.FormSelectDatas;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.common.UserInfoUtil;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysRole;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;

/**
 * @Description: 校友会——Service
 * @Author: syp
 * @project sm
 * @CreateDate: Thu Jan 24 16:49:29 CST 2019
 * @Version: v_1.0
 */
@Service
public class AsAssociationService extends CrudService<AsAssociationMapper, AsAssociation> {

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private SysUserService sysUserService;

	public void addSysAs(SysUserAsso sysUserAsso) {
		mapper.addSysAs(sysUserAsso);
	}

	public void deleteSysAs(SysUserAsso sysUserAsso) {
		this.mapper.deleteSysAs(sysUserAsso);
	}

	public PageIo<AsAssociation> loadSysAs(int pageNo, int pageSize, AsAssociation asAssociation) {
		PageHelper.startPage(pageNo, pageSize);
		List list = this.mapper.loadSysAs(asAssociation);
		PageIo<AsAssociation> pageInfo = new PageIo(list);
		return pageInfo;
	}

	public Boolean selectSysAs(SysUserAsso sysUserAsso) {
		return this.mapper.selectSysAs(sysUserAsso);
	}

	/**
	 * 获取当前用户所在的校友会ID集合
	 */
	public List<String> findAsIdByUser(String userId) {
		return mapper.findAsIdByUser(userId);
	}

	/**
	 * 插入某个校友所在的所有校友会
	 */
	@Transactional(readOnly = false)
	public void insertAsUsers(SysUser sysUser) {
		SysUserAsso sysUserAsso = new SysUserAsso();
		sysUserAsso.setUserId(sysUser.getId());
		mapper.deleteSysAs(sysUserAsso);
		mapper.insertAsUsers(sysUser);
	}

	/**
	 * 获取当前用户所在的校友会ID集合
	 * 
	 * @return
	 */
	public List<String> getAsIdsByUser(HttpServletRequest request) {
		String userId = UserInfoUtil.getUserId(request);
		List<String> assoIds = new ArrayList<String>();
		if (StringUtil.isNotEmpty(userId)) {
			assoIds = this.findAsIdByUser(userId);
		} else {
			assoIds.add(GlobalStr.DEFAULT_AS);
		}
		return assoIds;
	}

	/**
	 * 获取当前用户所在的校友会集合
	 */
	public List<AsAssociation> findAsByUser(HttpServletRequest request) {
		String userId = UserInfoUtil.getUserId(request);
		List<AsAssociation> assoIds = new ArrayList<AsAssociation>();
		if (StringUtil.isNotEmpty(userId)) {
			assoIds = mapper.findAsByUser(userId);
		}
		return assoIds;
	}
	
	/**
	 * 判断当前管理员是否有超级管理员权限
	 */
	public Boolean judgeUserIsAdmin(HttpServletRequest request) {
		String userId = UserInfoUtil.getUsername(request);
		if(StringUtil.isNotEmpty(userId)) {
			SysUser sysUser = new SysUser();
			sysUser.setId(userId);
			sysUser = sysUserService.get(sysUser);
			List<SysRole> roles = sysUser.getRoleList();
			for (SysRole role : roles) {
				if(role.getCode().equalsIgnoreCase(GlobalStr.SUPER_ADMIN)) {
					return true;
				}
			}
		}
		return false;
	}

    public int loadChildsListBy(AsAssociation asAssociation) {
		return this.mapper.loadChildsListBy(asAssociation);
	}
}