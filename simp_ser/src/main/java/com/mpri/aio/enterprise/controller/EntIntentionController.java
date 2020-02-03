package com.mpri.aio.enterprise.controller;

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
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.enterprise.model.EntIntention;
import com.mpri.aio.enterprise.service.EntIntentionService;

/**
 *  
 * @Description:  校友企业校友意向表——Controller
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Thu Feb 14 16:35:23 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/ent/entIntention")
public class EntIntentionController extends BaseController {
	
	@Autowired
	private EntIntentionService entIntentionService;
		
	/**
	 * 获取校友企业校友意向表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param entIntention
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<EntIntention> list(int pageNo,int pageSize,EntIntention entIntention) {
		PageIo<EntIntention> pageInfo =  entIntentionService.loadEntAndSchoolByPage(pageNo,pageSize,entIntention);
		return pageInfo;
	}

	 /**
	  * 获取校友企业意向列表，带校友
	  * @param pageNo
	  * @param pageSize
	  * @param entIntention
	  * @return
	  */
	 @CrossOrigin
	 @PostMapping(value = "/smlist")
	 public PageIo<EntIntention> loadEntAndSchoolByPage(int pageNo, int pageSize, EntIntention entIntention) {
		 if("root".equals(entIntention.getEnterId()) || "ROOT".equals(entIntention.getEnterId()))
		 {
			 entIntention.setEnterId("");
		 }
		 PageIo<EntIntention> pageInfo =  entIntentionService.loadEntAndSchoolByPage(pageNo,pageSize,entIntention);
		 return pageInfo;
	 }
	
	/**
	 * 增加或者更新校友企业校友意向表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param entIntention
	* @return
	 */
	@Logs(value = "校友企业校友意向编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated EntIntention entIntention){
		entIntentionService.save(entIntention);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友企业校友意向表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param entIntention
	* @return
	 */
	@Logs(value = "校友企业校友意向删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(EntIntention entIntention) {
		entIntentionService.delete(entIntention);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友企业校友意向表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param entIntention
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<EntIntention> get(EntIntention entIntention) {
		return new RestResponse<EntIntention>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				entIntentionService.get(entIntention));	
	}
		
}