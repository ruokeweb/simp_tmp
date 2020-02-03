package com.mpri.aio.enterprise.controller;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.enterprise.model.EntEnterprise;
import com.mpri.aio.enterprise.service.EntEnterpriseService;

 /**   
 *  
 * @Description:  校友会信息表——Controller
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 12 08:41:49 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/ent/enterprise")
public class EntEnterpriseController extends BaseController {
	
	@Autowired
	private EntEnterpriseService entEnterpriseService;
		
	/**
	 * 获取校友企业信息表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param entEnterprise
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<EntEnterprise> list(int pageNo,int pageSize,EntEnterprise entEnterprise) {
		PageIo<EntEnterprise> pageInfo =  entEnterpriseService.loadByPage(pageNo,pageSize,entEnterprise);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友会信息表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param entEnterprise
	* @return
	 */
	@Logs(value = "校友企业信息编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated EntEnterprise entEnterprise){

		EntEnterprise entEnt = entEnterpriseService.getname(entEnterprise);

		if(StringUtil.isEmpty(entEnterprise.getId()) && null!=entEnterprise.getId() && null != entEnt)
		{
			return new RestResponse<String>(ExceptionResult.DATA_USED, "系统已经存在该企业信息！", "");
		}
		entEnterpriseService.save(entEnterprise);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友企业信息表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param entEnterprise
	* @return
	 */
	@Logs(value = "校友企业信息删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(EntEnterprise entEnterprise) {
		entEnterpriseService.delete(entEnterprise);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友企业信息表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param entEnterprise
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<EntEnterprise> get(EntEnterprise entEnterprise) {
		return new RestResponse<EntEnterprise>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				entEnterpriseService.get(entEnterprise));	
	}
		
}