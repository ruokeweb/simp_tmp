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
import com.mpri.aio.schoolmate.model.SmOtherInfo;
import com.mpri.aio.schoolmate.service.SmOtherInfoService;

 /**   
 *  
 * @Description:  校友卡其他信息——Controller
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Mon Mar 04 17:51:29 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/sm/smOtherInfo")
public class SmOtherInfoController extends BaseController {
	
	@Autowired
	private SmOtherInfoService smOtherInfoService;
		
	/**
	 * 获取校友卡其他信息列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smOtherInfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmOtherInfo> list(int pageNo,int pageSize,SmOtherInfo smOtherInfo) {
		PageIo<SmOtherInfo> pageInfo =  smOtherInfoService.loadByPage(pageNo,pageSize,smOtherInfo);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友卡其他信息
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smOtherInfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmOtherInfo> save(@Validated SmOtherInfo smOtherInfo){
		smOtherInfoService.save(smOtherInfo);
		return new RestResponse<SmOtherInfo>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smOtherInfo);							
	}	
	
	/**
	 * 删除校友卡其他信息（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smOtherInfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmOtherInfo smOtherInfo) {
		smOtherInfoService.delete(smOtherInfo);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友卡其他信息
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smOtherInfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmOtherInfo> get(SmOtherInfo smOtherInfo) {
		return new RestResponse<SmOtherInfo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smOtherInfoService.get(smOtherInfo));	
	}
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmOtherInfo>> loadAllListBy(SmOtherInfo smOtherInfo) {						
		return new RestResponse<List<SmOtherInfo>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smOtherInfoService.loadAllListBy(smOtherInfo));
	}
}