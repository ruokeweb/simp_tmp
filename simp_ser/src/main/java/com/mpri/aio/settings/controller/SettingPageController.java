package com.mpri.aio.settings.controller;

import java.util.List;

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
import com.mpri.aio.settings.model.SettingPage;
import com.mpri.aio.settings.service.SettingPageService;

 /**   
 *  
 * @Description:  页面信息配置——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Tue May 28 14:54:02 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/settingPage")
public class SettingPageController extends BaseController {
	
	@Autowired
	private SettingPageService settingPageService;
		
	/**
	 * 获取页面信息配置列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingPage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingPage> list(int pageNo,int pageSize,SettingPage settingPage) {
		PageIo<SettingPage> pageInfo =  settingPageService.loadByPage(pageNo,pageSize,settingPage);							
		return pageInfo;
	}

	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SettingPage>> loadAllListBy(SettingPage settingPage) {
		List<SettingPage> list =  settingPageService.loadAllListBy(settingPage);							
		return new RestResponse<List<SettingPage>>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", list);	
	}	
	
	/**
	 * 增加或者更新页面信息配置
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingPage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingPage settingPage){
		settingPageService.save(settingPage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除页面信息配置（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingPage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingPage settingPage) {
		settingPageService.delete(settingPage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取页面信息配置
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingPage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingPage> get(SettingPage settingPage) {
		return new RestResponse<SettingPage>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				settingPageService.get(settingPage));	
	}
		
}