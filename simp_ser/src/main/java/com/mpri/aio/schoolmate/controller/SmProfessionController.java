package com.mpri.aio.schoolmate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.service.SmProfessionService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysIndustry;

 /**   
 *  
 * @Description:  校友职业经历——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:24 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smProfession")
public class SmProfessionController extends BaseController {
	
	@Autowired
	private SmProfessionService smProfessionService;

	@Autowired 
	private RedisCacheService redisCacheService;
	/**
	 * 获取校友职业经历列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smProfession
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmProfession> list(int pageNo,int pageSize,SmProfession smProfession) {
		PageIo<SmProfession> pageInfo =  smProfessionService.loadByPage(pageNo,pageSize,smProfession);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友职业经历
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smProfession
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmProfession> save(SmProfession smProfession){
		//设置不是单位
		smProfession.setType(GlobalStr.NOT_DEFAULT);
		smProfessionService.save(smProfession);
		return new RestResponse<SmProfession>(ExceptionResult.REQUEST_SUCCESS, "保存成功！",smProfession);							
	}	
	
	/**
	 * 删除校友职业经历（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smProfession
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmProfession smProfession) {
		smProfessionService.delete(smProfession);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友职业经历
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smProfession
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmProfession> get(SmProfession smProfession) {
		return new RestResponse<SmProfession>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smProfessionService.get(smProfession));	
	}
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmProfession>> loadAllListBy(SmProfession smProfession) {
		//设置不是单位
//		smProfession.setType(GlobalStr.NOT_DEFAULT);
		List<SmProfession> res = smProfessionService.loadAllListBy(smProfession);
		Map<String,SysIndustry> sysBaseIndustryCache = 
				(Map<String,SysIndustry>)redisCacheService.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE);		
		for (SmProfession sProfession : res) {
			System.out.println(sProfession.getIndustry());
			if(StringUtil.isNotEmpty(sProfession.getIndustry())) {
				sProfession.setIndustryName(sysBaseIndustryCache.get(sProfession.getIndustry()).getName());	
			}
		}
		return new RestResponse<List<SmProfession>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				res);
	}
}