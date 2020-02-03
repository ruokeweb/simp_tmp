package com.mpri.aio.app.mine.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmHisEducation;
import com.mpri.aio.schoolmate.service.SmHisEducationService;
import com.mpri.aio.system.shiro.JWTUtil;

/**
 * 其他教育经历
 * @author syp
 *
 */
@CrossOrigin
@RestController
@RequestMapping("app/hisEdu")
public class AppSmHisEduController extends BaseController {

    @Autowired
    private SmHisEducationService sEducationService;
    
	/**
	 * 获取校友其他教育经历表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smHisEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public RestResponse<List<SmHisEducation>> list(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmHisEducation smHisEducation = new SmHisEducation();
        smHisEducation.setUserId(userId);
		List<SmHisEducation> list =  sEducationService.loadAllListBy(smHisEducation);					
		return new RestResponse<List<SmHisEducation>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", list);
	}
	
	
	/**
	 * 增加或者更新校友其他教育经历表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smHisEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(SmHisEducation   smHisEducation,HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smHisEducation.setUserId(userId);
        sEducationService.save(smHisEducation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友其他教育经历表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smHisEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmHisEducation smHisEducation) {
		sEducationService.delete(smHisEducation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友其他教育经历表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smHisEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmHisEducation> get(SmHisEducation smHisEducation) {
		return new RestResponse<SmHisEducation>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				sEducationService.get(smHisEducation));	
	}
    
}
