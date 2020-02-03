package com.mpri.aio.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.vo.FormSelectDatas;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.service.SysAreaService;
import com.mpri.aio.system.vo.AreaVo;

/**
 * 区域管理Controller
* <p>Title: SysAreaController</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年8月18日
 */
@RestController
@RequestMapping("sys/sysarea")
public class SysAreaController extends BaseController {
	
	@Autowired
	private SysAreaService sysAreaService;
	
	@Autowired 
	private RedisCacheService redisCacheService;
	
	/**
	 * 
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysDict
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public ResJson<SysArea> tree(SysArea sysDict) {
		ResJson<SysArea> rj = new ResJson<SysArea>();
		List<SysArea> list =  sysAreaService.loadAllListBy(sysDict);	
		rj.setData(list);
		return rj;
	}
	
	/**
	 * 
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysDict
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageIo<SysArea> list(int pageNo,int pageSize,SysArea sysDict) {
		PageIo<SysArea> info = sysAreaService.loadByPage(pageNo,pageSize,sysDict);	
		return info;
	}	
	
	/**
	 * 
	* <p>Title: save</p>  
	* <p>Description: </p>  
	* @param sysDict
	* @return
	 */
	@Logs(value = "区域修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SysArea sysArea){
		if("Root".equals(sysArea.getParentId())|| null == sysArea.getParentId()) {
			//setRoot 目录
		}else {
			SysArea parentSysArea = new SysArea();
			parentSysArea.setId(sysArea.getParentId());
			parentSysArea = sysAreaService.get(parentSysArea);
			sysArea.setParentIds(parentSysArea.getParentIds()+","+parentSysArea.getId());
		}
		sysAreaService.save(sysArea);
		//更新初始化缓存
		redisCacheService.putCache(sysAreaService, new SysSetting() ,InitCache.AREA_BASE_CACHE,InitCache.AREA_CACHE);
		sysAreaService.putKeyCache(new SysArea(),InitCache.AREA_KEY_CACHE);	
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
		
	}	
	
	/**\
	 * 
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param sysDict
	* @return
	 */
	@Logs(value = "区域删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SysArea sysArea) {
		int count = sysAreaService.loadChildsBy(sysArea);
		if(count>0){
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "该区域有子级，删除失败！", "");
		}
		sysAreaService.delete(sysArea);
		//更新初始化缓存
		redisCacheService.putCache(sysAreaService, new SysSetting() ,InitCache.AREA_BASE_CACHE,InitCache.AREA_CACHE);
		sysAreaService.putKeyCache(new SysArea(),InitCache.AREA_KEY_CACHE);	
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");

	}
	

	/**
	 * 
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param sysDict
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	//@Cacheable(value = "dictCache", key = "#sysDict.id")
	public RestResponse<SysArea> get(SysArea sysArea) {
		
		SysArea resSysArea = sysAreaService.get(sysArea);
		SysArea parentSysArea = new SysArea();
		parentSysArea.setId(resSysArea.getParentId());
		resSysArea.setParentSysArea(sysAreaService.get(parentSysArea));
		
		return new RestResponse<SysArea>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", resSysArea);
	
	}
	
	/**
	 * 
	* <p>Title: POST</p>  
	* <p>Description: </p>  
	* @param sysDict
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadChildrenByParent")
	//@Cacheable(value = "dictCache", key = "#sysDict.id")
	public RestResponse<List<SysArea>> loadChildrenByParent(SysArea sysArea) {
		
		return new RestResponse<List<SysArea>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysAreaService.loadChildrenByParent(sysArea));

	}
	
	/**
	 * 
	* <p>Title: POST</p>  
	* <p>Description: </p>  
	* @param sysDict
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	//@Cacheable(value = "dictCache", key = "#sysDict.id")
	public RestResponse<List<SysArea>> loadAllListBy(SysArea sysArea) {
		return new RestResponse<List<SysArea>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysAreaService.loadAllListBy(sysArea));
	}
	
	
	/**
	 * 返回联动的数据结构(籍贯)
	* <p>Title: getFormSelectDatas</p>  
	* <p>Description: </p>  
	* @param sysArea
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/getFormSelectDatas")
	public RestResponse<FormSelectDatas> getFormSelectDatas(SysArea sysArea){
		return new RestResponse<FormSelectDatas>(0, "获取成功！", sysAreaService.getFormSelectDatas(sysArea));
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/putSimpleCache")	
	public RestResponse<List<AreaVo>> putSimpleCache(){
		return new RestResponse<List<AreaVo>>(0, "获取成功！", sysAreaService.putSimpleCache());
	}
	
//	/**
//	 * 返回联动的数据结构(通讯地址)
//	* <p>Title: getFormSelectDatas</p>  
//	* <p>Description: </p>  
//	* @param sysArea
//	* @return
//	 */
//	@CrossOrigin
//	@PostMapping(value = "/getAllFormSelectDatas")
//	public RestResponse<FormSelectDatas> getAllFormSelectDatas(SysArea sysArea){
//		return new RestResponse<FormSelectDatas>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysAreaService.getAllFormSelectDatas(sysArea));
//	}
	

}
