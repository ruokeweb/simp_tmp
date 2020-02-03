package com.mpri.aio.settings.controller;

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
import com.mpri.aio.settings.model.SettingWebvsb;
import com.mpri.aio.settings.service.SettingWebvsbService;

 /**   
 *  
 * @Description:  校友网配置表——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Sep 09 10:08:46 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/settings/settingwebvsb")
public class SettingWebvsbController extends BaseController {
	
	@Autowired
	private SettingWebvsbService settingWebvsbService;
		
	/**
	 * 获取校友网配置表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingWebvsb
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingWebvsb> list(int pageNo,int pageSize,SettingWebvsb settingWebvsb) {
		PageIo<SettingWebvsb> pageInfo =  settingWebvsbService.loadByPage(pageNo,pageSize,settingWebvsb);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友网配置表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingWebvsb
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingWebvsb settingWebvsb){
		settingWebvsbService.save(settingWebvsb);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友网配置表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingWebvsb
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingWebvsb settingWebvsb) {
		settingWebvsbService.delete(settingWebvsb);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友网配置表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingWebvsb
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingWebvsb> get(SettingWebvsb settingWebvsb) {
		return new RestResponse<SettingWebvsb>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				settingWebvsbService.get(settingWebvsb));	
	}
		
}