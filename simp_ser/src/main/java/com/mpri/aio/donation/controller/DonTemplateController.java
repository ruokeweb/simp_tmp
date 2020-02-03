package com.mpri.aio.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.mpri.aio.donation.model.DonTemplate;
import com.mpri.aio.donation.service.DonTemplateService;

 /**   
 *  
 * @Description:  捐赠证书模板表——Controller
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 20 13:57:44 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/don/donTemplate")
public class DonTemplateController extends BaseController {
	
	@Autowired
	private DonTemplateService donTemplateService;
		
	/**
	 * 获取捐赠证书模板表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param donTemplate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<DonTemplate> list(int pageNo,int pageSize,DonTemplate donTemplate) {
		PageIo<DonTemplate> pageInfo =  donTemplateService.loadByPage(pageNo,pageSize,donTemplate);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新捐赠证书模板表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param donTemplate
	* @return
	 */
	@Logs(value = "捐赠证书模板编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(DonTemplate donTemplate){
		donTemplateService.save(donTemplate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除捐赠证书模板表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param donTemplate
	* @return
	 */
	@Logs(value = "捐赠证书模板删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(DonTemplate donTemplate) {
		donTemplateService.delete(donTemplate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取捐赠证书模板表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param donTemplate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<DonTemplate> get(DonTemplate donTemplate) {
		return new RestResponse<DonTemplate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				donTemplateService.get(donTemplate));	
	}
		
}