package com.mpri.aio.act.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.act.service.ActSelforgContentService;

 /**   
 *  
 * @Description:  值年返校内容——Controller
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Mon May 27 15:50:50 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/actContent/actSelforgContent")
public class ActSelforgContentController extends BaseController {
	
	@Autowired
	private ActSelforgContentService actSelforgContentService;
		
	/**
	 * 获取值年返校内容列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actSelforgContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ActSelforgContent> list(int pageNo,int pageSize,ActSelforgContent actSelforgContent) {
		PageIo<ActSelforgContent> pageInfo =  actSelforgContentService.loadByPageAndName(pageNo,pageSize,actSelforgContent);

		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新值年返校内容
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param actSelforgContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ActSelforgContent actSelforgContent){
		actSelforgContentService.save(actSelforgContent);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除值年返校内容（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param actSelforgContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ActSelforgContent actSelforgContent) {
		actSelforgContentService.delete(actSelforgContent);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取值年返校内容
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param actSelforgContent
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ActSelforgContent> get(ActSelforgContent actSelforgContent) {
		return new RestResponse<ActSelforgContent>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				actSelforgContentService.get(actSelforgContent));	
	}
		
}