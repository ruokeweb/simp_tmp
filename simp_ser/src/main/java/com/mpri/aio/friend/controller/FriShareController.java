package com.mpri.aio.friend.controller;

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
import com.mpri.aio.friend.model.FriShare;
import com.mpri.aio.friend.service.FriShareService;

 /**   
 *  
 * @Description:  用户分享——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Fri Nov 15 10:12:35 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/fri/friShare")
public class FriShareController extends BaseController {
	
	@Autowired
	private FriShareService friShareService;
		
	/**
	 * 获取用户分享列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param friShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<FriShare> list(int pageNo,int pageSize,FriShare friShare) {
		PageIo<FriShare> pageInfo =  friShareService.loadByPage(pageNo,pageSize,friShare);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新用户分享
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param friShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated FriShare friShare){
		friShareService.save(friShare);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除用户分享（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param friShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(FriShare friShare) {
		friShareService.delete(friShare);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取用户分享
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param friShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<FriShare> get(FriShare friShare) {
		return new RestResponse<FriShare>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				friShareService.get(friShare));	
	}
		
}