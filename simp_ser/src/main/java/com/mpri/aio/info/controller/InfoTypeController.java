package com.mpri.aio.info.controller;

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
import com.mpri.aio.info.model.InfoType;
import com.mpri.aio.info.service.InfoTypeService;

 /**   
 *  
 * @Description:  信息类型表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Dec 10 09:52:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/info/infoType")
public class InfoTypeController extends BaseController {
	
	@Autowired
	private InfoTypeService infoTypeService;
		
	/**
	 * 获取信息类型表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param infoType
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<InfoType> list(int pageNo,int pageSize,InfoType infoType) {
		PageIo<InfoType> pageInfo =  infoTypeService.loadByPage(pageNo,pageSize,infoType);							
		return pageInfo;
	}
	
	/**
	 * 获取信息类型表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param infoType
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<InfoType>> loadAllListBy(InfoType infoType) {						
		return new RestResponse<List<InfoType>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", infoTypeService.loadAllListBy(infoType));
	}
	
	
	/**
	 * 增加或者更新信息类型表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param infoType
	* @return
	 */
	@Logs(value = "信息类型编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated InfoType infoType){
		infoTypeService.save(infoType);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息类型表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param infoType
	* @return
	 */
	@Logs(value = "信息类型删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(InfoType infoType) {
		infoTypeService.delete(infoType);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息类型表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param infoType
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<InfoType> get(InfoType infoType) {
		return new RestResponse<InfoType>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				infoTypeService.get(infoType));	
	}
		
}