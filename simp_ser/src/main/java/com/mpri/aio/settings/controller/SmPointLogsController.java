package com.mpri.aio.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SmPointLogs;
import com.mpri.aio.settings.service.SmPointLogsService;

 /**   
 *  
 * @Description:  校友积分记录——Controller
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Fri Sep 14 15:17:48 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/st/smPointLogs")
public class SmPointLogsController extends BaseController {
	
	@Autowired
	private SmPointLogsService smPointLogsService;
		
	/**
	 * 获取校友积分记录列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smPointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmPointLogs> list(int pageNo,int pageSize,SmPointLogs smPointLogs) {
		PageIo<SmPointLogs> pageInfo =  smPointLogsService.loadByPage(pageNo,pageSize,smPointLogs);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友积分记录
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smPointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SmPointLogs smPointLogs){
		smPointLogsService.save(smPointLogs);							
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除校友积分记录（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smPointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmPointLogs smPointLogs) {
		smPointLogsService.delete(smPointLogs);
		return  new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友积分记录
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smPointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmPointLogs> get(SmPointLogs smPointLogs) {
		return new RestResponse<SmPointLogs>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smPointLogsService.get(smPointLogs));
	}
		
}