package com.mpri.aio.app.reg.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mpri.aio.settings.service.PointLoginSignService;
import org.activiti.engine.task.Task;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.mine.service.AppSchoomateBaseInfoService;
import com.mpri.aio.app.reg.model.SmSchoolmateProve;
import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.service.RegistService;
import com.mpri.aio.app.reg.service.SmSchoolmateProveService;
import com.mpri.aio.app.reg.service.SysLoginExpandService;
import com.mpri.aio.app.reg.vo.ActivitiSmVo;
import com.mpri.aio.app.reg.vo.MultipleLinkageVo;
import com.mpri.aio.app.utils.service.TemplateUtils;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.exception.UnauthorizedException;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.MD5Util;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.AESUtil;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 注册登录相关接口
 * @author syp
 *
 */
@RestController
@RequestMapping("/app")
@CrossOrigin
public class RegistController extends BaseController{

	@Autowired
	private RedisCacheService redisCacheService;
	
	@Autowired
	private RegistService registService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SmSchoolmateService smSchoolmateService;
	
	@Autowired
	private ActivitiUtils activitiUtils;
	
	@Autowired
	private SmSchoolmateProveService smSchoolmateProveService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SysLoginExpandService sysLoginExpandService;

	@Autowired
	private PointLoginSignService pointLoginSignService;
	
	
	private String groupIdList ="xiaoyou_001";
	
	@Autowired
	private AppSchoomateBaseInfoService appSchoomateBaseInfoService;
	
