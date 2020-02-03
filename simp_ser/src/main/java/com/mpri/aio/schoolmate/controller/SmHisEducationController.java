package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmHisEducation;
import com.mpri.aio.schoolmate.service.SmHisEducationService;

 /**   
 *  
 * @Description:  校友其他教育经历表——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Jul 30 15:06:53 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/sm/smHisEducation")
public class SmHisEducationController extends BaseController {
	
	@Autowired
	private SmHisEducationService smHisEducationService;
		
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
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmHisEducation>> loadAllListBy(SmHisEducation smHisEducation) {
		List<SmHisEducation> list =  smHisEducationService.loadAllListBy(smHisEducation);					
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
	public RestResponse<String> save(@Validated SmHisEducation smHisEducation){
		smHisEducationService.save(smHisEducation);
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
		smHisEducationService.delete(smHisEducation);
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
				smHisEducationService.get(smHisEducation));	
	}
		
}