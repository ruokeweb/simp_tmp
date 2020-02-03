package com.mpri.aio.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.utils.DatasCovert;
import com.mpri.aio.schoolmate.vo.FormSelectDatas;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.mapper.SysAreaMapper;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.vo.AreaVo;


/**
* .地区CRUD实现
* @author lzq
* @date 2018年8月1日
*/
@Service
public class SysAreaService extends CrudService<SysAreaMapper, SysArea>  {	

	private static String ROOT_AREA = "root";
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired 
	private RedisCacheService redisCacheService;
	
	/**
	 * 根据code获取对象
	 * @return 
	 */
	public SysArea getSysAreaByCode(String code) {
		SysArea area = new SysArea();
		area.setCode(code);
		return mapper.getSysAreaByCode(area);
	}
	
	/**
	 * .根据父获取所有子
	 * @param sysArea
	 * @return
	 */
	public List<SysArea> loadChildrenByParent(SysArea sysArea){
		 List<SysArea> list =mapper.loadChildrenByParent(sysArea);
		 return list;
		
	}
	
	/**
	 * FormSelectDatas 数据格式(籍贯)
	* <p>Title: getFormSelectDatas</p>  
	* <p>Description: </p>  
	* @param sysAreaList
	* @return
	 */
	public FormSelectDatas getFormSelectDatas(SysArea sysArea){
		List<FormSelectDatas> formSelectDatas = new ArrayList<FormSelectDatas>();
		Map<String , SysArea> areaList = redisCacheService.getBaseCache(new SysArea() , InitCache.AREA_BASE_CACHE);
		areaList.forEach((k, v) -> formSelectDatas.add(new FormSelectDatas(v.getId(),v.getParentId(),v.getName())));	
		FormSelectDatas res = DatasCovert.setRootFormSelectData(formSelectDatas, ROOT_AREA);
	    Collections.sort(res.getChildren(), new Comparator<FormSelectDatas>() {
	        @Override
	        public int compare(FormSelectDatas p1, FormSelectDatas p2) {
	    		if(Long.valueOf(p1.getValue()) < Long.valueOf(p2.getValue())) {
	    			return -1;
	    		}else if(Long.valueOf(p1.getValue()) > Long.valueOf(p2.getValue())) {
	    			return 1;
	    		}else {
	    			return 0;	
	    		}
	        }
	    });
		return res;
	}
	
//	/**
//	 * FormSelectDatas 数据格式(住址)
//	* <p>Title: getFormSelectDatas</p>  
//	* <p>Description: </p>  
//	* @param sysAreaList
//	* @return
//	 */
//	public FormSelectDatas getAllFormSelectDatas(SysArea sysArea){
//		List<FormSelectDatas> formSelectDatas = new ArrayList<FormSelectDatas>();
//		List<SysArea> sysAreaList = this.loadAllListBy(sysArea);
//		Map<String , SysArea> areaList = redisCacheService.getBaseCache(new SysArea() , InitCache.AREA_BASE_CACHE);
//		areaList.forEach((k, v) -> formSelectDatas.add(new FormSelectDatas(v.getId(),v.getParentId(),v.getName())));
//		FormSelectDatas res = DatasCovert.setRootFormSelectDatas(formSelectDatas, ROOT_AREA);
//		return res;
//	}
	
	/**
	 * 放置key_name缓存
	 * 
	 * @param service
	 * @param dataEntity
	 * @param cacheName
	 * @param kv         是否加载key-value的缓存
	 */
	public void putKeyCache(SysArea t, String cachekeyName) {
		Map<String, SysArea> baseMap = new HashMap<>();

		// 每次初始化前，移除redis中对应缓存
		// redisTemplate.delete(cacheBaseName);
		// 循环新增缓存
		List<SysArea> list = this.loadAllListBy(t);
		for (SysArea o : list) {
			String baseKey = o.getId();
			SysArea area = new SysArea();
			area.setId(baseKey);
			area.setParentId(o.getParentId());
			area.setName(o.getName());
			baseMap.put(baseKey, area);
		}
		// 基准缓存
		redisTemplate.opsForHash().putAll(cachekeyName, baseMap);
		System.out.println("-------------------->" + cachekeyName + "：加载完成：" + baseMap.size());
	}
	
	/**
	 * 获取简单对象的缓存
	 */
	public List<AreaVo> putSimpleCache(){
		List<SysArea> areaList = this.loadAllListBy(new SysArea());
		List<AreaVo> areaVos = new ArrayList<AreaVo>();		
		for (SysArea area : areaList) {
			areaVos.add( new AreaVo(area.getId(), area.getParentId(), area.getName()));	 
		}
		return areaVos;
	}

	public int loadChildsBy(SysArea sysArea) {
		return this.mapper.loadChildsBy(sysArea);
	}
}