package com.mpri.aio.message.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.service.SmMarkService;
import com.mpri.aio.system.model.SysArea;
import org.apache.poi.ss.formula.eval.AreaEvalBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.model.MesGroupCondition;
import com.mpri.aio.message.service.MesGroupConditionService;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.message.vo.MesGroupConditionEnum;
import com.mpri.aio.message.vo.MesGroupConditionVo;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysDict;

 /**   
 *  
 * @Description:  信息群组条件——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Wed Nov 14 15:50:23 CST 2018
 * @Version:      v_1.02
 *    
 */
@RestController
@RequestMapping("/mes/mesGroupCondition")
public class MesGroupConditionController extends BaseController {
	
	@Autowired
	private MesGroupConditionService mesGroupConditionService;
	
	
	@Autowired
	private MesGroupService mesGroupService;
	
	@Autowired 
	private RedisCacheService redisCacheService;

	@Autowired
	private SmMarkService smMarkService;

	/**
	 * 获取信息群组条件列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesGroupCondition
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesGroupCondition> list(int pageNo,int pageSize,MesGroupCondition mesGroupCondition) {
		PageIo<MesGroupCondition> pageInfo =  mesGroupConditionService.loadByPage(pageNo,pageSize,mesGroupCondition);
		pageInfo.setList(this.setMesGroupCondition(pageInfo.getList()));
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

	public RestResponse<List<MesGroupCondition>> loadAllListBy(MesGroupCondition mesGroupCondition) {
		return new RestResponse<List<MesGroupCondition>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", mesGroupConditionService.loadAllListBy(mesGroupCondition));
	}
	
	/**
	 * 增加或者更新信息群组条件
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesGroupCondition
	* @return
	 */
	@Logs(value = "信息群组条件编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesGroupCondition mesGroupCondition){
		mesGroupConditionService.save(mesGroupCondition);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	
	/**
	 * 增加或者更新信息群组条件
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesGroupCondition
	* @return
	 */
	@Logs(value = "信息群组条件编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/saveInfo")
	public RestResponse<String> saveInfo(MesGroupConditionVo mesGroupConditionVo){
		mesGroupConditionService.saveInfo(mesGroupConditionVo);
		//更新消息群组初始化数据
		getMesGroupsCache().clear();
		getMesGroupsCache().addAll(mesGroupService.getMesGroupInfo());
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}
	
	/**
	 * 删除信息群组条件（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesGroupCondition
	* @return
	 */
	@Logs(value = "信息群组条件删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesGroupCondition mesGroupCondition) {
		mesGroupConditionService.delete(mesGroupCondition);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息群组条件
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesGroupCondition
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesGroupCondition> get(MesGroupCondition mesGroupCondition) {
		return new RestResponse<MesGroupCondition>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesGroupConditionService.get(mesGroupCondition));	
	}
	
	
	private List<MesGroupCondition> setMesGroupCondition (List<MesGroupCondition>  list) {
		Map<String,List<SysDict>> dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		Map<String,SysArea> areaBaseCache= (Map<String,SysArea>)redisCacheService.getBaseCache(new SysArea(), InitCache.AREA_BASE_CACHE);
		Map<String,SettingDepartment> departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		for (MesGroupCondition m : list) {
			if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.DEPARTMENT.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					m.setName(departBaseCache.get(m.getValue()).getName());
				}
			}/*else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.TYPE.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					List<SysDict> dicts = dictCache.get(GlobalStr.SCHOOLEMATE_TYPE);
					dicts.forEach((SysDict dict) -> {
					    if(dict.getValue().equals(m.getValue())) {
					    	m.setName(dict.getLabel());
					    }
					});
				}
			}*/else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.SEX.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					List<SysDict> dicts = dictCache.get(GlobalStr.SEX_TYPECODE);
					dicts.forEach((SysDict dict) -> {
					    if(dict.getValue().equals(m.getValue())) {
					    	m.setName(dict.getLabel());
					    }
					});
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.STARTDATE.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					m.setName(m.getValue());
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.ENDDATE.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					m.setName(m.getValue());
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.COUNTRY.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					SysArea sysArea = areaBaseCache.get(m.getValue());
					m.setName(sysArea.getName());
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.PROVINCE.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					SysArea sysArea = areaBaseCache.get(m.getValue());
					m.setName(sysArea.getName());
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.CITY.getCode())) {
				if(StringUtil.isNotEmpty(m.getValue())) {
					SysArea sysArea = areaBaseCache.get(m.getValue());
					m.setName(sysArea.getName());
				}
			}else if(m.getField().equalsIgnoreCase(MesGroupConditionEnum.SCHOOLMATEMARK.getCode())) {
				if (StringUtil.isNotEmpty(m.getValue())) {
					String[] markIds = m.getValue().split(",");
					if ((markIds != null && markIds.length > 0)) {
						String markName = "";
						for (int i = 0; i < markIds.length; i++) {
							SmMark sm = new SmMark();
							if (!"root".equals(markIds[i])) {
								sm.setId(markIds[i]);
								SmMark smMark = smMarkService.get(sm);
								if (i == markIds.length - 1) {
									markName += smMark.getName();
								} else {
									markName += smMark.getName() + " | ";
								}
							}
						}
						m.setName(markName);
					}
				}
			}
		}
		return list;	
	}
	
	/**
	 * 获取信息组缓存
	 * @return
	 */
	public List<MesGroup> getMesGroupsCache() {
		redisCacheService.getBaseCache(new MesGroup(), InitCache.MESG_BASE_CACHE);
		Map<String, MesGroup> map = new HashMap<String, MesGroup>(); 
		
		List<MesGroup> list=new ArrayList<MesGroup>();
		for (Map.Entry<String, MesGroup> entry : map.entrySet()) { 
			list.add(entry.getValue());
		}
		return list;
	}	
		
}