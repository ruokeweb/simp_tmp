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
import com.mpri.aio.ranking.model.ChaProve;
import com.mpri.aio.ranking.service.ChaProveService;

 /**   
 *  
 * @Description:  校友认证归纳表——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:17:07 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/ranking/chaProve")
public class ChaProveController extends BaseController {
	
	@Autowired
	private ChaProveService chaProveService;
		
	/**
	 * 获取校友认证归纳表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param chaProve
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ChaProve> list(int pageNo,int pageSize,ChaProve chaProve) {
		PageIo<ChaProve> pageInfo =  chaProveService.loadByPage(pageNo,pageSize,chaProve);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友认证归纳表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param chaProve
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ChaProve chaProve){
		chaProveService.save(chaProve);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友认证归纳表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param chaProve
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ChaProve chaProve) {
		chaProveService.delete(chaProve);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友认证归纳表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param chaProve
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ChaProve> get(ChaProve chaProve) {
		return new RestResponse<ChaProve>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				chaProveService.get(chaProve));	
	}
		
}