package com.mpri.aio.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.service.SysIndustryService;

 /**   
 *  
 * @Description:  行业管理——Controller
 * @Author:       Carry
 * @project 	  simp 
 * @CreateDate:   Thu Feb 21 15:52:16 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/sys/industry")
public class SysIndustryController extends BaseController {
	
	@Autowired
	private SysIndustryService sysIndustryService;
	
	@Autowired 
	private RedisCacheService redisCacheService;
	
	/**
	 * 获取树
	* <p>Title: list</p>  	
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysOrg
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public ResJson<SysIndustry> tree(SysIndustry sysIndustry) {
		ResJson<SysIndustry> rj = new ResJson<SysIndustry>();
		List<SysIndustry> list = sysIndustryService.loadAllListBy(sysIndustry);							
		rj.setData(list);
		return rj;
	}

	
	/**
	 * 获取行业管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysIndustry
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SysIndustry> list(int pageNo,int pageSize,SysIndustry sysIndustry) {
		PageIo<SysIndustry> pageInfo =  sysIndustryService.loadByPage(pageNo,pageSize,sysIndustry);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新行业管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param sysIndustry
	* @return
	 */
	@Logs(value = "行业修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SysIndustry sysIndustry){
		SysIndustry parent = new SysIndustry();
		parent.setId(sysIndustry.getParentId());
		parent = sysIndustryService.get(parent);
		sysIndustry.setParentIds(parent.getParentIds()+","+parent.getId());
		sysIndustryService.save(sysIndustry);
		//更新初始化缓存
		redisCacheService.putCache(sysIndustryService, new SysIndustry() ,InitCache.SYS_BASE_INDUSTRY_CACHE,InitCache.SYS_INDUSTRY_CACHE);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除行业管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param sysIndustry
	* @return
	 */
	@Logs(value = "行业删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysIndustry sysIndustry) {
		int count = sysIndustryService.loadChildsBy(sysIndustry);
		if(count>0){
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "该行业有子级，删除失败！", "");
		}
		sysIndustryService.delete(sysIndustry);
		//更新初始化缓存
		redisCacheService.putCache(sysIndustryService, new SysIndustry() ,InitCache.SYS_BASE_INDUSTRY_CACHE,InitCache.SYS_INDUSTRY_CACHE);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取行业管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param sysIndustry
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysIndustry> get(SysIndustry sysIndustry) {
		SysIndustry res = sysIndustryService.get(sysIndustry);
		SysIndustry parent = new SysIndustry();
		parent.setId(res.getParentId());
		res.setParentSysIndustry(sysIndustryService.get(parent));
		return new RestResponse<SysIndustry>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", res);
	
	}
		
}