package com.mpri.aio.system.controller;

import java.util.List;

import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.model.SysRole;
/**
 * 
* <p>Title: SysRoleController</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年8月18日
 */
import com.mpri.aio.system.service.SysRoleService;
import com.mpri.aio.system.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("sys/sysrole")
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sysRole
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageIo<SysRole> list(int pageNo, int pageSize, SysRole sysRole, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		sysUser=sysUserService.get(sysUser);//得到登陆用户的信息
		PageIo<SysRole> pageInfo = null;
		boolean isSuperAdmin = false;
		List<SysRole> roleList = sysUser.getRoleList();
		if(roleList!=null){
			for (SysRole role:roleList) {
				if(role.getCode().equals(GlobalStr.SUPER_ADMIN)){
					//这个超级管理员
					isSuperAdmin = true;
					pageInfo = sysRoleService.loadByPage(pageNo, pageSize, sysRole);
				}
			}
		}
		if(!isSuperAdmin){
			pageInfo = sysRoleService.loadByPageNotSupper(pageNo, pageSize, sysRole);
		}
		return pageInfo;
	}

	// 获取系统所有角色
	@CrossOrigin
	@PostMapping(value = "/loadAll")
	public RestResponse<List<SysRole>> loadAllBy(SysRole sysRole) {
		List<SysRole> roleList = sysRoleService.loadAllListBy(sysRole);
//		for (SysRole role : roleList) {
//			role.setName(StringEscapeUtils.unescapeHtml4(role.getName()));;
//		}
		return new RestResponse<List<SysRole>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", roleList);
	}

	/**
	 * 
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysDict
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	// @Cacheable(value = "dictCache", key = "#sysDict.id")
	public RestResponse<SysRole> get(SysRole sysRole) {

		return new RestResponse<SysRole>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysRoleService.get(sysRole));

	}

	/**
	 * 
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysRole
	 * @return
	 */
	@Logs(value = "角色删除", type = "DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysRole sysRole) {
		sysRoleService.deleteRoleMenu(sysRole);
		sysRoleService.delete(sysRole);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");

	}

	/**
	 * 
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysRole
	 * @return
	 */
	@Logs(value = "角色修改", type = "UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SysRole sysRole) {
		sysRoleService.save(sysRole);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}

	/**
	 * 
	 * <p>
	 * Title: insertRoleMenu
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysRole
	 * @return
	 */
	@Logs(value = "角色权限", type = "OTHER")
	@CrossOrigin
	@PostMapping(value = "/saveRoleMenu")
	public RestResponse<String> saveRoleMenu(@RequestBody SysRole sysRole) {
		sysRoleService.saveRoleMenu(sysRole);
		redisUtil.delLikeKey("menuCache","perCache","pagePerCache");
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}
}
