package com.mpri.aio.settings.controller;

import com.mpri.aio.common.logs.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.service.SmStarService;

 /**   
 *  
 * @Description:  星级——Controller
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:53:53 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/settings/smStar")
public class SmStarController extends BaseController {
	
	@Autowired
	private SmStarService smStarService;
		
	/**
	 * 获取星级列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smStar
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmStar> list(int pageNo,int pageSize,SmStar smStar) {
		PageIo<SmStar> pageInfo =  smStarService.loadByPage(pageNo,pageSize,smStar);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新星级
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smStar
	* @return
	 */
	@Logs(value = "星级设置更新",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SmStar smStar){
		smStarService.save(smStar);							
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除星级（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smStar
	* @return
	 */
	@Logs(value = "星级设置删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmStar smStar) {
		smStarService.delete(smStar);
		return  new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取星级
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smStar
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public SmStar get(SmStar smStar) {
		return smStarService.get(smStar);
	}
		
}