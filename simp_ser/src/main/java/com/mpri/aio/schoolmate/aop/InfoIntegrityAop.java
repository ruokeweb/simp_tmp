package com.mpri.aio.schoolmate.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.common.utils.HttpContextUtils;
import com.mpri.aio.schoolmate.mapper.InfoIntegrityMapper;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.utils.InfoIntegrityUtils;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;

/**
 * 切面
 * <p>
 * Title: InfoIntegrityAop
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年9月13日
 */
@Aspect
@Component
public class InfoIntegrityAop {

	
	@Autowired
	private SysUserService sysUserService;
	
	
	@Autowired
	private SmSchoolmateService smSchoolmateService;
	
	@Autowired
	private InfoIntegrityMapper mapper;
	
	@Autowired
	private InfoIntegrityUtils infoIntegrityUtils;
	

	@Pointcut("@annotation(com.mpri.aio.schoolmate.aop.aspect.InfoIntegrity)")
	public void InfoIntegrityPointCut() {
	}

	@Around("InfoIntegrityPointCut()")
	public Object around(ProceedingJoinPoint point){
		Object obj = null;
		try {
			obj = point.proceed();
			
			// 获取request
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			
			String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
			if(StringUtil.isEmpty(userId)) {
				return obj;
			}			

			//获取当前用用户的信息完成度code
			List<String> status  = InfoIntegrityUtils.getInfoIntegrity(mapper, userId);
			//获取用户的星级
			int level = infoIntegrityUtils.setSmComplete(status);
			System.err.println(level);
			//修改星级
			SmSchoolmate sm = new SmSchoolmate();
			sm.setUserId(userId);
			sm.setComplete(level);
			smSchoolmateService.updateComplete(sm);
		} catch (Exception e) {
			//e.printStackTrace();
		} catch (Throwable e) {

		}
		// 返回目标方法的参数：
		return obj;
	}

	/**
	 * 
	* <p>Title: getUsernameByParms</p>  
	* <p>Description:根据token获取username </p>  
	* @param request
	* @return
	 */
	private String getUsernameByParms(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(StringUtil.isEmpty(token)) {
			return "";
		}
		String username=JWTUtil.getUsername(token);
		return null == username ? "" : username ;
	}
	

	/**
	 * 
	* <p>Title: getUserId</p>  
	* <p>Description: 通过用户名获取用户id</p>  
	* @param username
	* @return
	 */
	private String getUserId(String username) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		return sysUserService.getPwdByUsername(sysUser).getId();
	}

}
