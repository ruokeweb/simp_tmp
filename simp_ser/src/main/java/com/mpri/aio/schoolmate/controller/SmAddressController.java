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
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.service.SmAddressService;

 /**   
 *  
 * @Description:  校友通讯地址——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:31:22 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smAddress")
public class SmAddressController extends BaseController {
	
	@Autowired
	private SmAddressService smAddressService;
		
	/**
	 * 获取校友通讯地址列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smAddress
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmAddress> list(int pageNo,int pageSize,SmAddress smAddress) {
		PageIo<SmAddress> pageInfo =  smAddressService.loadByPage(pageNo,pageSize,smAddress);							
		return pageInfo;
	}
	
	
	/**
	 * 获取校友通讯地址列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smAddress
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmAddress>> loadAllListBy(SmAddress smAddress) {						
		return new RestResponse<List<SmAddress>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				smAddressService.loadAllListBy(smAddress));		
	}
	
	
	/**
	 * 增加或者更新校友通讯地址
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smAddress
	* @return
	 */
	@Logs(value = "校友通讯地址修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmAddress> save(SmAddress smAddress){
		smAddressService.save(smAddress);
		return new RestResponse<SmAddress>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smAddress);							
	}	
	
	/**
	 * 删除校友通讯地址（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smAddress
	* @return
	 */
	@Logs(value = "校友通讯地址删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmAddress smAddress) {
		smAddressService.delete(smAddress);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友通讯地址
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smAddress
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmAddress> get(SmAddress smAddress) {
		return new RestResponse<SmAddress>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smAddressService.get(smAddress));	
	}
		
}