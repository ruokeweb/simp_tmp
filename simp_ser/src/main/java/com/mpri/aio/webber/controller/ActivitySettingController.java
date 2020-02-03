package com.mpri.aio.webber.controller;

import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.act.service.ActActivityService;
import com.mpri.aio.act.service.ActContentService;
import com.mpri.aio.act.service.ActSettingService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.system.shiro.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("webber/activitysetting")
public class ActivitySettingController extends BaseController {

    @Autowired
    private ActSettingService actSettingService;
    @Autowired
    private ActContentService actContentService;
    @Autowired
    private ActActivityService actActivityService;
    /**
     * 获取设置列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param actSetting
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public RestResponse<List<ActSetting>> list( ActSetting actSetting) {

        return new RestResponse<List<ActSetting>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                    actSettingService.loadAllListBy(actSetting));
    }

    /**
     * 获取活动设置
     * <p>Title: get</p>
     * <p>Description: </p>
     * @param actSetting
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/get")
    public RestResponse<ActSetting> get(ActSetting actSetting) {
        return new RestResponse<ActSetting>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                actSettingService.get(actSetting));
    }
    /***
     * 保存接口
     */
    @CrossOrigin
    @PostMapping(value = "/save")
    public RestResponse<String> save( ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        String time = req.getHeader("Time");
        String key = req.getHeader("Key");
        String userId = JWTUtil.getUserId(authorization);
        Map<String, String[]> parameterMap = req.getParameterMap();
        List<ActContent> list = new ArrayList<ActContent>();
        String activityId="";
        Iterator<Map.Entry<String, String[]>> entries = parameterMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            if("activityId".equals(entry.getKey())){
                activityId = StringUtils.join(entry.getValue(),";");
                ActContent actContent = new ActContent();
                actContent.setUserId(userId);
                actContent.setActId(activityId);
                int num = actContentService.getNumBy(actContent);
                if(num>0){
                    return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "你已经报名，请勿重复！", "");
                }
            }else{
                ActContent actContent = new ActContent();
                actContent.setId(IdGen.uuid());
                actContent.setActId(activityId);
                actContent.setCode(entry.getKey());
                actContent.setUserId(userId);
                actContent.setVal(StringUtils.join(entry.getValue(),";"));
                actContent.setCreateDate(new Date());
                actContent.setFlag("NORMAL");
                actContent.setRemark("");
                actContent.setSort(list.size()+1);
                list.add(actContent);
            }

        }
        actContentService.insertList(list);
        ActActivity actActivity = new ActActivity();
        actActivity.setId(activityId);
        actActivity=actActivityService.get(actActivity);
        if(null==actActivity.getReadyNo()){
            actActivity.setReadyNo(1);
        }else{
            actActivity.setReadyNo(actActivity.getReadyNo()+1);
        }
        actActivityService.save(actActivity);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "报名成功！", "");
    }
    /***
     * 保存接口
     */
    @CrossOrigin
    @PostMapping(value = "/getNum")
    public RestResponse<String> getNum(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        String time = req.getHeader("Time");
        String key = req.getHeader("Key");
        String userId = JWTUtil.getUserId(authorization);
        Map<String, String[]> parameterMap = req.getParameterMap();
        List<ActContent> list = new ArrayList<ActContent>();
        String activityId="";
        Iterator<Map.Entry<String, String[]>> entries = parameterMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            if("activityId".equals(entry.getKey())){
                activityId = StringUtils.join(entry.getValue(),";");
                ActContent actContent = new ActContent();
                actContent.setUserId(userId);
                actContent.setActId(activityId);
                int num = actContentService.getNumBy(actContent);
                if(num>0){
                    return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "0", "");
                }
            }
        }
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "1", "");

    }

}
