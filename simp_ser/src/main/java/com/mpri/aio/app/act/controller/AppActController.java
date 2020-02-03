package com.mpri.aio.app.act.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.app.act.service.AppActContentService;
import com.mpri.aio.app.act.service.AppActService;
import com.mpri.aio.app.act.vo.ActDetailVo;
import com.mpri.aio.app.act.vo.ActListVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.act.vo.BannerActsVo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.system.shiro.JWTUtil;

@RestController
@RequestMapping("app/act")
public class AppActController extends BaseController {
    @Autowired
    private AppActService appActService;

    @Autowired
    private AppActContentService appActContentService;

    /**
     * 活动首页banner图
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/bannerActs")
    public PageInfo<BannerActsVo> bannerActs(int pageNo, int pageSize, ActActivity actActivity) {
        PageIo<BannerActsVo> bannerActs = appActService.getBannerActs(pageNo, pageSize, actActivity);
        return bannerActs;
    }

    /**
     * 列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/ActList")
    public PageInfo<ActListVo> ActList(int pageNo, int pageSize, ActActivity actActivity) {
        return appActService.ActList(pageNo,pageSize,actActivity);
    }
    /**
     * 列表 按照报名人数排列
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/hotActList")
    public PageInfo<ActListVo> hotActList(int pageNo, int pageSize, ActActivity actActivity) {
        return appActService.hotActList(pageNo,pageSize,actActivity);
    }

    /**
     * 活动详情
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getActDetail")
    public RestResponse<ActDetailVo> getActDetail(ActActivity actActivity) {
        return new RestResponse<ActDetailVo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                appActService.getActDetail(actActivity));
    }

    /**
     * 活动已经报名的校友列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getAttendSmByAct")
    public PageInfo<AttendSmByAct> getAttendSmByAct(int pageNo, int pageSize,ActActivity actActivity) {
        return appActService.getAttendSmByAct(pageNo,pageSize,actActivity);
    }

    /**
     * 活动某个活动的动态表单
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getActForm")
    public RestResponse<List<ActSetting>> loadAllListBy(ActActivity actActivity) {
        return new RestResponse<List<ActSetting>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                appActService.getActForm(actActivity));
    }

    /**
     * 判断是否报名
     * @param req
     * @param response
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getNumByUserId")
    public RestResponse<Boolean> getNumByUserId(HttpServletRequest req, ServletResponse response){
        String authorization = req.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        Map<String, String[]> parameterMap = req.getParameterMap();
        String activityId="";
        Iterator<Map.Entry<String, String[]>> entries = parameterMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            if("activityId".equals(entry.getKey())){
                activityId = StringUtils.join(entry.getValue(),";");
                ActContent actContent = new ActContent();
                actContent.setUserId(userId);
                actContent.setActId(activityId);
                int num= appActContentService.getNumByUserId(actContent);
                if(num>0){
                    return new RestResponse<Boolean>(ExceptionResult.REQUEST_SUCCESS, "已经报名", true);
                }
            }
        }
        return new RestResponse<Boolean>(ExceptionResult.REQUEST_SUCCESS, "没有报名", false);
    }

    /**
     * 保存活动表单
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/saveActContent")
    public RestResponse<String> saveActContent(HttpServletRequest req){
        String authorization = req.getHeader("Authorization");
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
                int num = appActContentService.getNumByUserId(actContent);
                if(num>0){
                    return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "你已经报名，请勿重复！", "1");
                }
            }
        }
        Iterator<Map.Entry<String, String[]>> entries1 = parameterMap.entrySet().iterator();
        while (entries1.hasNext()) {
            Map.Entry<String, String[]> entry = entries1.next();
            if(!"activityId".equals(entry.getKey())){
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
        if(list.isEmpty()){
            ActContent actContent = new ActContent();
            actContent.setActId(activityId);
            actContent.setId(IdGen.uuid());
            actContent.setUserId(userId);
            actContent.setCreateDate(new Date());
            appActContentService.insertOne(actContent);
        }else{
            appActContentService.saveActContent(list);
        }
        ActActivity actActivity = new ActActivity();
        actActivity.setId(activityId);
        actActivity=appActService.getById(actActivity);
        if(null==actActivity.getReadyNo()){
            actActivity.setReadyNo(1);
        }else{
            actActivity.setReadyNo(actActivity.getReadyNo()+1);
        }
        appActService.update(actActivity);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "报名成功！", "0");
    }
    /**
     * 我报名参与的活动列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getPartakeActList")
    public PageInfo<ActListVo> getPartakeActList(int pageNo, int pageSize, ActActivity actActivity ,HttpServletRequest req)
    {
        String authorization = req.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        return appActService.getPartakeActList(pageNo,pageSize,actActivity ,userId);
    }
}
