package com.mpri.aio.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.model.SysRole;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysRoleService;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.model.SysMenu;
import com.mpri.aio.system.service.SysMenuService;

import javax.servlet.http.HttpServletRequest;

/**
 * 
* <p>Title: SysMenuController</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年8月13日
 */
@RestController
@RequestMapping("sys/sysmenu")
public class SysMenuController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;
	
	
	/**
	 * 
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysMenu
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public ResJson<SysMenu> list(SysMenu sysMenu , HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		sysUser=sysUserService.get(sysUser);//得到登陆用户的信息
		boolean isSuperAdmin = false;
		List<SysMenu> list = new ArrayList<>();
		List<SysRole> roleList = sysUser.getRoleList();
		//根据userId 得到该用户的角色
		if(sysUser.getUsername().equals(GlobalStr.SUPER_ADMIN)){
			if(roleList!=null){
				for (SysRole sysRole:roleList) {
					if(sysRole.getCode().equals(GlobalStr.SUPER_ADMIN)){
						isSuperAdmin = true;
						list =sysMenuService.loadAllListBy(sysMenu);
					}
				}
			}
		}
		if(!isSuperAdmin){
			list =sysMenuService.loadAllListByRoleId(roleList,sysMenu);
		}
		ResJson<SysMenu> rj = new ResJson<SysMenu>();
		rj.setData(list);
		return rj;
	}
	
	
	/**
	 * 
	* <p>Title: save</p>  
	* <p>Description: </p>  
	* @param sysMenu
	* @return
	 */
	@Logs(value = "菜单修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	//@RequiresAuthentication
	public RestResponse<String> save(@Validated SysMenu sysMenu){
		if("".equals(sysMenu.getParentId())|| null == sysMenu.getParentId()) {
			//setRoot 目录
		}else {
			SysMenu parentSysMenu = new SysMenu();
			parentSysMenu.setId(sysMenu.getParentId());
			parentSysMenu = sysMenuService.get(parentSysMenu);
			sysMenu.setParentIds(parentSysMenu.getParentIds()+","+parentSysMenu.getId());
		}
		sysMenuService.save(sysMenu);
		sysMenuService.setChildIsShow(sysMenu);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param sysMenu
	* @return
	 */
	@Logs(value = "菜单删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysMenu sysMenu) {
		sysMenuService.delete(sysMenu);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param sysMenu
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysMenu> get(SysMenu sysMenu) {
		SysMenu resSysMenu = sysMenuService.get(sysMenu);
		SysMenu parentSysMenu = new SysMenu();
		parentSysMenu.setId(resSysMenu.getParentId());
		resSysMenu.setParentSysMenu(sysMenuService.get(parentSysMenu));
		return new RestResponse<SysMenu>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", resSysMenu);
	
	}
}
