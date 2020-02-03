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
import com.mpri.aio.schoolmate.model.SmFamous;
import com.mpri.aio.schoolmate.service.SmFamousService;

 /**   
 *  
 * @Description:  知名校友管理——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Mar 01 13:28:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/sm/smFamous")
public class SmFamousController extends BaseController {
	
	@Autowired
	private SmFamousService smFamousService;
		
	/**
	 * 获取知名校友管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smFamous
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmFamous> list(int pageNo,int pageSize,SmFamous smFamous) {
		PageIo<SmFamous> pageInfo =  smFamousService.loadByPage(pageNo,pageSize,smFamous);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新知名校友管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smFamous
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(SmFamous smFamous){
		smFamousService.save(smFamous);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除知名校友管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smFamous
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmFamous smFamous) {
		smFamousService.delete(smFamous);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取知名校友管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smFamous
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmFamous> get(SmFamous smFamous) {
		
		return new RestResponse<SmFamous>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smFamousService.get(smFamous));	
	}
	/**
	 * 获取知名校友管理
	 * <p>Title: get</p>  
	 * <p>Description: </p>  
	 * @param smFamous
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmFamous>> loadAllListBy(SmFamous smFamous) {
		return new RestResponse<List<SmFamous>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",smFamousService.loadAllListBy(smFamous)
				 );	
	}
	
		
}