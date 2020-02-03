package com.mpri.aio.app.utils.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.service.SysLoginExpandService;
import com.mpri.aio.app.utils.WechatGlobal.NoticeTemplate;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.app.utils.model.SubMessage.PayNotice;
import com.mpri.aio.app.utils.service.SubMessageService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;

@CrossOrigin
@RestController
@RequestMapping("/app/demo")
public class AppDemoController {

	@Autowired
	SysUserService userService;
	
	@Autowired
	SubMessageService  subMessageService;
	@Autowired
	SysLoginExpandService loginExpandService;
	
	//缴费通知的样例
	@PostMapping("/jftz")
	public RestResponse<String> jftzDemo(HttpServletRequest request) {
		
		//获取token
		String token = request.getHeader("Authorization");
		String user_open_id="";
		//用户登录时获取计划
		if(!token.isEmpty()) {
			String userId = JWTUtil.getUserId(token);
//			SysUser userParam=new SysUser();
//			userParam.setId(userId);
//			SysUser user=userService.get(userParam);
			//获取库里的userid
			SysLoginExpand loginExpandParam= new SysLoginExpand();
			loginExpandParam.setUserId(userId);
			SysLoginExpand loginExpand=loginExpandService.loadAllListBy(loginExpandParam).get(0);
			user_open_id=loginExpand.getExpand();
		}
		
		SubMessage sm = new SubMessage();
		PayNotice pn = sm.new PayNotice("特别的一个缴费项目","1130.30","2019-11-181 12:11:11","付费成功");//封装业务参数
		sm.setData(pn);
		sm.setTouser(user_open_id);
		sm.setTemplate_id(NoticeTemplate.JFTZ);
		String result=subMessageService.notice(sm);
		
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", result);
	}
}
