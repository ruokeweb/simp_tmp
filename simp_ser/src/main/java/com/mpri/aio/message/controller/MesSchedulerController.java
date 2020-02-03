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
import com.mpri.aio.message.model.MesScheduler;
import com.mpri.aio.message.service.MesSchedulerService;

 /**   
 *  
 * @Description:  定时通知消息——Controller
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Tue Mar 12 15:51:03 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/mes/mesScheduler")
public class MesSchedulerController extends BaseController {
	
	@Autowired
	private MesSchedulerService mesSchedulerService;
		
	/**
	 * 获取定时通知消息列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesScheduler
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesScheduler> list(int pageNo,int pageSize,MesScheduler mesScheduler) {
		PageIo<MesScheduler> pageInfo =  mesSchedulerService.loadByPage(pageNo,pageSize,mesScheduler);							
		return pageInfo;
	}  
	
	 
	/**
	 * 增加或者更新定时通知消息
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesScheduler
	* @return
	 */
	@Logs(value = "定时通知消息编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesScheduler mesScheduler){
		mesSchedulerService.save(mesScheduler);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除定时通知消息（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesScheduler
	* @return
	 */
	@Logs(value = "定时通知消息删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesScheduler mesScheduler) {
		mesSchedulerService.delete(mesScheduler);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取定时通知消息
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesScheduler
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesScheduler> get(MesScheduler mesScheduler) {
		return new RestResponse<MesScheduler>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesSchedulerService.get(mesScheduler));	
	}
		
}