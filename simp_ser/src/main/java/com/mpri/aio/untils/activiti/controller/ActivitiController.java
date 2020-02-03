package com.mpri.aio.untils.activiti.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.service.SysLoginExpandService;
import com.mpri.aio.app.utils.WechatGlobal.NoticeTemplate;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.app.utils.model.SubMessage.RegExam;
import com.mpri.aio.app.utils.service.SubMessageService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.model.SysRole;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysRoleService;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.untils.activiti.service.ActivitiService;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;
import com.mpri.aio.untils.activiti.vo.ActivitiVo;

@RestController
@RequestMapping("/activiti/myactiviti")
public class ActivitiController extends BaseController{

	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private ActivitiUtils activitiUtils;
	
	@Autowired
	SysLoginExpandService loginExpandService;
	
	@Autowired
	SubMessageService subMessageService;
	
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	/**
	 * 获取待办事项
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/getWilDoList")
	public RestResponse<List<ActivitiVo>> getWilDoList(HttpServletRequest request){
		String username = JWTUtil.getUsername(request.getHeader("Authorization"));
		SysUser sysUser = sysUserService.getSysUserByUsername(username);
		List<ActivitiVo> activitiVos = new ArrayList<ActivitiVo>();
		List<SysRole> sysRoles = sysRoleService.loadRoleByUser(sysUser.getId());
		for (SysRole role : sysRoles) {
			if(role.getCode().equalsIgnoreCase(GlobalStr.SUPER_ADMIN)) {
				activitiVos = activitiService.getWilDoList(GlobalStr.PROCESSDEFINITIONKEY_AUTHENTICATE, GlobalStr.SUPER_ADMIN);
			}
		}
		//开发环境
		return new RestResponse<List<ActivitiVo>>(ExceptionResult.REQUEST_SUCCESS, "获取成功", activitiVos);
	}
	
	
	/**
	 * 分页查询
	 */
	@CrossOrigin
	@RequestMapping(value = "/getWilDoByPage")
	public PageIo<ActivitiVo> getWilDoByPage(HttpServletRequest request,int pageNo,int pageSize){
		String username = JWTUtil.getUsername(request.getHeader("Authorization"));
		SysUser sysUser = sysUserService.getSysUserByUsername(username);
		List<SysRole> sysRoles = sysRoleService.loadRoleByUser(sysUser.getId());
		List<ActivitiVo> activitiVos = new ArrayList<ActivitiVo>();
		PageIo<ActivitiVo> pageIo = new PageIo<ActivitiVo>(activitiVos);
		for (SysRole role : sysRoles) {
			if(role.getCode().equalsIgnoreCase(GlobalStr.SUPER_ADMIN)) {
				pageIo = activitiService.getWilDoByPage(GlobalStr.PROCESSDEFINITIONKEY_AUTHENTICATE, 
						GlobalStr.SUPER_ADMIN,pageNo,pageSize);
			
			}
		}
		//开发环境
//		activitiVos = activitiService.getWilDoList(GlobalStr.PROCESSDEFINITIONKEY_AUTHENTICATE, GlobalStr.SUPER_ADMIN);
		return pageIo;
	}	
	/**
	 * 驳回审核流程
	 */
	@CrossOrigin
	@RequestMapping(value = "/authTurnDown")
	public RestResponse<String> authTurnDown(@RequestParam("userId") String userId,@RequestParam("processInstanceId") String processInstanceId,
			@RequestParam("resultCon") String resultCon){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("res", GlobalStr.ACTIVITI_HOLD);
		map.put("resultCon", resultCon);
		activitiUtils.setVarible(map, processInstanceId, GlobalStr.SUPER_ADMIN);
		
		//在此增加审核通过的通知
		SysLoginExpand loginExpandParam= new SysLoginExpand();
		loginExpandParam.setUserId(userId);
		SysLoginExpand loginExpand=null;
		List<SysLoginExpand> list=loginExpandService.loadAllListBy(loginExpandParam);
		if(list.size()>0) {
			loginExpand=list.get(0);
		}
		if(loginExpand!=null) {
			String user_open_id=loginExpand.getExpand();
			SubMessage sm = new SubMessage();
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);  
			RegExam re=sm.new RegExam( dateNowStr, "校友身份认证失败", "您的校友身份认证失败，请在小程序查看原因");
			sm.setData(re);
			sm.setTouser(user_open_id);
			sm.setTemplate_id(NoticeTemplate.ZCSH);
			subMessageService.notice(sm);
		}
		
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "驳回成功", ""); 
	}
	
	/**
	 * 审核通过
	 */
	@CrossOrigin
	@RequestMapping(value = "/authPass")
	public RestResponse<String> authPass(@RequestParam("userId") String userId ,@RequestParam("processInstanceId") String processInstanceId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("res", GlobalStr.ACTIVITI_PASS);
		activitiUtils.setVarible(map, processInstanceId, GlobalStr.SUPER_ADMIN);
		//在此增加审核通过的通知
		SysLoginExpand loginExpandParam= new SysLoginExpand();
		loginExpandParam.setUserId(userId);
		SysLoginExpand loginExpand=null;
		List<SysLoginExpand> list=loginExpandService.loadAllListBy(loginExpandParam);
		if(list.size()>0) {
			loginExpand=list.get(0);
		}
		if(loginExpand!=null) {
			String user_open_id=loginExpand.getExpand();
			SubMessage sm = new SubMessage();
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);  
			RegExam re=sm.new RegExam( dateNowStr, "校友身份已核实", "亲爱的校友你好，快去使用校友卡吧");
			sm.setData(re);
			sm.setTouser(user_open_id);
			sm.setTemplate_id(NoticeTemplate.ZCSH);
			subMessageService.notice(sm);
		}

		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "审核成功", ""); 
	}
	
//	private String getToken() {
//		String params = "grant_type=client_credential&appid=" + appId + "&secret=" + secret;
//		String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
//		JSONObject jsonObject1 = JSONObject.parseObject(sr);
//
//		return jsonObject1.get("access_token").toString();
//	}
}
