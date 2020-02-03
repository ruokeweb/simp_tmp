package com.mpri.aio.settings.controller;

import java.util.Map;
import java.util.TreeMap;

import com.mpri.aio.common.logs.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.vo.StarInfoEnum;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.model.SmStarSminfo;
import com.mpri.aio.settings.service.SmStarService;
import com.mpri.aio.settings.service.SmStarSminfoService;

 /**   
 *  
 * @Description:  星级设置——Controller
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:52:29 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/settings/smStarSminfo")
public class SmStarSminfoController extends BaseController {
	
	@Autowired
	private SmStarSminfoService smStarSminfoService;
	
	@Autowired
	private SmStarService smStarService;
	/**
	 * 获取星级设置列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smStarSminfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmStarSminfo> list(int pageNo,int pageSize,SmStarSminfo smStarSminfo) {
		PageIo<SmStarSminfo> pageInfo =  smStarSminfoService.loadByPage(pageNo,pageSize,smStarSminfo);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新星级设置
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smStarSminfo
	* @return
	 */
	@Logs(value = "星级记录更新",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SmStarSminfo smStarSminfo){
		smStarSminfoService.save(smStarSminfo);							
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	/**
	 * 增加或者更新星级设置
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smStarSminfo
	* @return
	 */
	@Logs(value = "星级记录更新",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/saveStarInfo")
	public RestResponse<String> saveStarInfo(@RequestBody SmStarSminfo smStarSminfo){
		SmStar smStar = smStarSminfo.getSmStar();
		smStarService.save(smStar);
		smStarSminfoService.saveSmStarInfo(smStar, smStarSminfo.getStartInfoCodes());
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	/**
	 * 删除星级设置（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smStarSminfo
	* @return
	 */
	@Logs(value = "星级记录删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmStar smStar) {
		smStarService.delete(smStar);
		smStarSminfoService.deleteStarInfo(smStar.getId());
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取星级设置
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smStarSminfo
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/getInfo")
	public RestResponse<SmStarSminfo> getInfo(SmStar smStar) {
		SmStarSminfo info = smStarSminfoService.getInfo(smStar);
		return new RestResponse<SmStarSminfo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", info);
	}
	
	
	/**
	 * 枚举信息类型
	* <p>Title: getStarInfo</p>  
	* <p>Description: </p>  
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/getStarInfo")
	public RestResponse<Map<String, String>> getStarInfo() {
	    /** 类型 */
	    Map<String, String> info = new TreeMap<String, String>();
	    
	    for (StarInfoEnum s : StarInfoEnum.values()) {
			info.put(s.getCode(), s.getDesc());
		}
	    
	    return new RestResponse<Map<String, String>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", info);
	}
}