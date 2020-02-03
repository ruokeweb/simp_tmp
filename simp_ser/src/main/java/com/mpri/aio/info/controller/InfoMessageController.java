package com.mpri.aio.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.info.model.InfoMessage;
import com.mpri.aio.info.service.InfoMessageService;

 /**   
 *  
 * @Description:  信息留言表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Fri Dec 14 09:44:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/info/infoMessage")
public class InfoMessageController extends BaseController {
	
	@Autowired
	private InfoMessageService infoMessageService;
		
	/**
	 * 获取信息留言表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param infoMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<InfoMessage> list(int pageNo,int pageSize,InfoMessage infoMessage) {
		PageIo<InfoMessage> pageInfo =  infoMessageService.loadByPage(pageNo,pageSize,infoMessage);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新信息留言表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param infoMessage
	* @return
	 */
	@Logs(value = "信息留言编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated InfoMessage infoMessage){
		infoMessageService.save(infoMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息留言表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param infoMessage
	* @return
	 */
	@Logs(value = "信息留言删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(InfoMessage infoMessage) {
		infoMessageService.delete(infoMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息留言表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param infoMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<InfoMessage> get(InfoMessage infoMessage) {
		return new RestResponse<InfoMessage>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				infoMessageService.get(infoMessage));	
	}
		
}