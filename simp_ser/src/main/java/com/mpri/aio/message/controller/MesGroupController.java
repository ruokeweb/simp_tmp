package com.mpri.aio.message.controller;

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
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;

 /**   
 *  
 * @Description:  信息群组——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:33:03 CST 2018
 * @Version:      v_1.02
 *    
 */
@RestController
@RequestMapping("/mes/mesGroup")
public class MesGroupController extends BaseController {
	
	@Autowired
	private MesGroupService mesGroupService;
	
	@Autowired 
	private RedisCacheService redisCacheService;
		
	/**
	 * 获取信息群组列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesGroup
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesGroup> list(int pageNo,int pageSize,MesGroup mesGroup) {
		PageIo<MesGroup> pageInfo =  mesGroupService.loadByPage(pageNo,pageSize,mesGroup);							
		return pageInfo;
	}
	
	/**
	 * 获取所有
	* <p>Title: loadAllListBy</p>  
	* <p>Description: </p>  
	* @param sysArea
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<MesGroup>> loadAllListBy(MesGroup mesGroup) {
		return new RestResponse<List<MesGroup>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", mesGroupService.loadAllListBy(mesGroup));
	}
	
	
	/**
	 * 增加或者更新信息群组
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesGroup
	* @return
	 */
	@Logs(value = "信息群组编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesGroup mesGroup){
		mesGroupService.save(mesGroup);
		//更新缓存
		redisCacheService.putCache(mesGroupService, new MesGroup() ,InitCache.MESG_BASE_CACHE,null);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息群组（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesGroup
	* @return
	 */
	@Logs(value = "信息群组删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesGroup mesGroup) {
		mesGroupService.delete(mesGroup);
		//更新缓存
		redisCacheService.putCache(mesGroupService, new MesGroup() ,InitCache.MESG_BASE_CACHE,null);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息群组
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesGroup
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesGroup> get(MesGroup mesGroup) {
		return new RestResponse<MesGroup>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesGroupService.get(mesGroup));	
	}
		
}