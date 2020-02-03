package com.mpri.aio.act.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.act.service.ActSettingService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;

 /**   
 *  
 * @Description:  活动——Controller
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:28:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/act/actSetting")
public class ActSettingController extends BaseController {
	
	@Autowired
	private ActSettingService actSettingService;
		
	/**
	 * 获取活动列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actSetting
	* @return
	 */
	
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ActSetting> list(int pageNo,int pageSize,ActSetting actSetting) {
		PageIo<ActSetting> pageInfo =  actSettingService.loadByPage(pageNo,pageSize,actSetting);							
		return pageInfo;
	}
	/**
	 * 获取活动设置全部列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actSetting
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAll")
	public RestResponse<List<ActSetting>> loadAll(ActSetting actSetting) {
		List<ActSetting> list =  actSettingService.loadAllListBy(actSetting);				
		return new RestResponse<List<ActSetting>>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", list);	
	}
	
	
	/**
	 * 增加或者更新活动
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param actSetting
	* @return
	 */
	@Logs(value = "活动设置修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ActSetting actSetting){
		ActSetting actSetting1 = new ActSetting();
		actSetting1.setActivityId(actSetting.getActivityId());
		List<ActSetting> list = actSettingService.loadAllListBy(actSetting1);
		if(!list.isEmpty()&&actSetting.getIsNewRecord()){
			for (ActSetting act :list) {
				if(act.getCode().equals(actSetting.getCode())){
					return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "编码不能重复！", "");
				}
			}
		}
		actSettingService.save(actSetting);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除活动（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param actSetting
	* @return
	 */
	@Logs(value = "活动设置删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ActSetting actSetting) {
		actSettingService.delete(actSetting);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取活动
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param actSetting
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ActSetting> get(ActSetting actSetting) {
		return new RestResponse<ActSetting>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				actSettingService.get(actSetting));	
	}
		
}