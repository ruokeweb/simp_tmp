package com.mpri.aio.ranking.controller;

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
import com.mpri.aio.ranking.model.ChaShare;
import com.mpri.aio.ranking.service.ChaShareService;

 /**   
 *  
 * @Description:  校友分享归纳表——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:19:42 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/ranking/chaShare")
public class ChaShareController extends BaseController {
	
	@Autowired
	private ChaShareService chaShareService;
		
	/**
	 * 获取校友分享归纳表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param chaShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ChaShare> list(int pageNo,int pageSize,ChaShare chaShare) {
		PageIo<ChaShare> pageInfo =  chaShareService.loadByPage(pageNo,pageSize,chaShare);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友分享归纳表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param chaShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ChaShare chaShare){
		chaShareService.save(chaShare);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友分享归纳表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param chaShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ChaShare chaShare) {
		chaShareService.delete(chaShare);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友分享归纳表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param chaShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ChaShare> get(ChaShare chaShare) {
		return new RestResponse<ChaShare>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				chaShareService.get(chaShare));	
	}
		
}