	/**
	 * 使用Openid 去登录
	 */
	@PostMapping(value="/loginByOpenId")
	public RestResponse<Map<String,Object>> loginByOpenId(@RequestParam("openid") String openid,@RequestParam("comeFrom")String comeFrom){
		//1、登录扩展表查询是否有匹配的信息
		Map<String,Object> userInfo=new HashMap<String,Object>(); 
		SysLoginExpand sysLoginExpand = new SysLoginExpand();
		sysLoginExpand.setType(GlobalStr.LOGIN_SOURCE);
		sysLoginExpand.setExpand(openid);
		List<SysLoginExpand> list = sysLoginExpandService.loadAllListBy(sysLoginExpand);
		if(list.size() > 0) {
			SysLoginExpand expand  = list.get(0);
			String userId = expand.getUserId();
			SysUser user = new SysUser();
			user.setId(userId);
			user = sysUserService.get(user);
			userInfo = getUserInfoByUserId(user, comeFrom);
			return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "登录成功！", userInfo);
			
		}
		return new RestResponse<Map<String,Object>>(ExceptionResult.NO_PERMISSION, "openid 不存在，请使用账号登录！", null);
	}
	
	
	/**
	 * 登录
	 */
	@PostMapping(value="/login")
	public RestResponse<Map<String,Object>> login(@RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("comeFrom")String comeFrom,
            @RequestParam("openid") String openid){
		Map<String,Object> userInfo=new HashMap<String,Object>();
		SysUser sysUser=registService.getUserByUserName(username);
        //确认用户是否存在
        if(sysUser!=null) {
            //解密密码
            password=AESUtil.aesDecrypt(password);
            //加盐处理密码
            String safeCode=sysUser.getSafecode();
            ByteSource salt = ByteSource.Util.bytes(safeCode);
            //加盐次数
            int saltTimes = registService.getSaltTimes();
            String result = new Md5Hash(password,salt,saltTimes).toString();
            String userId =sysUser.getId();
            //登陆密码校验
            if (sysUser.getPassword().equals(result)) {
            	/**
            	 * 获取校友基本数据
            	 */
				userInfo = getUserInfoByUserId(sysUser, comeFrom);
                //更新OpenId
        		SysLoginExpand sysLoginExpand = new SysLoginExpand();
        		sysLoginExpand.setType(GlobalStr.LOGIN_SOURCE);
        		sysLoginExpand.setUserId(userId);
        		List<SysLoginExpand> list = sysLoginExpandService.loadAllListBy(sysLoginExpand);
        		if(list.size() > 0) {
        			sysLoginExpand = list.get(0);
        			sysLoginExpand.setExpand(openid);
        			sysLoginExpandService.save(sysLoginExpand);
        		}else {
        			sysLoginExpand.setExpand(openid);
        			sysLoginExpandService.save(sysLoginExpand);
        		}
                
                return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "登录成功！", userInfo);
            } else {
                throw new UnauthorizedException("账号或密码错误，请重新输入");
            }
        }else {
            return new RestResponse<Map<String,Object>>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
        }
	}
	/**
	 * 根据userid获取userinfo
	 */
	private  Map<String,Object> getUserInfoByUserId(SysUser user,String comeFrom){
		Map<String,Object> userInfo=new HashMap<String,Object>();
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId(user.getId());
		schoolmate= registService.getSchoolmateBaseInfo(schoolmate);
		//注册token
		String token=JWTUtil.sign(user.getUsername(),user.getId(), user.getPassword(),comeFrom);
		//获取token过期时间
		long tokenTime= JWTUtil.getTokenTime(token);
		userInfo.put("token", token);  //返回token
		userInfo.put("tokenTime", tokenTime); //返回token时间
		userInfo.put("userInfo", schoolmate);
		/**
		 *	保存登录记录
		 */
		pointLoginSignService.saveLoginSign(user.getId());
		return  userInfo;
	}
	
	
	/**
	 * 获取基础数据
	 */
	@PostMapping(value="/getBaseDatas")
	public RestResponse<Map<String,Object>> getBaseDatas() {
		Map<String,Object> baseData=new HashMap<String,Object>();
		
		Object dict  =(Map<String, List<SysDict>>) redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		//key Object
		Object areaBase  =(Map<String,SysArea>)redisCacheService.getKeyCache(new SysArea(), InitCache.AREA_KEY_CACHE);
		Object departmentBase = (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		Object sysIndustryBase = (Map<String,SysIndustry>)redisCacheService.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE);
		Object settingSubjectBase = (Map<String,SettingSubject>)redisCacheService.getBaseCache(new SysIndustry(), InitCache.SETTING_BASE_SUBJECT);
		baseData.put("dict", dict);
		baseData.put("areaBase", areaBase);
		baseData.put("departmentBase", departmentBase);
		baseData.put("sysIndustryBase", sysIndustryBase);
		baseData.put("settingSubjectBase", settingSubjectBase);		
		baseData.put("multipleLinkageDepartments", registService.getMultipleLinkageDepartments());
		baseData.put("multipleLinkageSubject", registService.getMultipleLinkageSubject());
		baseData.put("multipleLinkageIndustry", registService.getMultipleLinkageIndustry());
		baseData.put("multipleLinkageAddress", registService.getMultipleLinkageAddress());
		return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "基础数据获取成功", baseData);
	}
	
	/**
	 * 注册发送验证码
	 */
	@PostMapping(value = "/sendCaptCha")
	public RestResponse<String> sendCaptCha(@RequestParam("username") String username) {
		if (StringUtil.isNotEmpty(username)) {
			Boolean existThisUser = registService.existThisUser(username);
			if (existThisUser) {
				return new RestResponse<String>(ExceptionResult.DATA_USED, "该手机已注册，请到登录页面进行登录", "");
			}
		}
		try {
			String res = registService.sendRegistCaptCha(username);
			if (res.equalsIgnoreCase(GlobalStr.SUCCESS)) {
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "请查看手机验证码", "");
			} else {
				return new RestResponse<String>(ExceptionResult.SYS_ERROR, "请检查手机号和网络环境", "");
			}
		} catch (Exception e) {
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "请检查手机号和网络环境", "");
		}
	}

	/**
	 * 找回密码发送验证码
	 */
	@PostMapping(value = "/sendBackPwdCaptCha")
	public RestResponse<String> sendBackPwdCaptCha(@RequestParam("username") String username) {
		try {
			if (StringUtil.isNotEmpty(username)) {
				Boolean existThisUser = registService.existThisUser(username);
				if (existThisUser) {
					Boolean existSysUser = registService.existSysUser(username);
					if(existSysUser){
						return new RestResponse<String>(ExceptionResult.SYS_ERROR, "您是系统管理员，请到系统管理端修改密码！", "");
					}else{
						String res = registService.sendBackPwdCaptCha(username);
						if (res.equalsIgnoreCase(GlobalStr.SUCCESS)) {
							return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "请查看手机验证码", "");
						} else {
							return new RestResponse<String>(ExceptionResult.SYS_ERROR, "请检查手机号和网络环境", "");
						}
					}

				}
			}
		} catch (Exception e) {
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "请检查手机号和网络环境", "");
		}
		return new RestResponse<String>(ExceptionResult.SYS_ERROR, "请输入正确的用户名", "");
	}
	
	/**
	 * 找回密码功能
	 */
	@PostMapping(value="/updateBackPwd")
	public RestResponse<String> updateBackPwd(@RequestParam("username") String username,@RequestParam("captChaCode") String captChaCode,
			@RequestParam("password") String password){
    	//验证找回密码验证码
    	Boolean flag = registService.judgeCode(username,captChaCode, RegistService.backSecretCodeType);
    	if(flag) {
    		//解密
    		String newPwd = AESUtil.aesDecrypt(password);
    		registService.updateBackpwd(username, newPwd);
    		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "密码修改成功！请去登录", "");
    	}
		return new RestResponse<String>(ExceptionResult.SYS_ERROR, "验证码错误！", "");
	}
	
	/**
	 * 获取院系专业基本数据
	 */
	@PostMapping(value="/getDepartBase")
	public RestResponse<MultipleLinkageVo> getDepartBase(){
		return new RestResponse<MultipleLinkageVo>(ExceptionResult.REQUEST_SUCCESS, "数据获取成功", registService.getMultipleLinkageDepartments()); 
	}
	
	/**
	 * 注册
	 */
	@PostMapping(value="/regist")
	public RestResponse<Map<String,Object>> regist(@RequestBody SmSchoolmate schoolmate){
		Map<String,Object> userInfo=new HashMap<String,Object>(); 
    	//验证注册验证码
    	Boolean flag = registService.judgeCode(schoolmate.getSysUser().getUsername(),
    			schoolmate.getParamA(), RegistService.regitType);
    	if(!flag) {
    		return new RestResponse<Map<String,Object>>(ExceptionResult.SYS_ERROR, "验证码错误",userInfo); 
    	}
    	try {
    		ActivitiSmVo activitiSmVo  = registService.registSaveInfo(schoolmate);
    		Task task = activitiSmVo.getTask();
    		SmSchoolmate resSchoolmate = activitiSmVo.getSchoolmate();
    		
    		SysUser sysUser = resSchoolmate.getSysUser();   		
        	/**
        	 * 获取校友基本数据
        	 */
//            SmSchoolmate resSchoolmate = new SmSchoolmate();
//            resSchoolmate.setUserId(sysUser.getId());
//            resSchoolmate= registService.getSchoolmateBaseInfo(resSchoolmate);
            //注册token
            
            String token=JWTUtil.sign(sysUser.getUsername(),
            		sysUser.getId(), sysUser.getPassword(),JWTUtil.FROM_APP);                
            //获取token过期时间
            long tokenTime= JWTUtil.getTokenTime(token);                
            userInfo.put("token", token);  //返回token
            userInfo.put("tokenTime", tokenTime); //返回token时间
            userInfo.put("userInfo", resSchoolmate);
            if(task != null) {
            	userInfo.put("pages", task.getCategory());
            }else {
            	userInfo.put("pages", "");
            }            
			return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "信息提交成功",userInfo); 
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return new RestResponse<Map<String,Object>>(ExceptionResult.SYS_ERROR, "请检查输入是否正确",userInfo); 
		}
	}
		
	
	/**
	 * 人脸搜索识别通过后的请求registFacePass
	 */
	@PostMapping(value="/registFacePass")
	public RestResponse<Map<String,Object>> registFacePass(@RequestBody SmSchoolmate schoolmate){
		Map<String,Object> userInfo=new HashMap<String,Object>(); 
    	//验证注册验证码
    	Boolean flag = registService.judgeCode(schoolmate.getSysUser().getUsername(),
    			schoolmate.getParamA(), RegistService.regitType);
    	if(!flag) {
    		return new RestResponse<Map<String,Object>>(ExceptionResult.SYS_ERROR, "验证码错误",userInfo); 
    	}
    	try {
    		Task task  = registService.registFacePass(schoolmate);
    		SysUser sysUser = sysUserService.getPwdByUsername(schoolmate.getSysUser());   		
        	/**
        	 * 获取校友基本数据
        	 */
            SmSchoolmate resSchoolmate = new SmSchoolmate();
            resSchoolmate.setUserId(sysUser.getId());
            resSchoolmate= registService.getSchoolmateBaseInfo(resSchoolmate);
            //注册token
            String token=JWTUtil.sign(sysUser.getUsername(),
            		sysUser.getId(), sysUser.getPassword(),JWTUtil.FROM_APP);                
            //获取token过期时间
            long tokenTime= JWTUtil.getTokenTime(token);                
            userInfo.put("token", token);  //返回token
            userInfo.put("tokenTime", tokenTime); //返回token时间
            userInfo.put("userInfo", resSchoolmate);
            if(task != null) {
            	userInfo.put("pages", task.getCategory());
            }else {
            	userInfo.put("pages", "");
            }            
			return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "认证已通过",userInfo); 
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return new RestResponse<Map<String,Object>>(ExceptionResult.SYS_ERROR, "请检查输入是否正确",userInfo); 
		}
	}	
	
	/**
	 * 点击我的校友卡完成着个注册流程
	 */
	@PostMapping(value="/updateCardStatusWorkFlow")
	public RestResponse<SmSchoolmate> updateCardStatusWorkFlow(HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId(userId);
		schoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
		//查询校友会
		
		
		registService.updateCardStatusWorkFlow(schoolmate);
    	/**
    	 * 获取校友基本数据
    	 */
        SmSchoolmate resSchoolmate = new SmSchoolmate();
        SmEducation edu = new SmEducation();
        edu.setIsDefault(GlobalStr.IS_DEFAULT);
        resSchoolmate.setSmEducation(edu);
        resSchoolmate.setUserId(userId);        
        resSchoolmate= registService.getSchoolmateBaseInfo(resSchoolmate);
        
        //校友卡逻辑（2019-11-09新增）
        appSchoomateBaseInfoService.saveUserCard(resSchoolmate);
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "校友卡审核已通过",resSchoolmate); 
	}
	
	
	/**
	 * 获取确认信息页面校友信息
	 */
	@PostMapping(value="/getConfirmSmInfo")
	public RestResponse<SmSchoolmate> getConfirmSmInfo(HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
        SmSchoolmate schoolmate = new SmSchoolmate();
        schoolmate.setUserId(userId);
        SmEducation smEducation = new SmEducation();
        smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
		schoolmate.setSmEducation(smEducation);
        schoolmate= registService.getConfirmSmInfo(schoolmate);
        return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "校友信息获取成功",schoolmate); 
	}
	
	/**
	 * 通过UserId获取校友信息
	 */
	@PostMapping(value="/getInfoByUserId")
	public RestResponse<SmSchoolmate> getInfoByUserId(@RequestParam("userId") String userId){
        SmSchoolmate schoolmate = new SmSchoolmate();
        SmEducation edu = new SmEducation();
        edu.setIsDefault(GlobalStr.IS_DEFAULT);
        SmProfession smProfession = new SmProfession();
        smProfession.setType(GlobalStr.IS_DEFAULT);
        schoolmate.setSmEducation(edu);
        schoolmate.setSmProfession(smProfession);
        schoolmate.setUserId(userId);
        schoolmate= registService.getConfirmSmInfo(schoolmate);
        return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "校友信息获取成功",schoolmate); 
	}	
	
	
	/**
	 * 再次提交
	 */
	@PostMapping(value="/secondSubmit")
	public RestResponse<Map<String,Object>> secondSubmit(@RequestBody SmSchoolmate schoolmate){
		String userId = smSchoolmateService.get(schoolmate).getUserId();
		schoolmate.setUserId(userId);
		Map<String,Object> userInfo=new HashMap<String,Object>(); 
    	try {
    		 
    		registService.secondSubmit(schoolmate);		
        	/**
        	 * 获取校友基本数据
        	 */
            SmSchoolmate resSchoolmate = new SmSchoolmate();
            resSchoolmate.setUserId(userId);
            resSchoolmate= registService.getSchoolmateBaseInfo(resSchoolmate);
            userInfo.put("userInfo", resSchoolmate);
            //获取当前认证人所在的节点
    		List<Task> resList = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, userId);
    		if(resList.size() != 0) {
    			userInfo.put("pages", resList.get(0).getCategory());
    		}else {
    			userInfo.put("pages", "");
    		}        
			return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "信息提交成功",userInfo); 
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return new RestResponse<Map<String,Object>>(ExceptionResult.SYS_ERROR, "请检查输入是否正确",userInfo); 
		}
	}
	
	/**
	 * 获取认证他的好友
	 */
	@PostMapping(value = "/getProveListByStart")
	public RestResponse<List<SmSchoolmate>> getProveListByStart(HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		SmSchoolmateProve smSchoolmateProve = new SmSchoolmateProve();
		smSchoolmateProve.setStartUserId(userId);
		List<SmSchoolmate> smSchoolmates = smSchoolmateProveService.loadAllSmListBy(smSchoolmateProve);
		return new RestResponse<List<SmSchoolmate>>(ExceptionResult.REQUEST_SUCCESS, "获取认证它的好友成功",smSchoolmates);
	}
	
	/**
	 * 好友帮助认证时个人信息页面数据获取
	 */
	@PostMapping(value = "/getProvedSmByStart")
	public RestResponse<SmSchoolmate> getProvedSmByStart(SmSchoolmate schoolmate,HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
        SmEducation edu = new SmEducation();
        edu.setIsDefault(GlobalStr.IS_DEFAULT);
        schoolmate.setSmEducation(edu);
		schoolmate = registService.getConfirmSmInfo(schoolmate);
		SmSchoolmateProve smSchoolmateProve = new SmSchoolmateProve();
		smSchoolmateProve.setStartUserId(schoolmate.getUserId());
		List<SmSchoolmate> proveSchoolmates = smSchoolmateProveService.loadAllSmListBy(smSchoolmateProve);
		schoolmate.setProveSchoolmates(proveSchoolmates);
		//判断你是否已经认证
		for (SmSchoolmate sm : proveSchoolmates) {
			if(userId.equals(sm.getUserId())) {
				return new RestResponse<SmSchoolmate>(ExceptionResult.DATA_USED, "你已经认证过他了",schoolmate);
			}
		}		
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取校友数据成功",schoolmate);
	};
	
	/**
	 * 认证好友
	 */
	@PostMapping(value = "/helpProve")
	public RestResponse<String> helpProve(SmSchoolmateProve schoolmateProve,HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		String username = JWTUtil.getUsername(request.getHeader("Authorization"));
		schoolmateProve.setProveUserId(userId);
		schoolmateProve.setProveUsername(username);
		schoolmateProve.setStatus(GlobalStr.STATUS_PASS);
		//先去查询
		List<SmSchoolmateProve> schoolmateProves = smSchoolmateProveService.loadAllListBy(schoolmateProve);
		if(schoolmateProves.size() == 0) {
			smSchoolmateProveService.save(schoolmateProve);
			int num = registService.getProveNum();//认证人数;
			SmSchoolmateProve prove = new SmSchoolmateProve();
			prove.setStartUserId(schoolmateProve.getStartUserId());
			prove.setStartUsername(schoolmateProve.getStartUsername());
			Boolean flag = smSchoolmateProveService.judgeisNumProve(num, prove);
			if(flag) {
				registService.updateCardStatusProve(schoolmateProve.getStartUserId(),schoolmateProve.getStartUsername());
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "该校友已经是校友身份了","");	
			}else {
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "帮助认证成功","");	
			}
		}
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "已经认证过了","");
	}
	
	/**
	 * 上传真实图像
	 */
	@PostMapping("/uploadTruePhoto")
	public RestResponse<SmSchoolmate> uploadTruePhoto(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		File returnFile = null;
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));
			returnFile = fileService.saveFile(f);
			//更新到校友信息
			SmSchoolmate schoolmate = new SmSchoolmate();
			schoolmate.setUserId(userId);
			schoolmate.setTruePhoto(returnFile.getId());
			registService.updateSmTruePhoto(schoolmate);
            SmSchoolmate resSchoolmate = new SmSchoolmate();
            resSchoolmate.setUserId(userId);
            resSchoolmate= registService.getSchoolmateBaseInfo(resSchoolmate);
			return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "上传成功", resSchoolmate);
		} catch (IOException | NoSuchAlgorithmException ex) {
			
			// ex.printStackTrace();
			return new RestResponse<SmSchoolmate>(ExceptionResult.SYS_ERROR, "上传失败", null);
		} 

	}
	
	/**
	 * face_search 人脸搜索
	 */
	@PostMapping("/findSmByFace")
	public RestResponse<String> findSmByFace(@RequestParam("fileId") String fileId){
		String userId = registService.getSchoolmateByFace(fileId, groupIdList);
		if(StringUtil.isEmpty(userId)) {
			return new RestResponse<String>(ExceptionResult.NO_PERMISSION, "人脸认证失败", null);
		}
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "人脸认证成功", userId);
	}
}
