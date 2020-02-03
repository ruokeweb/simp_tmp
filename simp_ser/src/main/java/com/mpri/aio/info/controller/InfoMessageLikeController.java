package com.mpri.aio.info.controller;

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
import com.mpri.aio.info.model.InfoMessageLike;
import com.mpri.aio.info.service.InfoMessageLikeService;

 /**   
 *  
 * @Description:  信息点赞表表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Fri Dec 14 13:55:56 CST 2018
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/info/infoMessageLike")
public class InfoMessageLikeController extends BaseController {
	
	@Autowired
	private InfoMessageLikeService infoMessageLikeService;
		
	/**
	 * 获取信息点赞表表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param infoMessageLike
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<InfoMessageLike> list(int pageNo,int pageSize,InfoMessageLike infoMessageLike) {
		PageIo<InfoMessageLike> pageInfo =  infoMessageLikeService.loadByPage(pageNo,pageSize,infoMessageLike);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新信息点赞表表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param infoMessageLike
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated InfoMessageLike infoMessageLike){
		infoMessageLikeService.save(infoMessageLike);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息点赞表表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param infoMessageLike
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(InfoMessageLike infoMessageLike) {
		infoMessageLikeService.delete(infoMessageLike);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息点赞表表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param infoMessageLike
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<InfoMessageLike> get(InfoMessageLike infoMessageLike) {
		return new RestResponse<InfoMessageLike>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				infoMessageLikeService.get(infoMessageLike));	
	}
		
}