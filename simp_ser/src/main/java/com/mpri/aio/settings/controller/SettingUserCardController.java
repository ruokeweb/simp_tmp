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
import com.mpri.aio.settings.model.SettingUserCard;
import com.mpri.aio.settings.service.SettingUserCardService;

 /**   
 *  
 * @Description:  用户校友卡——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Thu Nov 07 17:32:00 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/settings/settingUserCard")
public class SettingUserCardController extends BaseController {
	
	@Autowired
	private SettingUserCardService settingUserCardService;
		
	/**
	 * 获取用户校友卡列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingUserCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingUserCard> list(int pageNo,int pageSize,SettingUserCard settingUserCard) {
		PageIo<SettingUserCard> pageInfo =  settingUserCardService.loadByPage(pageNo,pageSize,settingUserCard);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新用户校友卡
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingUserCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingUserCard settingUserCard){
		settingUserCardService.save(settingUserCard);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除用户校友卡（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingUserCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingUserCard settingUserCard) {
		settingUserCardService.delete(settingUserCard);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取用户校友卡
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingUserCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingUserCard> get(SettingUserCard settingUserCard) {
		return new RestResponse<SettingUserCard>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				settingUserCardService.get(settingUserCard));	
	}
		
}