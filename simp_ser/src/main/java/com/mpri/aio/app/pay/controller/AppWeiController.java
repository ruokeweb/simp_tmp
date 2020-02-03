package com.mpri.aio.app.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.app.pay.model.WeiChatInfo;
import com.mpri.aio.app.pay.util.HttpRequest;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

/**
* desc
* @author lzq
* @date 2018年9月5日 - 下午3:23:20
*/
@RestController
@RequestMapping("/app/pay")
public class AppWeiController extends BaseController
{
//    @Value("${wechat.appId}")
    private String appId;
//    @Value("${wechat.secret}")
    private String secret;
    @Value("${file.uploadFolder}")
    private String weixinFolder;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    
    private final static String xcxPath ="/pages/index/index"; //默认路径
    private final static String width = "500";
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
    
    
    /**
     * 获取openid
     * @param code 小程序登录返回的Code 
     * @return openid
     */
    @CrossOrigin
    @PostMapping(value = "/getInfo")
    public RestResponse<WeiChatInfo> getWCUserInfo(@RequestParam("code") String code)
    {
              
        if (code == null || code.length() == 0) {     
            return new RestResponse<WeiChatInfo>(ExceptionResult.NO_PERMISSION, "code不能为空！", null);
        }     
        
        String grantType = "authorization_code";
        String params = "appid=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId) + "&secret=" 
        + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret) + "&js_code=" + code + "&grant_type=" + grantType;        
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        
        JSONObject json =JSONObject.parseObject(sr);
        //String sessionKey = json.get("session_key").toString();
        String openId = json.get("openid").toString();
    
        WeiChatInfo weiChatInfo = new WeiChatInfo();
        weiChatInfo.setOpenId(openId);        
        return new RestResponse<WeiChatInfo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", weiChatInfo);
    }
    
    
    /**
     * 微信获取token
     * @return RestResponse<String>
     */
    @CrossOrigin
    @PostMapping(value = "/getwxToken")
    public RestResponse<String> getWCToken()
    {
        
        String sr = getToken();        
        
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sr);
    }
   
    
    
    private String getToken(){
        String params = "grant_type=client_credential&appid=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId) +
        		"&secret=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret);  
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        return sr;
    }
    
    
    
}
