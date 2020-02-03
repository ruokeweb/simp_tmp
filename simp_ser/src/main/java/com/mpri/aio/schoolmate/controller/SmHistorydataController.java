package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmHistorydata;
import com.mpri.aio.schoolmate.service.SmHistorydataService;

 /**   
 *  
 * @Description:  校友历史数据表——Controller
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:54:51 CST 2019
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smHistorydata")
public class SmHistorydataController extends BaseController {
	
	@Autowired
	private SmHistorydataService smHistorydataService;
		
	/**
	 * 获取校友历史数据表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smHistorydata
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmHistorydata> list(int pageNo,int pageSize,SmHistorydata smHistorydata) {
		PageIo<SmHistorydata> pageInfo =  smHistorydataService.loadByPage(pageNo,pageSize,smHistorydata);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友历史数据表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smHistorydata
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmHistorydata> save(SmHistorydata smHistorydata){
		smHistorydataService.save(smHistorydata);
		return new RestResponse<SmHistorydata>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smHistorydata);							
	}	
	
	/**
	 * 删除校友历史数据表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smHistorydata
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmHistorydata smHistorydata) {
		smHistorydataService.delete(smHistorydata);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友历史数据表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smHistorydata
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmHistorydata> get(SmHistorydata smHistorydata) {
		return new RestResponse<SmHistorydata>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smHistorydataService.get(smHistorydata));	
	}

	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmHistorydata>> loadAllListBy(SmHistorydata smHistorydata) {					
		return new RestResponse<List<SmHistorydata>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smHistorydataService.loadAllListBy(smHistorydata));	
	}	
}