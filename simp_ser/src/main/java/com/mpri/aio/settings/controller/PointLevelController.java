package com.mpri.aio.settings.controller;

import com.mpri.aio.common.logs.Logs;
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
import com.mpri.aio.settings.model.PointLevel;
import com.mpri.aio.settings.service.PointLevelService;

 /**   
 *  
 * @Description:  等级积分设置——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Mon Feb 25 16:11:15 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/pointLevel")
public class PointLevelController extends BaseController {
	
	@Autowired
	private PointLevelService pointLevelService;
		
	/**
	 * 获取等级积分设置列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param pointLevel
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<PointLevel> list(int pageNo,int pageSize,PointLevel pointLevel) {
		PageIo<PointLevel> pageInfo =  pointLevelService.loadByPage(pageNo,pageSize,pointLevel);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新等级积分设置
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param pointLevel
	* @return
	 */
	@Logs(value = "等级积分修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated PointLevel pointLevel){
		pointLevelService.save(pointLevel);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除等级积分设置（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param pointLevel
	* @return
	 */
	@Logs(value = "等级积分删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(PointLevel pointLevel) {
		pointLevelService.delete(pointLevel);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取等级积分设置
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param pointLevel
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<PointLevel> get(PointLevel pointLevel) {
		return new RestResponse<PointLevel>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				pointLevelService.get(pointLevel));	
	}
		
}