package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmExperience;
import com.mpri.aio.schoolmate.service.SmExperienceService;

 /**   
 *  
 * @Description:  校友校园经历——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:38:47 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smExperience")
public class SmExperienceController extends BaseController {
	
	@Autowired
	private SmExperienceService smExperienceService;
		
	/**
	 * 获取校友校园经历列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smExperience
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmExperience> list(int pageNo,int pageSize,SmExperience smExperience) {
		PageIo<SmExperience> pageInfo =  smExperienceService.loadByPage(pageNo,pageSize,smExperience);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友校园经历
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smExperience
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmExperience> save(SmExperience smExperience){
		smExperienceService.save(smExperience);
		return new RestResponse<SmExperience>(ExceptionResult.REQUEST_SUCCESS, "保存成功！",smExperience);							
	}	
	
	/**
	 * 删除校友校园经历（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smExperience
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmExperience smExperience) {
		smExperienceService.delete(smExperience);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友校园经历
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smExperience
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmExperience> get(SmExperience smExperience) {
		return new RestResponse<SmExperience>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smExperienceService.get(smExperience));	
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmExperience>> loadAllListBy(SmExperience smExperience) {						
		return new RestResponse<List<SmExperience>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smExperienceService.loadAllListBy(smExperience));	
	}
}