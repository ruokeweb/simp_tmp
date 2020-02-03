package com.mpri.aio.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.settings.model.PointLogs;
import com.mpri.aio.settings.service.PointLogsService;

 /**   
 *  
 * @Description:  校友积分记录表——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:44:18 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/pointLogs")
public class PointLogsController extends BaseController {
	
	@Autowired
	private PointLogsService pointLogsService;
		
	/**
	 * 获取校友积分记录表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param pointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<PointLogs> list(int pageNo,int pageSize,PointLogs pointLogs) {
		PageIo<PointLogs> pageInfo =  pointLogsService.loadByPage(pageNo,pageSize,pointLogs);
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友积分记录表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param pointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated PointLogs pointLogs){
		pointLogsService.save(pointLogs);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友积分记录表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param pointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(PointLogs pointLogs) {
		pointLogsService.delete(pointLogs);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友积分记录表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param pointLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<PointLogs> get(PointLogs pointLogs) {
		return new RestResponse<PointLogs>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				pointLogsService.get(pointLogs));	
	}
		
}