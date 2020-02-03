package com.mpri.aio.message.controller;

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
import com.mpri.aio.message.model.MesUserMessage;
import com.mpri.aio.message.service.MesUserMessageService;

 /**   
 *  
 * @Description:  用户消息标记表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:32:26 CST 2018
 * @Version:      v_1.02
 *    
 */
@RestController
@RequestMapping("/mes/mesUserMessage")
public class MesUserMessageController extends BaseController {
	
	@Autowired
	private MesUserMessageService mesUserMessageService;
		
	/**
	 * 获取用户消息标记表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesUserMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesUserMessage> list(int pageNo,int pageSize,MesUserMessage mesUserMessage) {
		PageIo<MesUserMessage> pageInfo =  mesUserMessageService.loadByPage(pageNo,pageSize,mesUserMessage);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新用户消息标记表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesUserMessage
	* @return
	 */
	@Logs(value = "用户消息标记编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesUserMessage mesUserMessage){
		mesUserMessageService.save(mesUserMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除用户消息标记表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesUserMessage
	* @return
	 */
	@Logs(value = "用户消息标记删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesUserMessage mesUserMessage) {
		mesUserMessageService.delete(mesUserMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取用户消息标记表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesUserMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesUserMessage> get(MesUserMessage mesUserMessage) {
		return new RestResponse<MesUserMessage>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesUserMessageService.get(mesUserMessage));	
	}
		
}