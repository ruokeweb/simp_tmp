package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.service.SmContactService;

 /**   
 *  
 * @Description:  校友联系方式——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:30:05 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smContact")
public class SmContactController extends BaseController {
	
	@Autowired
	private SmContactService smContactService;
		
	/**
	 * 获取校友联系方式列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smContact
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmContact> list(int pageNo,int pageSize,SmContact smContact) {
		PageIo<SmContact> pageInfo =  smContactService.loadByPage(pageNo,pageSize,smContact);							
		return pageInfo;
	}
	
	
	/**
	 * 获取校友联系方式列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smContact
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmContact>> loadAllListBy(SmContact smContact) {			
		return new RestResponse<List<SmContact>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				smContactService.loadAllListBy(smContact));		
	}
	
	/**
	 * 增加或者更新校友联系方式
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smContact
	* @return
	 */
	@Logs(value = "联系方式修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmContact> save(@Validated SmContact smContact){
		smContactService.save(smContact);
		return new RestResponse<SmContact>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smContact);							
	}	
	
	/**
	 * 删除校友联系方式（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smContact
	* @return
	 */
	@Logs(value = "联系方式删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmContact smContact) {
		smContactService.delete(smContact);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友联系方式
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smContact
	* @return
	 */
	@CrossOrigin 
	@PostMapping(value = "/get")
	public RestResponse<SmContact> get(SmContact smContact) {
		return new RestResponse<SmContact>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smContactService.get(smContact));	
	}
		
}