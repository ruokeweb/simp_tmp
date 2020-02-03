package com.mpri.aio.settings.controller;

import com.mpri.aio.common.logs.Logs;
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
import com.mpri.aio.settings.model.SettingCardElement;
import com.mpri.aio.settings.service.SettingCardElementService;

 /**   
 *  
 * @Description:  校友卡元素管理——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Mon Feb 18 10:33:48 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/settingCardElement")
public class SettingCardElementController extends BaseController {
	
	@Autowired
	private SettingCardElementService settingCardElementService;
		
	/**
	 * 获取校友卡元素管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingCardElement
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingCardElement> list(int pageNo,int pageSize,SettingCardElement settingCardElement) {
		PageIo<SettingCardElement> pageInfo =  settingCardElementService.loadByCardIdNotNo(pageNo,pageSize,settingCardElement);
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友卡元素管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingCardElement
	* @return
	 */
	@Logs(value = "校友卡元素修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingCardElement settingCardElement){
		settingCardElementService.save(settingCardElement);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友卡元素管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingCardElement
	* @return
	 */
	@Logs(value = "校友卡元素删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingCardElement settingCardElement) {
		settingCardElementService.delete(settingCardElement);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友卡元素管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingCardElement
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingCardElement> get(SettingCardElement settingCardElement) {
		return new RestResponse<SettingCardElement>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				settingCardElementService.get(settingCardElement));	
	}
		
}