package com.mpri.aio.webber.controller;

import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestController {

    @Autowired
    private RedisCacheService redisCacheService;
    
    @PostMapping("testLoadCacheMap")
    public RestResponse<Map<String, Object>> loadCacheMap() {
        Map<String, Object> cacheMap = new HashMap<String, Object>();

        Object dictCache = (Map<String, List<SysDict>>) redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
        Object areaCache = (Map<String, List<SysArea>>) redisCacheService.getCache(new SysArea(), InitCache.AREA_CACHE);
        ;
        Object departCache = (Map<String, List<SettingDepartment>>) redisCacheService.getCache(new SettingDepartment(),
                InitCache.DEPART_CACHE);
        cacheMap.put("dictCache", dictCache);
        cacheMap.put("departCache", departCache);
        cacheMap.put("areaCache", areaCache);

        return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "缓存数据获取成功", cacheMap);

    }
}
