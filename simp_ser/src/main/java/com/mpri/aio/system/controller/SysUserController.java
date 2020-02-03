package com.mpri.aio.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mpri.aio.system.model.SysHistoryPassword;
import com.mpri.aio.system.service.SysHistoryPasswordService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.FileUtils;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.AESUtil;
import com.mpri.aio.system.utils.RedisUtil;

/**
 * 用户登陆控制器
 * 
 * @author Cary
 * @date 2018年9月7日
 */

@RestController
@RequestMapping("/sys/sysuser")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private AsAssociationService asAssociationService;
	
	@Autowired
	private SmSchoolmateService smSchoolmateService;

	@Value("${file.staticAccessPath}")
	private String staticAccessPath;

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	// @Value("${ps.salt}")
	private int saltTimes;

	/* 初始没有身份证号的密码 */
	private static final String DEFAULT_PWD = "DF" + DateUtils.getYear();

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private SysHistoryPasswordService sysHistoryPasswordService;

	/**
	 * 获取用户列表
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param SysUser
	 * @return PageIo<SysUser>
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageIo<SysUser> list(int pageNo, int pageSize, SysUser SysUser,HttpServletRequest request) {
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		if(StringUtil.isNotEmpty(userId)) {
			SysUser.setId(userId);
		}
		PageIo<SysUser> pageInfo = sysUserService.loadByPage(pageNo, pageSize, SysUser);
		return pageInfo;
	}

	/**
	 * 增加或者更新用户
	 * <p>
	 * Title: add
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param SysUser
	 * @return RestResponse<String>
	 */
	@Logs(value = "用户修改", type = "UPDATE")
	@CrossOrigin
	@RequestMapping(value = "/save")
	public RestResponse<String> save(@RequestBody @Validated SysUser sysUser) {

			boolean isNew = false;
		if ((null == sysUser.getId() || "".equals(sysUser.getId()))) {
			sysUser.setSafecode(IdGen.uuid());
			sysUser.setPassword(initPwd(sysUser.getIdcard()));
			ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
			// 加盐炒三次safecode=salt
			String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
			sysUser.setPassword(result);
			isNew = true;
		}
		SysUser saveUser = new SysUser();
		saveUser =sysUserService.save(sysUser);
		sysUserService.insertUserRole(sysUser);
		if(sysUser.getAsList().size() != 0) {
			asAssociationService.insertAsUsers(sysUser);
		}else {
			sysUserService.insertDfAs(sysUser);	
		}
		if(!saveUser.getUserType().equals(GlobalStr.DEFAULT_USER_TYPE)){
			SysHistoryPassword sysHistoryPassword = new SysHistoryPassword();
			sysHistoryPassword.setUserId(sysUser.getId());
			SysHistoryPassword historyPassword = sysHistoryPasswordService.getByUserId(sysHistoryPassword);
			if(historyPassword==null||!historyPassword.getPassword().equals(saveUser.getPassword())){
				sysHistoryPassword.setPassword(saveUser.getPassword());
				if (isNew) {
					sysHistoryPassword.setIsFirstPassword(GlobalStr.BOOLEAN_YES);
				}else{
					sysHistoryPassword.setIsFirstPassword(GlobalStr.BOOLEAN_NO);
				}
				sysHistoryPasswordService.save(sysHistoryPassword);
			}
		}
		redisUtil.delLikeKey("menuCache","perCache","pagePerCache","userCache");
		RestResponse<String> rs = new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");

		return rs;
	}

	/**
	 * 删除用户（逻辑删除）
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param SysUser
	 * @return RestResponse<String>
	 */
	@Logs(value = "用户删除", type = "DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	// @CacheEvict(value = "userCache",allEntries=true)
	public RestResponse<String> delete(SysUser sysUser) {
		SysUser user = sysUserService.get(sysUser);
		smSchoolmateService.deleteSmByUser(user);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");

	}

	/**
	 * 根据id获取用户
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @return RestResponse<SysUser>
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysUser> get(SysUser sysUser) {
		return new RestResponse<SysUser>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysUserService.get(sysUser));
	}

	/**
	 * 根据username获取用户
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param username
	 * @return RestResponse<String>
	 */
	@CrossOrigin
	@PostMapping(value = "/getusername")
	public RestResponse<String> getSysUserByUsername(@RequestParam("username") String username) {

		SysUser sysUser = sysUserService.getSysUserByUsername(username);
		if (null == sysUser) {
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "用户名不存在！", null);
		} else {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "用户名已经存在！", null);
		}

	}

	/**
	 * 根据username获取用户
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param username
	 * @return RestResponse<String>
	 */
	@CrossOrigin
	@PostMapping(value = "/checkUserExist")
	public RestResponse<String> checkUserExist(@RequestParam("username") String username) {
		int userNum = sysUserService.getUserNum(username);
		if (userNum > 0) {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "用户名已经存在！", null);
		} else {
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "用户名不存在！", null);
		}

	}

	/**
	 * 文件图上传
	 * <p>
	 * Title: uploadImg
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/uploadimg")
	public RestResponse<String> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		String newFilName = String.valueOf(System.currentTimeMillis()) + "."
				+ fileName.substring(fileName.lastIndexOf(".") + 1); /* 更改文件名 */
		String resfillPath = DateUtils.getDate();
		String filePath = uploadFolder + resfillPath + "/";
		try {
			FileUtils.uploadFile(file.getBytes(), filePath, newFilName);
			return RestResponse.getInstance(ExceptionResult.REQUEST_SUCCESS, "上传成功",
					staticAccessPath.replaceAll("\\*", "") + resfillPath + "/" + newFilName);
		} catch (Exception e) {
			return RestResponse.getInstance(ExceptionResult.SYS_ERROR, "上传失败", resfillPath + fileName);
		}
	}

	/**
	 * 检查旧密码
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysUser
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/checkOldPwd")
	public RestResponse<String> checkOldPwd(SysUser sysUser, @RequestParam("oldPwd") String oldPwd) {
		Boolean check = checkPwd(sysUser, oldPwd);
		if (check) {
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "旧密码正确！", null);
		}
		return new RestResponse<String>(ExceptionResult.NOT_FOUND, "旧密码输入有误！", null);
	}

	/**
	 * 修改密码
	 * 
	 * @param sysUser
	 * @param newPwd
	 * @return
	 */
	@Logs(value = "修改密码", type = "OTHER")
	@CrossOrigin
	@PostMapping(value = "/changePwd")
	public RestResponse<String> changePwd(SysUser formUser, @RequestParam("newPwd") String newPwd,
			@RequestParam("oldPwd") String oldPwd) {
		Boolean check = checkPwd(formUser, oldPwd);
		if (check) {
			SysUser oldUser = sysUserService.getPwdByUsername(formUser);
			ByteSource salt = ByteSource.Util.bytes(oldUser.getSafecode());

			newPwd = AESUtil.aesDecrypt(newPwd);

			// 加盐炒safecode=salt
			String result = new Md5Hash(newPwd, salt, getSaltTimes()).toString();

			oldUser.setPassword(result);
			sysUserService.save(oldUser);
			if(StringUtil.isNotEmpty(oldUser.getUserType())&&!oldUser.getUserType().equals(GlobalStr.DEFAULT_USER_TYPE)){
				SysHistoryPassword sysHistoryPassword = new SysHistoryPassword();
				sysHistoryPassword.setUserId(oldUser.getId());
				sysHistoryPassword.setPassword(result);
				sysHistoryPassword.setIsFirstPassword(GlobalStr.BOOLEAN_NO);
				sysHistoryPasswordService.save(sysHistoryPassword);
			}
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "密码修改成功！", null);
		}
		return new RestResponse<String>(ExceptionResult.SYS_ERROR, "密码修改失败！", null);
	}

	/**
	 * 重置密码
	 * <p>
	 * Title: resetpwd
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysUser
	 * @return
	 */
	@Logs(value = "重置密码", type = "OTHER")
	@CrossOrigin
	@PostMapping(value = "/resetPwd")
	public RestResponse<String> resetpwd(SysUser sysUser) {
		SysUser resUser = sysUserService.get(sysUser);
		resUser.setPassword(initPwd(resUser.getIdcard()));
		ByteSource salt = ByteSource.Util.bytes(resUser.getSafecode());
		// 加盐炒三次safecode=salt
		String result = new Md5Hash(resUser.getPassword(), salt, getSaltTimes()).toString();
		resUser.setPassword(result);
		sysUserService.save(resUser);
		if(StringUtil.isNotEmpty(resUser.getUserType())&&!resUser.getUserType().equals(GlobalStr.DEFAULT_USER_TYPE)){
			SysHistoryPassword sysHistoryPassword = new SysHistoryPassword();
			sysHistoryPassword.setUserId(resUser.getId());
			SysHistoryPassword byUserId = sysHistoryPasswordService.getByUserId(sysHistoryPassword);
		//	if(null == byUserId||byUserId.getIsFirstPassword().equals(GlobalStr.BOOLEAN_NO)||!byUserId.getPassword().equals(result)){
				sysHistoryPassword.setPassword(result);
				sysHistoryPassword.setIsFirstPassword(GlobalStr.BOOLEAN_YES);
				sysHistoryPasswordService.save(sysHistoryPassword);
		//	}
		}

 		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "重置密码成功！", null);
	}

	@CrossOrigin
	@PostMapping(value = "/getAsIdsByUser")
	public RestResponse<List<String>> getAsIdsByUser(@RequestParam("id") String id){
		List<String> assoIds = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)) {
			assoIds = asAssociationService.findAsIdByUser(id);
		}else {
			assoIds.add(GlobalStr.DEFAULT_AS);
		}
		return new RestResponse<List<String>>(ExceptionResult.REQUEST_SUCCESS, "获取数据成功！", assoIds);
	}	
	
	
	/**
	 * 获取已经更新的用户
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/findUserListByUpdate") 
	public RestResponse<List<SysUser>> findUserListByUpdate(@RequestParam("code") String code){
		try {
			code = AESUtil.aesDecrypt(URLDecoder.decode(code,"UTF-8"));
			String transferCode =  getTransferCode();
			if(StringUtil.isNotEmpty(code) && code.equals(transferCode)) {
				SysUser user = new SysUser();
				//已经在生产者中被更新
				user.setUpdateFlag(GlobalStr.BOOLEAN_YES);
				List<SysUser> list = sysUserService.loadAllByUpdate(user);
				return new RestResponse<List<SysUser>>(ExceptionResult.REQUEST_SUCCESS, "获取数据成功！", list);
			}
		} catch (UnsupportedEncodingException e) {
			return new RestResponse<List<SysUser>>(ExceptionResult.NO_PERMISSION, "鉴权失败！", null);	
		}
		return new RestResponse<List<SysUser>>(ExceptionResult.NO_PERMISSION, "鉴权失败！", null);		
	} 
	
	/**
	 * 更新已经同步的数据为未更新
	 */
	@CrossOrigin
	@PostMapping(value = "/updateSynchronizedUser") 
	public RestResponse<String> updateSynchronizedUser(@RequestParam("code") String code){
		try {
			code = AESUtil.aesDecrypt(URLDecoder.decode(code,"UTF-8"));
			String transferCode =  getTransferCode();
			if(StringUtil.isNotEmpty(code) && code.equals(transferCode)) {
				SysUser user = new SysUser();
				//已经在生产者中被更新
				user.setUpdateFlag(GlobalStr.BOOLEAN_NO);
				sysUserService.updateSynchronizedUser(user);
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "更新数据成功！", "");
			}
		} catch (UnsupportedEncodingException e) {
			return new RestResponse<String>(ExceptionResult.NO_PERMISSION, "鉴权失败！", null);	
		}
		return new RestResponse<String>(ExceptionResult.NO_PERMISSION, "鉴权失败！", null);	
	}
	
	/**
	 * 初始化密码
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

	/**
	 * 验证密码
	 * 
	 * @param oldUser
	 * @param newUser
	 * @return
	 */
	private Boolean checkPwd(SysUser formUser, String oldPwd) {
		SysUser oldUser = sysUserService.getPwdByUsername(formUser);
		ByteSource salt = ByteSource.Util.bytes(oldUser.getSafecode());
		oldPwd = AESUtil.aesDecrypt(oldPwd);

		// 加盐炒safecode=salt
		String result = new Md5Hash(oldPwd, salt, getSaltTimes()).toString();

		if (result.equals(oldUser.getPassword())) {
			return true;
		}
		return false;
	}
	
	
	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		saltTimes = Integer.parseInt(list.get(0).getValue());
		return saltTimes;
	}

	public String getTransferCode(){
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "transfer.code");
		String code = (list.get(0).getValue());
		return code;
	}	
	
	public void setSaltTimes(int saltTimes) {
		this.saltTimes = saltTimes;
	}
}
