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
import com.mpri.aio.settings.model.PointLoginSign;
import com.mpri.aio.settings.service.PointLoginSignService;

 /**   
 *  
 * @Description:  校友登陆签到表——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:45:42 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/pointLoginSign")
public class PointLoginSignController extends BaseController {
	
	@Autowired
	private PointLoginSignService pointLoginSignService;
		
	/**
	 * 获取校友登陆签到表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param pointLoginSign
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<PointLoginSign> list(int pageNo,int pageSize,PointLoginSign pointLoginSign) {
		PageIo<PointLoginSign> pageInfo =  pointLoginSignService.loadByPage(pageNo,pageSize,pointLoginSign);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友登陆签到表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param pointLoginSign
	* @return
	 */
	@Logs(value = "登陆签到编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated PointLoginSign pointLoginSign){
		pointLoginSignService.save(pointLoginSign);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友登陆签到表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param pointLoginSign
	* @return
	 */
	@Logs(value = "登陆签到删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(PointLoginSign pointLoginSign) {
		pointLoginSignService.delete(pointLoginSign);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友登陆签到表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param pointLoginSign
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<PointLoginSign> get(PointLoginSign pointLoginSign) {
		return new RestResponse<PointLoginSign>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				pointLoginSignService.get(pointLoginSign));	
	}
		
}