package com.mpri.aio.system.controller;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.response.RestToken;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.LicenseAuth.LicenseExceptionResult;
import com.mpri.aio.system.LicenseAuth.LoginLicenseInterceptor;
import com.mpri.aio.system.LicenseAuth.VerifyLicense;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.*;
import com.mpri.aio.system.service.SysHistoryPasswordService;
import com.mpri.aio.system.service.SysMenuService;
import com.mpri.aio.system.service.SysRoleService;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.AESUtil;
import com.mpri.aio.system.vo.LoginVo;
import com.mpri.aio.system.vo.MenuVo;
import com.mpri.aio.system.vo.UpdatePasswordVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登陆控制器
 *
 * @author Cary
 * @Date 2018年7月27日
 */
@CrossOrigin
@RestController
public class LoginController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysHistoryPasswordService sysHistoryPasswordService;

	// 菜单根节点的父值定义为root
	private final String parentId = "root";

	// @Value("${ps.salt}")
	private int saltTimes;

	@Autowired
	private RedisCacheService redisCacheService;

	private int PASSWORD_LOSE_DAY;

	/**
	 * 管理登录
	 */
	@Logs(value = "登录", type = "LOGIN")
	@PostMapping(value = "login")
	public RestResponse<LoginVo> login(@RequestParam("username") String username,
										 @RequestParam("password") String password, @RequestParam("comeFrom") String comeFrom) {
		LoginVo loginVo = new LoginVo();
		//license证书状态判断
		String licenseFlag = LoginLicenseInterceptor.licenseFlag.get();
		if(null != licenseFlag && "0".equals(licenseFlag)){
			return new RestResponse<LoginVo>(LicenseExceptionResult.LICENSE_ERROR, "系统许可证已失效，如需继续使用请联系客服！联系电话：400-605-1065", null);
		}

		try {

			SysUser sysUser = sysUserService.getSysUserByUsername(username);
			// 确认用户是否存在
			if (sysUser != null) {
				// 存在后处理
				// 解密密码
				password = AESUtil.aesDecrypt(password);
				// 加盐处理密码
				String safeCode = sysUser.getSafecode();
				ByteSource salt = ByteSource.Util.bytes(safeCode);
				String result = new Md5Hash(password, salt, getSaltTimes()).toString();
				String userId = sysUser.getId();
				// 登陆密码校验
				if (sysUser.getPassword().equals(result)) {
					// 注册token
					String token = JWTUtil.sign(username, userId, result, comeFrom);

					// 获取token过期时间
					long tokenTime = JWTUtil.getTokenTime(token);

					// 封装token
					RestToken restToken = new RestToken();
					restToken.setToken(token);
					restToken.setTokenTime(tokenTime);

					String endDate = VerifyLicense.endDateFlag.get();
					UpdatePasswordVo updatePasswordVo = new UpdatePasswordVo();
					String regex ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{8,20}$";
					if(StringUtil.isNotEmpty(sysUser.getUserType())&&!sysUser.getUserType().equals(GlobalStr.DEFAULT_USER_TYPE)){
						if(!password.matches(regex)){
							updatePasswordVo.setFlag(true);
							updatePasswordVo.setMsg("您当前的密码存在安全风险，管理员已强制要求您更改密码，请更改!");
						}else{
							PASSWORD_LOSE_DAY =getLoseDay();
							SysHistoryPassword sysHistoryPassword = new SysHistoryPassword();
							sysHistoryPassword.setUserId(sysUser.getId());
							SysHistoryPassword historyPassword = sysHistoryPasswordService.getByUserId(sysHistoryPassword);
							if(null!=historyPassword){
								if(historyPassword.getPassword().equals(result)&&historyPassword.getIsFirstPassword().equals(GlobalStr.BOOLEAN_YES)){
									updatePasswordVo.setFlag(true);
									updatePasswordVo.setMsg("您当前的密码存在安全风险，管理员已强制要求您更改密码，请更改!");
								}else{
									int days = (int) ((new Date().getTime() - historyPassword.getCreateDate().getTime()) / (1000*3600*24));
									if(days>PASSWORD_LOSE_DAY){
										updatePasswordVo.setFlag(true);
										updatePasswordVo.setMsg("您的密码已经有超过"+PASSWORD_LOSE_DAY+"天没有修改，管理员已强制要求您更改密码，请更改!");
									}
								}
							}
						}
					}
					loginVo.setRestToken(restToken);
					loginVo.setUpdatePasswordVo(updatePasswordVo);
					if(endDate!=null){
						int internaldays = VerifyLicense.dateBetweenIncludeToday(new Date(), endDate);
						if(internaldays <= 10  && internaldays>= 1){
							return new RestResponse<LoginVo>(ExceptionResult.REQUEST_SUCCESS, String.valueOf(internaldays), loginVo);
						}
					}
					return new RestResponse<LoginVo>(ExceptionResult.REQUEST_SUCCESS, "登录成功！", loginVo);
				} else {
					return new RestResponse<LoginVo>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
//                throw new UnauthorizedException("密码错误，请重新输入");
				}

			} else {
				return new RestResponse<LoginVo>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return new RestResponse<LoginVo>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
		}

	}

	/**
	 * 首页初次加载菜单
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("refreshToken")
	public RestResponse<RestToken> refreshToken(@RequestParam("comeFrom") String comeFrom, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		// 当前时间
		Date now = new Date();
		long nowTime = now.getTime();
		// 当前token过期时间
		long tokenTime = JWTUtil.getTokenTime(token);
		String username = JWTUtil.getUsername(token);

		long freshTime = 0;

		if (comeFrom.equals(JWTUtil.FROM_WEB)) {
			freshTime = JWTUtil.REFESH_TIME;
		} else if (comeFrom.equals(JWTUtil.FROM_APP)) {
			freshTime = JWTUtil.APP_REFESH_TIME;
		} else {
			freshTime = JWTUtil.REFESH_TIME;
		}
		// 刷新token时间
		if ((tokenTime - nowTime) > 0 && (tokenTime - nowTime) < freshTime) {
			SysUser sysUser = sysUserService.getSysUserByUsername(username);
			String password = sysUser.getPassword();
			String userId = sysUser.getId();
			// 注册new token
			String newToken = JWTUtil.sign(username, userId, password, comeFrom);

			// 获取token过期时间
			long newTokenTime = JWTUtil.getTokenTime(newToken);

			// 封装token
			RestToken restToken = new RestToken();
			restToken.setToken(newToken);
			restToken.setTokenTime(newTokenTime);

			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "Token已刷新", restToken);

		} else if ((tokenTime - nowTime) < 0) {
			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "Token已经过期，请重新登陆", null);
		} else {
			// 封装token
			RestToken restToken = new RestToken();
			restToken.setToken(token);
			restToken.setTokenTime(tokenTime);
			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "token可以继续使用", restToken);
		}
	}

	/**
	 * 首页初次加载菜单
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("index")
	public Map<String, Object> index(HttpServletRequest request) {

		String username = JWTUtil.getUsername(request.getHeader("Authorization"));

		Map<String, Object> userInfo = new HashMap<String, Object>();
		// 获取用户对象
		SysUser sysUser = sysUserService.getSysUserByUsername(username);
		// 获取角色
		List<SysRole> sysRoles = sysRoleService.loadRoleByUser(sysUser.getId());
		// 获取菜单权限
		List<SysMenu> sysMenus = sysMenuService.loadMenuByUser(sysUser.getId());

		userInfo.put("user", sysUser);

		// 处理空角色
		if (null != sysRoles) {
			userInfo.put("role", sysRoles);
		}

		// 权限数据组织
		if (null != sysMenus) {
			userInfo.put("menu", installMenu(sysMenus, parentId));
		}
		return userInfo;
	}

	/**
	 * 首页初次加载权限按钮
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("getPagePer")
	public List<SysMenu> getPagePer(String menuId, HttpServletRequest request) {

		String username = JWTUtil.getUsername(request.getHeader("Authorization"));
		// 获取用户对象
		SysUser sysUser = sysUserService.getSysUserByUsername(username);

		List<SysMenu> sysmenus = sysMenuService.loadPagePer(sysUser.getId(), menuId);
		return sysmenus;
	}

	/**
	 * 加载缓存到前台
	 *
	 * @return
	 */
	@PostMapping("loadCacheMap")
	public RestResponse<Map<String, Object>> loadCacheMap() {
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		Object dictCache = (Map<String, List<SysDict>>) redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		Object areaCache = (Map<String, List<SysArea>>) redisCacheService.getCache(new SysArea(), InitCache.AREA_CACHE);
		Object departCache = (Map<String, List<SettingDepartment>>) redisCacheService.getCache(new SettingDepartment(),
				InitCache.DEPART_CACHE);
		Object departBaseCache = (Map<String, SettingDepartment>) redisCacheService.getBaseCache(new SettingDepartment(),
				InitCache.DEPART_BASE_CACHE);
		cacheMap.put("dictCache", dictCache);
		cacheMap.put("departCache", departCache);
		cacheMap.put("areaCache", areaCache);
		cacheMap.put("departBaseCache", departBaseCache);
		return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "缓存数据获取成功", cacheMap);

	}

	@GetMapping(path = "/401")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public RestResponse<String> unauthorized() {
		return new RestResponse<>(0, "Unauthorized", null);
	}

	/**
	 * 筛选组织menu
	 *
	 * @param menuList
	 * @return
	 */
	public MenuVo installMenu(List<SysMenu> menuList, String parentId) {

		MenuVo newMenu = new MenuVo();
		// 获取顶级菜单
		for (SysMenu menu : menuList) {
			if (menu.getParentId().equals(parentId)) {
				List<MenuVo> mvl = new ArrayList<>();
				newMenu.setId(menu.getId());
				newMenu.setName(menu.getName());
				newMenu.setCode(menu.getCode());
				newMenu.setIcon(menu.getIcon());
				newMenu.setHref(menu.getHref());
				newMenu.setType(menu.getType());
				newMenu.setParentId(menu.getParentId());
				newMenu.setPermission(menu.getPermission());
				// 获取子对象
				newMenu.setChildren(getChildrenMenu(menu.getId(), menuList));
				mvl.add(newMenu);
				break;
			}
		}
		return newMenu;
	}

	/**
	 * 获取当前节点的所有子节点
	 *
	 * @param nodeId
	 * @param nodes
	 * @return
	 */
	public static List<MenuVo> getChildrenMenu(String menuId, List<SysMenu> menuList) {
		List<MenuVo> mvl = new ArrayList<>();
		for (SysMenu menu : menuList) {
			if (menu.getParentId().equals(menuId)) {
				MenuVo newMenu = new MenuVo();
				newMenu.setId(menu.getId());
				newMenu.setName(menu.getName());
				newMenu.setCode(menu.getCode());
				newMenu.setIcon(menu.getIcon());
				newMenu.setHref(menu.getHref());
				newMenu.setType(menu.getType());
				newMenu.setParentId(menu.getParentId());
				newMenu.setPermission(menu.getPermission());
				newMenu.setChildren(getChildrenMenu(menu.getId(), menuList));
				mvl.add(newMenu);
			} else {
				continue;
			}
		}

		return mvl;
	}

	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		saltTimes = Integer.parseInt(list.get(0).getValue());
		return saltTimes;
	}
	public int getLoseDay() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "PASSWORD_LOSE_DAY");
		PASSWORD_LOSE_DAY = Integer.parseInt(list.get(0).getValue());
		return PASSWORD_LOSE_DAY;
	}

	public void setSaltTimes(int saltTimes) {
		this.saltTimes = saltTimes;
	}
}
