package com.mpri.aio.app.utils.service;

import org.springframework.stereotype.Component;

@Component
public class WechatUtils {

	/**
	 * 获取微信Token
	 * @param appId
	 * @param secret
	 * @return
	 */
    public String getToken(String appId,String secret){
        String params = "grant_type=client_credential&appid=" + appId + "&secret=" + secret;  
        String sr = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);     
        return sr;
    }
}
