package com.mpri.aio.wxdata.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.wxdata.mapper.WxdataEventTargetMapper;
import com.mpri.aio.wxdata.model.WxdataEventTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.wxdata.model.WxdataPointed;
import com.mpri.aio.wxdata.mapper.WxdataPointedMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *  
 * @Description:  小程序埋点数据表——Service
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Jul 18 17:08:03 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class WxdataPointedService extends CrudService<WxdataPointedMapper, WxdataPointed>  {
    @Autowired
    private WxdataEventTargetMapper wxdataEventTargetMapper;

    @Transactional(readOnly = false)
    public WxdataPointed save(String data) {
        Map<String, String> params = JSONObject.parseObject(data, new TypeReference<Map<String, String>>(){});
        Iterator<String> it = params.keySet().iterator();  //map.keySet()得到的是set集合，可以使用迭代器遍历
        WxdataPointed entity = new WxdataPointed();
        List<WxdataEventTarget> wxdataEventTargets = new ArrayList<WxdataEventTarget>();
        if(StringUtil.isNotEmpty( params.get("_openId"))){
            while(it.hasNext()) {
                String key = it.next();
                if("$networkType".equals(key)){
                    entity.setNetworkType(params.get(key));
                }
                if("$os".equals(key)){
                    entity.setOs(params.get(key));
                }
                if("$urlPath".equals(key)){
                    entity.setUrlPath(params.get(key));
                }
                if("$isFirstDay".equals(key)){
                    entity.setIsFirstDay(params.get(key));
                }
                if("$latestScene".equals(key)){
                    entity.setLatestScene(params.get(key));
                }
                if("$event_name".equals(key)){
                    entity.setEventName(params.get(key));
                }
                if("$brand".equals(key)){
                    entity.setBrand(params.get(key));
                }
                if("$nn".equals(key)){
                    entity.setNn(params.get(key));
                }
                if("$model".equals(key)){
                    entity.setModel(params.get(key));
                }
                if("$gd".equals(key)){
                    entity.setGd(params.get(key));
                }
                if("$isDefaultTrack".equals(key)){
                    entity.setIsDefaultTrack("true");
                }
                if("_userId".equals(key)){
                    entity.setUserId(params.get(key));
                }
                if("_openId".equals(key)){
                    entity.setOpenId(params.get(key));
                }
                if(!key.startsWith("_")&&!key.startsWith("$")){
                    WxdataEventTarget wxdataEventTarget = new WxdataEventTarget();
                    wxdataEventTarget.setEventKey(key);
                    wxdataEventTarget.setEventValue(params.get(key));
                    wxdataEventTargets.add(wxdataEventTarget);
                }

            }
            entity.preInsert();
            this.mapper.insert(entity);
            String id = entity.getId();
            if(wxdataEventTargets.size()!=0){
                for (WxdataEventTarget wxdataEventTarget:wxdataEventTargets) {
                    wxdataEventTarget.preInsert();
                    wxdataEventTarget.setPointedId(id);
                    wxdataEventTargetMapper.insert(wxdataEventTarget);
                }
            }
        }


        return entity;
    }

}