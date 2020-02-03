package com.mpri.aio.system.controller;

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
import com.mpri.aio.system.model.SysHistoryPassword;
import com.mpri.aio.system.service.SysHistoryPasswordService;

 /**   
 *  
 * @Description:  用户密码记录——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Dec 26 11:02:28 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/sys/historyPassword")
public class SysHistoryPasswordController extends BaseController {
	
	@Autowired
	private SysHistoryPasswordService sysHistoryPasswordService;
		
	/**
	 * 获取用户密码记录列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysHistoryPassword
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SysHistoryPassword> list(int pageNo,int pageSize,SysHistoryPassword sysHistoryPassword) {
		PageIo<SysHistoryPassword> pageInfo =  sysHistoryPasswordService.loadByPage(pageNo,pageSize,sysHistoryPassword);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新用户密码记录
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param sysHistoryPassword
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SysHistoryPassword sysHistoryPassword){
		sysHistoryPasswordService.save(sysHistoryPassword);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除用户密码记录（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param sysHistoryPassword
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysHistoryPassword sysHistoryPassword) {
		sysHistoryPasswordService.delete(sysHistoryPassword);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取用户密码记录
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param sysHistoryPassword
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysHistoryPassword> get(SysHistoryPassword sysHistoryPassword) {
		return new RestResponse<SysHistoryPassword>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				sysHistoryPasswordService.get(sysHistoryPassword));	
	}
		
}