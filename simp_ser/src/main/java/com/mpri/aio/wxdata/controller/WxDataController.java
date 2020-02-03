package com.mpri.aio.wxdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.app.pay.util.HttpRequest;
import com.mpri.aio.app.utils.service.HttpUtils;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/wx/data")
class WxDataController extends BaseController {

//    @Value("${wechat.appId}")
    private String appId;
//    @Value("${wechat.secret}")
    private String secret;

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    

    
    /**
     *  获取用户访问小程序月留存
     */
    @PostMapping(value = "/getMonthlyRetain")
    public RestResponse<String> getUserPortrait(HttpServletRequest request){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("begin_date","20190708");
        jsonObject.put("end_date","20190714");

        String s = HttpUtils.sendPost("https://api.weixin.qq.com/datacube/getweanalysisappidweeklyretaininfo?access_token="+ getToken(), jsonObject);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", s);
    }

    private String getToken(){
        String params = "grant_type=client_credential&appid=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId) + 
        		"&secret=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret);
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        JSONObject jsonObject1 =JSONObject.parseObject(sr);

        return jsonObject1.get("access_token").toString();
    }

}
