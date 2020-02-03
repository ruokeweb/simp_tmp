package com.mpri.aio.act.controller;

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
import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.act.service.ActContentService;

 /**   
 *  
 * @Description:  活动——Controller
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:30:13 CST 2019
 * @Version:      v_1.2
 *    
 */ 
@RestController
@RequestMapping("/act/actContent")
public class ActContentController extends BaseController {
	
	@Autowired
	private ActContentService actContentService;
		
	/**
	 * 获取活动列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ActContent> list(int pageNo,int pageSize,ActContent actContent) {
		PageIo<ActContent> pageInfo =  actContentService.loadContent(pageNo, pageSize, actContent);
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新活动
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param actContent
	* @return
	 */
	@Logs(value = "活动内容修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ActContent actContent){
		actContentService.save(actContent);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除活动（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param actContent
	* @return
	 */
	@Logs(value = "活动内容删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ActContent actContent) {
		actContentService.delete(actContent);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取活动
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param actContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ActContent> get(ActContent actContent) {
		return new RestResponse<ActContent>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				actContentService.get(actContent));	
	}
		
}