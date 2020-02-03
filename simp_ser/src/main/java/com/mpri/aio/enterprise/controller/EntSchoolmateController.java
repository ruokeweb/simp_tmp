package com.mpri.aio.enterprise.controller;

import java.util.List;

import com.github.pagehelper.util.StringUtil;
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
import com.mpri.aio.enterprise.model.EntSchoolmate;
import com.mpri.aio.enterprise.service.EntSchoolmateService;

/**
 *  
 * @Description:  校友企业校友任职表——Controller
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Thu Feb 14 15:59:00 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/ent/entSchoolmate")
public class EntSchoolmateController extends BaseController {
	
	@Autowired
	private EntSchoolmateService entSchoolmateService;
		
	/**
	 * 获取校友企业校友任职表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param entSchoolmate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<EntSchoolmate> list(int pageNo,int pageSize,EntSchoolmate entSchoolmate) {
//		PageIo<EntSchoolmate> pageInfo =  entSchoolmateService.loadByPage(pageNo,pageSize,entSchoolmate);

		if("root".equals(entSchoolmate.getEnterId()) || "ROOT".equals(entSchoolmate.getEnterId()))
		{
			entSchoolmate.setEnterId("");
		}


		PageIo<EntSchoolmate> pageInfo =  entSchoolmateService.loadSchoolByPage(pageNo,pageSize,entSchoolmate);
		return pageInfo;
	}

	/**
	 * 增加或者更新校友企业校友任职表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param entSchoolmate
	* @return
	 */
	@Logs(value = "校友企业校友任职编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated EntSchoolmate entSchoolmate){

		EntSchoolmate entSch = new EntSchoolmate();
		entSch.setEnterId(entSchoolmate.getEnterId());
		entSch.setUserId(entSchoolmate.getUserId());
		List<EntSchoolmate> list =entSchoolmateService.loadAllListBy(entSchoolmate);

		if(StringUtil.isEmpty(entSchoolmate.getId())&&null!=list && list.size() >0)
		{
			return new RestResponse<String>(ExceptionResult.DATA_USED, "该校友会下已经存在此校友", "");
		}

		entSchoolmateService.save(entSchoolmate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友企业校友任职表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param entSchoolmate
	* @return
	 */
	@Logs(value = "校友企业校友任职删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(EntSchoolmate entSchoolmate) {
		entSchoolmateService.delete(entSchoolmate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友企业校友任职表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param entSchoolmate
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<EntSchoolmate> get(EntSchoolmate entSchoolmate) {
		return new RestResponse<EntSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				entSchoolmateService.get(entSchoolmate));	
	}
		
}