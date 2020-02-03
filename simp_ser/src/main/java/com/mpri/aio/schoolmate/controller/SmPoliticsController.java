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
import com.mpri.aio.schoolmate.model.SmPolitics;
import com.mpri.aio.schoolmate.service.SmPoliticsService;

 /**   
 *  
 * @Description:  校友政治面貌——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:56:39 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smPolitics")
public class SmPoliticsController extends BaseController {
	
	@Autowired
	private SmPoliticsService smPoliticsService;
		
	/**
	 * 获取校友政治面貌列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smPolitics
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmPolitics> list(int pageNo,int pageSize,SmPolitics smPolitics) {
		PageIo<SmPolitics> pageInfo =  smPoliticsService.loadByPage(pageNo,pageSize,smPolitics);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友政治面貌
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smPolitics
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmPolitics> save(SmPolitics smPolitics){
		smPoliticsService.save(smPolitics);
		return new RestResponse<SmPolitics>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smPolitics);							
	}	
	
	/**
	 * 删除校友政治面貌（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smPolitics
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmPolitics smPolitics) {
		smPoliticsService.delete(smPolitics);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友政治面貌
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smPolitics
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmPolitics> get(SmPolitics smPolitics) {
		return new RestResponse<SmPolitics>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smPoliticsService.get(smPolitics));	
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmPolitics>> loadAllListBy(SmPolitics SmPolitics) {			
		return new RestResponse<List<SmPolitics>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				smPoliticsService.loadAllListBy(SmPolitics));		
	}	
}