package com.mpri.aio.schoolmate.controller;

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
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmOther;
import com.mpri.aio.schoolmate.service.SmOtherService;

 /**   
 *  
 * @Description:  校友其他信息类型表——Controller
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Sat Mar 02 16:01:26 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/sm/smOther")
public class SmOtherController extends BaseController {
	
	@Autowired
	private SmOtherService smOtherService;
		
	/**
	 * 获取校友其他信息类型表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smOther
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmOther> list(int pageNo,int pageSize,SmOther smOther) {
		PageIo<SmOther> pageInfo =  smOtherService.loadByPage(pageNo,pageSize,smOther);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友其他信息类型表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smOther
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SmOther smOther){
		smOtherService.save(smOther);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友其他信息类型表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smOther
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmOther smOther) {
		Boolean flag =smOtherService.isUseAble(smOther.getId());
		if(flag) {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "该信息类型已被使用，无法删除！", "");
		}
		smOtherService.delete(smOther);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友其他信息类型表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smOther
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmOther> get(SmOther smOther) {
		return new RestResponse<SmOther>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smOtherService.get(smOther));	
	}
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmOther>> loadAllListBy(SmOther smOther) {						
		return new RestResponse<List<SmOther>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smOtherService.loadAllListBy(smOther));
	}
	
		
}