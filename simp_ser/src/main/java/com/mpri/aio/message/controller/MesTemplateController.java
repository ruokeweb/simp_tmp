package com.mpri.aio.message.controller;

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
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.service.MesTemplateService;

 /**   
 *  
 * @Description:  消息模板表——Controller
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Thu Mar 07 18:43:59 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/mes/mesTemplate")
public class MesTemplateController extends BaseController {
	
	@Autowired
	private MesTemplateService mesTemplateService;
		
	/**
	 * 获取消息模板表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesTemplate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesTemplate> list(int pageNo,int pageSize,MesTemplate mesTemplate) {
		PageIo<MesTemplate> pageInfo =  mesTemplateService.loadByPage(pageNo,pageSize,mesTemplate);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新消息模板表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesTemplate
	* @return
	 */
	@Logs(value = "消息模板编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesTemplate mesTemplate){
		mesTemplateService.save(mesTemplate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除消息模板表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesTemplate
	* @return
	 */
	@Logs(value = "消息模板删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesTemplate mesTemplate) {
		mesTemplateService.delete(mesTemplate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取消息模板表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesTemplate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesTemplate> get(MesTemplate mesTemplate) {
		return new RestResponse<MesTemplate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesTemplateService.get(mesTemplate));	
	}
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<MesTemplate>> loadAllListBy(MesTemplate mesTemplate) {					
		return new RestResponse<List<MesTemplate>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesTemplateService.loadAllListBy(mesTemplate));	
	}	
}