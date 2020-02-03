package com.mpri.aio.system.controller;

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
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.service.SysSettingService;

 /**   
 *  
 * @Description:  配置信息——Controller
 * @Author:       Carry
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 13 10:22:06 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/sys/setting")
public class SysSettingController extends BaseController {
	
	@Autowired
	private SysSettingService sysSettingService;
	
	
	@Autowired 
	private RedisCacheService redisCacheService;
	/**
	 * 获取配置信息列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysSetting
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SysSetting> list(int pageNo,int pageSize,SysSetting sysSetting) {
		PageIo<SysSetting> pageInfo =  sysSettingService.loadByPage(pageNo,pageSize,sysSetting);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新配置信息
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param sysSetting
	* @return
	 */
	@Logs(value = "配置信息编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(SysSetting sysSetting){
		sysSettingService.save(sysSetting);
		//更新缓存
		redisCacheService.putCache(sysSettingService, new SysSetting() ,InitCache.SETTING_BASE_CACHE,InitCache.SETTING_CACHE);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除配置信息（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param sysSetting
	* @return
	 */
	@Logs(value = "配置信息删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysSetting sysSetting) {
		sysSettingService.delete(sysSetting);
		//更新缓存
		redisCacheService.putCache(sysSettingService, new SysSetting() ,InitCache.SETTING_BASE_CACHE,InitCache.SETTING_CACHE);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取配置信息
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param sysSetting
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysSetting> get(SysSetting sysSetting) {
		return new RestResponse<SysSetting>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				sysSettingService.get(sysSetting));	
	}
		
}