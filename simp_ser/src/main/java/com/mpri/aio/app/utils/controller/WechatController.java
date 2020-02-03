package com.mpri.aio.app.utils.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mpri.aio.app.pay.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.app.utils.service.HttpUtils;
import com.mpri.aio.app.utils.service.WechatUtils;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SettingPage;
import com.mpri.aio.settings.service.SettingPageService;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

/**
 * 微信相关接口
 * @author syp
 *
 */
@RestController
@RequestMapping("/app/wx")
@CrossOrigin
public class WechatController {
	
	@Autowired
	private WechatUtils wechatUtils;
	
//    @Value("${wechat.appId}")
    private String appId;
//    @Value("${wechat.secret}")
    private String secret;
    @Value("${file.uploadFolder}")
    private String weixinFolder;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    
	@Autowired
	private SettingPageService settingPageService;

	private  String  sendUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" ;
	
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	
    /**
     * 小程序生成二维码
    * <p>Title: getWCRecode</p>  
    * <p>Description: </p>  
    * @param page
    * @param scene
    * @param width
    * @param response
    * @return RestResponse<String>
     */
    @PostMapping(value = "/getWCRecode")
    public RestResponse<String> getWCRecode(@RequestParam(value="page",required=false) String page,
            @RequestParam("scene") String scene, @RequestParam(value="width",required=false) String width,
             HttpServletResponse response){
        String sr = wechatUtils.getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId),
        		runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret));
        JSONObject obj = JSONObject.parseObject(sr);
        String token = obj.getString("access_token");
        
        JSONObject paramobj = new JSONObject();
        paramobj.put("scene", scene);
        paramobj.put("page", page);
        paramobj.put("width", width);
        HttpUtils.sendPost1("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token, paramobj,response);
       
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！","");
    }
    
    /**
     * 小程序生成二维码 保存到服务端
    * <p>Title: getWCRecodeUrl</p>  
    * <p>Description: </p>  
    * @param page
    * @param scene
    * @param width
    * @param response
    * @return 返回服务器对应路径
     */
    @PostMapping(value = "/getWCRecodeUrl")
    public RestResponse<String> getWCRecodeUrl(@RequestParam(value="page",required=false) String page,
            @RequestParam("scene") String scene, @RequestParam(value="width",required=false) String width,
            HttpServletResponse response){
        
    	String sr = wechatUtils.getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId),
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret));
        JSONObject obj = JSONObject.parseObject(sr);
        String token = obj.getString("access_token");
        
        JSONObject paramobj = new JSONObject();
        paramobj.put("scene", scene);
        paramobj.put("page", page);
        paramobj.put("width", width);
        String codeUrl = HttpUtils.getCordUrl("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token, weixinFolder,staticAccessPath,paramobj,response);
        
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", codeUrl);
    }
    
    /**
     * 获取页面文字
     */
    @PostMapping(value = "/getContentByPage")
    public RestResponse<String> getContentByPage(SettingPage settingPage){
    	List<SettingPage> list = settingPageService.loadAllListBy(settingPage);
    	String content = "";
    	if(list.size() > 0) {
    		content = list.get(0).getContent();
    	}
    	return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", content);
    }
    /**
     * 模板消息接口
     */
    @PostMapping(value = "/send")
    public RestResponse<String> send(HttpServletRequest request){
        String touser =request.getParameter("touser");
        String template_id =request.getParameter("template_id");
        String page =request.getParameter("page");
        String form_id =request.getParameter("form_id");
        String keyword =request.getParameter("keyword");
        Object parse = JSONObject.parse(keyword);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser",touser);
        jsonObject.put("template_id",template_id);
        jsonObject.put("page",page);
        jsonObject.put("form_id",form_id);
        jsonObject.put("data",parse);
        jsonObject.put("appid",runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
        String s = HttpUtils.sendPost(sendUrl+ getToken(), jsonObject);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", s);
    }
    private String getToken(){
        String params = "grant_type=client_credential&appid=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId) 
        + "&secret=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_secret);
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        JSONObject jsonObject1 =JSONObject.parseObject(sr);

        return jsonObject1.get("access_token").toString();
    }
    /**
     *  获取微信小程序管理平台的统计数据
     */
    @PostMapping(value = "/getUserPortrait")
    public RestResponse<String> getUserPortrait(HttpServletRequest request){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("begin_date","20190708");
        jsonObject.put("end_date","20190714");

        String s = HttpUtils.sendPost("https://api.weixin.qq.com/datacube/getweanalysisappidweeklyretaininfo?access_token="+ getToken(), jsonObject);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", s);
    }

    /**
     * 前端埋点保存接口
     * @param request
     * @return
     */
    @GetMapping(value = "/track")
    public RestResponse<String> track(HttpServletRequest request){
        String data = request.getParameter("data");
        String decode = decode(data);
        System.out.println(decode);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", "");
    }

    /**
     * base64解码
     * @param base64Str
     * @return
     */
    public static String decode(String base64Str){
        String str="";
        byte[] base64Data = Base64.getDecoder().decode(base64Str);
        try {
            str = new String(base64Data,"utf-8");
        }catch (Exception e){
        }
        return str;
    }
    
    
    /**
     * desc 获取小程序配置
     */
    @RequestMapping(value = "/getMiniProConfig")
    public RestResponse<Map<String,Object>> getMiniProConfig(){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put(RunSettingParamsUtils.is_on_webvsb,
    			Boolean.valueOf(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.is_on_webvsb)));
    	res.put("vsb_webber_domain",
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain));
    	res.put(RunSettingParamsUtils.school_name,
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.school_name));
    	res.put(RunSettingParamsUtils.is_don_project,
    			Boolean.valueOf(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.is_don_project)));
    	res.put(RunSettingParamsUtils.is_on_index_mediaMes,
    			Boolean.valueOf(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.is_on_index_mediaMes)));
    	res.put(RunSettingParamsUtils.is_don_team,
    			Boolean.valueOf(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.is_don_team)));
    	res.put(RunSettingParamsUtils.template_jftz,
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.template_jftz));
    	res.put(RunSettingParamsUtils.template_zcsh,
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.template_zcsh));
    	res.put(RunSettingParamsUtils.template_shjg,
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.template_shjg));
    	res.put(RunSettingParamsUtils.template_hdks,
    			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.template_hdks));
        return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", res);
    }

}
