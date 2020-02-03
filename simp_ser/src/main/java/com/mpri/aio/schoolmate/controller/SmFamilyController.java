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
import com.mpri.aio.schoolmate.model.SmFamily;
import com.mpri.aio.schoolmate.service.SmFamilyService;

 /**   
 *  
 * @Description:  校友家庭成员——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:35:41 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smFamily")
public class SmFamilyController extends BaseController {
	
	@Autowired
	private SmFamilyService smFamilyService;
		
	/**
	 * 获取校友家庭成员列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smFamily
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmFamily> list(int pageNo,int pageSize,SmFamily smFamily) {
		PageIo<SmFamily> pageInfo =  smFamilyService.loadByPage(pageNo,pageSize,smFamily);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友家庭成员
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smFamily
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmFamily> save(SmFamily smFamily){
		smFamilyService.save(smFamily);
		return new RestResponse<SmFamily>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smFamily);							
	}	
	
	/**
	 * 删除校友家庭成员（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smFamily
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmFamily smFamily) {
		smFamilyService.delete(smFamily);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友家庭成员
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smFamily
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmFamily> get(SmFamily smFamily) {
		return new RestResponse<SmFamily>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smFamilyService.get(smFamily));	
	}
	
	/**
	 * 获取校友家庭成员列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smAddress
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmFamily>> loadAllListBy(SmFamily smFamily) {						
		return new RestResponse<List<SmFamily>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				smFamilyService.loadAllListBy(smFamily));		
	}
}