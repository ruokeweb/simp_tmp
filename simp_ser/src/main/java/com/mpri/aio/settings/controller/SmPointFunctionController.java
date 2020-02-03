package com.mpri.aio.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SmPointFunction;
import com.mpri.aio.settings.service.SmPointFunctionService;

 /**   
 *  
 * @Description:  校友积分场景——Controller
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Fri Sep 14 14:34:30 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/st/smPointFunction")
public class SmPointFunctionController extends BaseController {
	
	@Autowired
	private SmPointFunctionService smPointFunctionService;
		
	/**
	 * 获取校友积分场景列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smPointFunction
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmPointFunction> list(int pageNo,int pageSize,SmPointFunction smPointFunction) {
		PageIo<SmPointFunction> pageInfo =  smPointFunctionService.loadByPage(pageNo,pageSize,smPointFunction);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友积分场景
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smPointFunction
	* @return
	 */
	@Logs(value = "校友积分场景编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(SmPointFunction smPointFunction){
		smPointFunctionService.save(smPointFunction);							
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除校友积分场景（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smPointFunction
	* @return
	 */
	@Logs(value = "校友积分场景删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmPointFunction smPointFunction) {
		smPointFunctionService.delete(smPointFunction);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友积分场景
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smPointFunction
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmPointFunction> get(SmPointFunction smPointFunction) {
		return new RestResponse<SmPointFunction>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smPointFunctionService.get(smPointFunction));
	}
		
}