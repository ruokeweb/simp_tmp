package com.mpri.aio.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.settings.model.PointFunction;
import com.mpri.aio.settings.service.PointFunctionService;

 /**   
 *  
 * @Description:  校友积分场景表——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:46:44 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/pointFunction")
public class PointFunctionController extends BaseController {
	
	@Autowired
	private PointFunctionService pointFunctionService;
		
	/**
	 * 获取校友积分场景表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param pointFunction
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<PointFunction> list(int pageNo,int pageSize,PointFunction pointFunction) {
		PageIo<PointFunction> pageInfo =  pointFunctionService.loadByPage(pageNo,pageSize,pointFunction);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友积分场景表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param pointFunction
	* @return
	 */
	@Logs(value = "校友积分场景编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated PointFunction pointFunction){
		pointFunctionService.save(pointFunction);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友积分场景表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param pointFunction
	* @return
	 */
	@Logs(value = "校友积分场景删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(PointFunction pointFunction) {
		pointFunctionService.delete(pointFunction);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友积分场景表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param pointFunction
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<PointFunction> get(PointFunction pointFunction) {
		return new RestResponse<PointFunction>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				pointFunctionService.get(pointFunction));	
	}
		
}