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
import com.mpri.aio.schoolmate.model.SmSocial;
import com.mpri.aio.schoolmate.service.SmSocialService;

 /**   
 *  
 * @Description:  校友社会兼职——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:48 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smSocial")
public class SmSocialController extends BaseController {
	
	@Autowired
	private SmSocialService smSocialService;
		
	/**
	 * 获取校友社会兼职列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smSocial
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmSocial> list(int pageNo,int pageSize,SmSocial smSocial) {
		PageIo<SmSocial> pageInfo =  smSocialService.loadByPage(pageNo,pageSize,smSocial);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友社会兼职
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smSocial
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmSocial> save(SmSocial smSocial){
		smSocialService.save(smSocial);
		return new RestResponse<SmSocial>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smSocial);							
	}	
	
	/**
	 * 删除校友社会兼职（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smSocial
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmSocial smSocial) {
		smSocialService.delete(smSocial);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友社会兼职
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smSocial
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmSocial> get(SmSocial smSocial) {
		return new RestResponse<SmSocial>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smSocialService.get(smSocial));	
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmSocial>> loadAllListBy(SmSocial smSocial) {					
		return new RestResponse<List<SmSocial>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smSocialService.loadAllListBy(smSocial));
	}
}