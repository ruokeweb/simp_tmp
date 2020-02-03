package com.mpri.aio.app.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.app.utils.AccessTokenUtil;
import com.mpri.aio.app.utils.WechatGlobal;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

@Service
public class SubMessageService {
//	@Value("${wechat.appId}")
    private String appId;
//    @Value("${wechat.secret}")
    private String secret;
    
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	//通知服务
	public String notice(SubMessage sm) {
		
		String access_token=AccessTokenUtil.getAccessToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId), 
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret));    
		
		String params = "?access_token=" + access_token;
		String url=WechatGlobal.WX_SUB_SEND + params;
		String data = JSON.toJSONString(sm);
		
        String sr = HttpUtils.sendPost(url, JSONObject.parseObject(data));

		
		return sr;
	}
}
