package com.mpri.aio.untils.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;

/**
 * 对数据交换提供基础数据接口
* <p>Title: ExchangeBaseDatasController</p>
* <p>Description: </p>
* @author syp
* @date 2019年1月31日
 */
@RestController
@RequestMapping("exchange")
public class ExchangeBaseDatasController extends BaseController{

	@Autowired 
	private RedisCacheService redisCacheService;
	@Autowired
	private SmSchoolmateService smSchoolmateService;
	
	@RequestMapping(value="/getBaseDatas")
	public RestResponse<Map<String,Object>> getBaseDatas() {
		Map<String,Object> baseData=new HashMap<String,Object>();
		
		Object dict  =(Map<String, List<SysDict>>) redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		Object area  =(Map<String,SysArea>)redisCacheService.getBaseCache(new SysArea(), InitCache.AREA_BASE_CACHE);
		Object department = (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		baseData.put("dict", dict);
		baseData.put("area", area);
		baseData.put("department", department);
		return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "基础数据获取成功", baseData);
	}

	//数据交换事务
	@PostMapping(value = "/saveSm")
	public RestResponse<String> saveSm(@RequestBody SmSchoolmate smSchoolmate) {
		String oldId =  smSchoolmate.getUserId();
		try{
			smSchoolmateService.saveSm(smSchoolmate);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", oldId);
		}catch(Exception e){
			//// ex.printStackTrace();;
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "保存失败！", oldId);
		}
	}
}
