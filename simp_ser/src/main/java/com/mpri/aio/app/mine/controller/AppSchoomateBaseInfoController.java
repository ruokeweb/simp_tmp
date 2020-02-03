package com.mpri.aio.app.mine.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.mine.service.AppSchoomateBaseInfoService;
import com.mpri.aio.app.reg.service.RegistService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.MD5Util;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.model.MesMessage;
import com.mpri.aio.message.model.MesUserMessage;
import com.mpri.aio.message.service.MesMessageService;
import com.mpri.aio.message.service.MesUserMessageService;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.settings.model.SettingCard;
import com.mpri.aio.settings.model.SettingUserCard;
import com.mpri.aio.settings.service.SettingCardService;
import com.mpri.aio.settings.service.SettingUserCardService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.UserInfoUtil;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.AESUtil;
import com.mpri.aio.system.utils.StringUtils;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

/**
 * 校友基本信息
 * 
 * @author syp
 *
 */
@RestController
@RequestMapping("/app")
@CrossOrigin
public class AppSchoomateBaseInfoController extends BaseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private RegistService registService;

	@Autowired
	private AppSchoomateBaseInfoService baseInfoService;
	
	@Autowired
	private SysUserService sysUserService;
	
	
	@Autowired
	private MesMessageService mesMessageService;
	
	@Autowired
	private MesUserMessageService mesUserMessageService;
	
	@Autowired
	private SettingCardService settingCardService;
	
	@Autowired
	private SettingUserCardService settingUserCardService;
	
	/**
	 * 上传真实图像
	 */
	@PostMapping("/uploadVirtualPhoto")
	public RestResponse<SmSchoolmate> uploadVirtualPhoto(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		File returnFile = null;
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));
			returnFile = fileService.saveFile(f);
			// 更新到用户信息
			SysUser sysUser = new SysUser();
			sysUser.setId(userId);
			sysUser.setVirtualPhoto(returnFile.getId());
			baseInfoService.updateVirtualPhoto(sysUser);
			// 更新storage
			SmSchoolmate resSchoolmate = new SmSchoolmate();
			resSchoolmate.setUserId(userId);
			resSchoolmate = registService.getSchoolmateBaseInfo(resSchoolmate);
			return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "上传成功", resSchoolmate);
		} catch (IOException | NoSuchAlgorithmException ex) {
			// ex.printStackTrace();
			return new RestResponse<SmSchoolmate>(ExceptionResult.SYS_ERROR, "上传失败", null);
		}
	}

	/**
	 * 修改昵称
	 */
	@PostMapping("/updateVirtualName")
	public RestResponse<SmSchoolmate> updateVirtualName(HttpServletRequest request, SysUser sysUser) {
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		// 更新到用户信息
		sysUser.setId(userId);
		baseInfoService.updateVirtualName(sysUser);
		// 更新storage
		SmSchoolmate resSchoolmate = new SmSchoolmate();
		resSchoolmate.setUserId(userId);
		resSchoolmate = registService.getSchoolmateBaseInfo(resSchoolmate);
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "修改成功", resSchoolmate);

	}
	
	
	/**
	 * 注册发送验证码
	 */
	@PostMapping(value = "/sendChangePhoneCaptCha")
	public RestResponse<String> sendChangePhoneCaptCha(@RequestParam("newphone") String newphone) {
		if (StringUtil.isNotEmpty(newphone)) {
			Boolean existThisUser = registService.existThisUser(newphone);
			if (existThisUser) {
				return new RestResponse<String>(ExceptionResult.DATA_USED, "该手机已注册，请到登录页面进行登录", "");
			}
		}
		try {
			String res = registService.sendChangePhoneCaptCha(newphone);
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
	 * 更换手机号
	 */
	@PostMapping(value = "/changePhone")
	public RestResponse<SmSchoolmate> changePhone(@RequestParam("newphone") String newphone,
			@RequestParam("username") String username,@RequestParam("captChaCode") String captChaCode,HttpServletRequest request) {
    	//验证找回密码验证码
    	Boolean flag = registService.judgeCode(newphone,captChaCode, RegistService.changUsernameType);
    	if(flag) {
    		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
    		SysUser user = new SysUser();
    		user.setId(userId);
    		user = sysUserService.get(user);
			if(user.getUsername().equals(username)) {
				user.setUsername(newphone);
				registService.updateUsernameById(user);
				// 更新storage
				SmSchoolmate resSchoolmate = new SmSchoolmate();
				resSchoolmate.setUserId(userId);
				resSchoolmate = registService.getSchoolmateBaseInfo(resSchoolmate);
				return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "修改成功", resSchoolmate);
			}else {
				return new RestResponse<SmSchoolmate>(ExceptionResult.SYS_ERROR, "原手机号错误", null);
			}
    	}else {
        	return new RestResponse<SmSchoolmate>(ExceptionResult.SYS_ERROR, "验证码错误", null);
    	}
	}
	
	/**
	 * 更改密码
	 */
	@PostMapping(value = "/updatePwd")
	public RestResponse<String> updatePwd(@RequestParam("oldPass") String oldPass,@RequestParam("newPass") String newPass,
			HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		//解密新密码和旧密码
		//解密
		oldPass = AESUtil.aesDecrypt(oldPass);
		//解密
		newPass = AESUtil.aesDecrypt(newPass);
		//旧密码加密验证
		SysUser user = new SysUser();
		user.setId(userId);
		user = sysUserService.get(user);
		ByteSource salt = ByteSource.Util.bytes(user.getSafecode());
		String result = new Md5Hash(oldPass,salt,registService.getSaltTimes()).toString();
		if(user.getPassword().equals(result)) {
			//新密码加密保存
			String newPassResult = new Md5Hash(newPass,salt,registService.getSaltTimes()).toString();
			//更新密码
			SysUser newUser = new SysUser();
			newUser.setId(userId);
			newUser.setUsername(user.getUsername());
			newUser.setPassword(newPassResult);
			sysUserService.save(newUser);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "密码修改完成", "");
		}
		
		return new RestResponse<String>(ExceptionResult.SYS_ERROR, "旧密码错误", "");
	}
	
	/**
	 * 获取消息条数
	* <p>Title: getMesCountBy</p>  
	* <p>Description: </p>  
	* @param request
	* @return RestResponse<Integer>
	 */
    @PostMapping(value = "/getMesCountBy")
    public RestResponse<String> getMesCountBy(HttpServletRequest request){   
    	String userId = UserInfoUtil.getUserId(request);
		List<MesGroup> mesGroups = baseInfoService.getGroup(userId);
		List<String> ids = new ArrayList<>();
		for(MesGroup group : mesGroups){
			ids.add(group.getId());
		}
		String IdsStr = StringUtils.join(ids, ",");
		MesMessage mesMessage = new  MesMessage();
		mesMessage.setGroupIds(IdsStr);
		//设置包括通知
		mesMessage.setType(GlobalStr.MES_NOTICE);
		mesMessage.setReceiveUserId(userId);
		//设置未过期
		mesMessage.setSendType(GlobalStr.MES_SENDTYPE_CARD);
		mesMessage.setStatus(GlobalStr.NOT_OVERDUE);
		int count = mesMessageService.getMesCountBy(mesMessage);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", count+"");
    }   
	
	
    
    /**
     * 获取所有未读的
    * <p>Title: getFirstMesBy</p>  
    * <p>Description: </p>  
    * @param request
    * @return RestResponse<List<MesMessage>>
     */
    @PostMapping(value = "/getMesListUnReadBy")
    public RestResponse<List<MesMessage>> getMesListUnReadBy(HttpServletRequest request){   
    	String userId = UserInfoUtil.getUserId(request);
		List<MesGroup> groups = baseInfoService.getGroup(userId);
		List<String> ids = new ArrayList<>();
		for(MesGroup group : groups){
			ids.add(group.getId());
		}
		String IdsStr = StringUtils.join(ids, ",");
		MesMessage mesMessage = new  MesMessage();
		mesMessage.setGroupIds(IdsStr);
		//设置包括通知
		mesMessage.setType(GlobalStr.MES_NOTICE);
		mesMessage.setReceiveUserId(userId);
		//设置未过期
		mesMessage.setSendType(GlobalStr.MES_SENDTYPE_CARD);
		mesMessage.setStatus(GlobalStr.NOT_OVERDUE);
		List<MesMessage> messageList = mesMessageService.getMesListBy(mesMessage);
        return new RestResponse<List<MesMessage>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", messageList);
    } 	
	
    /**
     * 获取已读的
    * <p>Title: getMesListUnReadBy</p>  
    * <p>Description: </p>  
    * @param request
    * @return RestResponse<List<MesMessage>>
     */
    @CrossOrigin
    @PostMapping(value = "/getMesListReadBy")
    public RestResponse<List<MesMessage>> getMesListReadBy(HttpServletRequest request){   
    	String userId = UserInfoUtil.getUserId(request);
		MesMessage mesMessage = new  MesMessage();
		mesMessage.setReceiveUserId(userId);
		//设置未过期
		mesMessage.setStatus(GlobalStr.NOT_OVERDUE);
		mesMessage.setSendType(GlobalStr.MES_SENDTYPE_CARD);
        return new RestResponse<List<MesMessage>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", mesMessageService.getMesLisReadtBy(mesMessage));
    } 
    
    /**
     * 标记已读接口
    * <p>Title: saveMesUser</p>  
    * <p>Description: </p>  
    * @param request
    * @param messageId
    * @return RestResponse<String>
     */
    @CrossOrigin
    @PostMapping(value = "/saveMesUser")
    public RestResponse<String> saveMesUser(HttpServletRequest request,@RequestParam(value="messageId",required=false) String messageId){   
    	String userId = UserInfoUtil.getUserId(request);
    	MesUserMessage mesUserMessage = new MesUserMessage();
    	mesUserMessage.setMessageId(messageId);
    	mesUserMessage.setUserId(userId);
    	List<MesUserMessage> messages = mesUserMessageService.loadAllListBy(mesUserMessage);
    	if(messages.size() != 0) {
    		 return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "之前已读！", ""); 
    	}
		//设置状态
    	mesUserMessage.setStatus(GlobalStr.MES_READ);
    	mesUserMessageService.save(mesUserMessage);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "已读成功！", "");
    } 
    
    
    /**
     * 获取消息内容并标记已读
    * <p>Title: getMesContent</p>  
    * <p>Description: </p>  
    * @param mesMessage
    * @param request
    * @return RestResponse<MesMessage>
     */
    @PostMapping(value = "/getMesContent")
	public RestResponse<MesMessage> getMesContent(MesMessage mesMessage,HttpServletRequest request) {
    	String userId = UserInfoUtil.getUserId(request);
    	mesMessage.setStatus(GlobalStr.NOT_OVERDUE);
    	
    	MesUserMessage mesUserMessage = new MesUserMessage();
    	mesUserMessage.setMessageId(mesMessage.getId());
    	mesUserMessage.setUserId(userId);
    	List<MesUserMessage> messages = mesUserMessageService.loadAllListBy(mesUserMessage);
    	if(messages.size() != 0) {
    		 return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "之前已读！", mesMessageService.getMesContent(mesMessage)); 
    	}
		//设置状态
    	mesUserMessage.setStatus(GlobalStr.MES_READ);
    	mesMessage.setSendType(GlobalStr.MES_SENDTYPE_CARD);
    	mesUserMessageService.save(mesUserMessage);	
    	return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "已读取！", mesMessageService.getMesContent(mesMessage));
	}
    
    /**
     * 获取消息内容
     */
    @PostMapping(value = "/getMesContentById")
	public RestResponse<MesMessage> getMesContentById(MesMessage mesMessage) {
    	mesMessage = mesMessageService.get(mesMessage);
    	return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "已读取！",mesMessage);
	}   
    
    /**
     * 获取校友卡
     */
    
    @PostMapping(value = "/getAlumniCard")
	public RestResponse<SettingCard> getAlumniCard(SettingCard card) {
    	List<SettingCard> settingCards = settingCardService.getSettingCardByLevel(card);
    	if(settingCards.size() != 0) {
    		return new RestResponse<SettingCard>(ExceptionResult.REQUEST_SUCCESS, "获取校友卡成功！",settingCards.get(0));
    	}
    	return new RestResponse<SettingCard>(ExceptionResult.SYS_ERROR, "获取校友卡失败！",null);
	}
    
    /**
     * 
     */
    @PostMapping(value = "/getAlumniCards")
	public RestResponse<List<SettingUserCard>> getAlumniCards(HttpServletRequest request) {
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
    	if(StringUtil.isNotEmpty(userId)) {
    		SettingUserCard settingUserCard = new SettingUserCard();
    		settingUserCard.setUserId(userId);
    		List<SettingUserCard> list = settingUserCardService.loadCardListByUserId(settingUserCard);
    		return new RestResponse<List<SettingUserCard>>(ExceptionResult.REQUEST_SUCCESS, "获取校友卡成功！",list);
    	}
    	return new RestResponse<List<SettingUserCard>>(ExceptionResult.SYS_ERROR, "获取校友卡失败！",null);
	}
}
