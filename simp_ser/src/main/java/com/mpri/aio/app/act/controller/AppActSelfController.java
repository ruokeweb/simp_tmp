package com.mpri.aio.app.act.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.service.AppActSelfContentService;
import com.mpri.aio.app.act.service.AppActSelfService;
import com.mpri.aio.app.act.vo.ActSelfVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.utils.service.TemplateUtils;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/app/actSelf")
public class AppActSelfController extends BaseController {

    @Autowired
    private AppActSelfService appActSelfService;

    @Autowired
    private AppActSelfContentService appActSelfContentService;

    /**
     * 列表
     *
     * @param pageNo
     * @param pageSize
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getActSelfList")
    public PageInfo<ActSelfVo> getActSelfList(int pageNo, int pageSize, ActSelforg actSelforg) {
        actSelforg.setStatus(GlobalStr.SELFORG_STATUS_SUCCESS);
        return appActSelfService.getActSelfList(pageNo, pageSize, actSelforg);
    }

    /**
     * 新增值年返校
     *
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/insert")
    public RestResponse<String> insert(ActSelforg actSelforg, HttpServletRequest req)  {
        String authorization = req.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        actSelforg.setId(IdGen.uuid());
        actSelforg.setUserId(userId);
        actSelforg.setStatus("AUDITING");
        actSelforg.setCreateDate(new Date());
        String touser =req.getParameter("touser");
        String form_id =req.getParameter("form_id");
        WxdataTemplateSend templateSend = TemplateUtils.getTemplateSend(touser, form_id);
        appActSelfService.insert(actSelforg,templateSend);
        /*报名成功之后，将自己加到已经报名的列表里面*/
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", actSelforg.getId());
    }

    /**
     * 值年返校详情
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getSelfDetail")
    public RestResponse<ActSelfVo> getSelfDetail(ActSelforg actSelforg) {
        return new RestResponse<ActSelfVo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                appActSelfService.getSelfDetail(actSelforg));
    }
    /**
     * 判断是否报名
     * @param req
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getNumByUserId")
    public RestResponse<Boolean> getNumByUserId(ActSelforg actSelforg,HttpServletRequest req){
        String authorization = req.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        ActSelforgContent actSelforgContent = new ActSelforgContent();
        actSelforgContent.setUserId(userId);
        actSelforgContent.setActSelforgId(actSelforg.getId());
        int num= appActSelfContentService.getNumByUserId(actSelforgContent);
        if(num>0){
            return new RestResponse<Boolean>(ExceptionResult.REQUEST_SUCCESS, "已经报名", true);
        }
        return new RestResponse<Boolean>(ExceptionResult.REQUEST_SUCCESS, "没有报名", false);
    }
    /**
     * 活动已经值年返校的校友列表
     * @param pageNo
     * @param pageSize
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getAttendSmBySelf")
    public PageInfo<AttendSmByAct> getAttendSmBySelf(int pageNo, int pageSize, ActSelforg actSelforg) {
        return appActSelfService.getAttendSmBySelf(pageNo,pageSize,actSelforg);
    }

    /**
     * 值年返校报名
     * @param request
     * @param actSelforgContent
     * @return
     */
    @PostMapping(value = "/saveSelfContent")
    public RestResponse<String> saveSelfContent(ServletRequest request, ActSelforgContent actSelforgContent){
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        actSelforgContent.setUserId(userId);

        int num= appActSelfContentService.getNumByUserId(actSelforgContent);
        if(num>0){
            return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "你已经报名，请勿重复！", "0");
        }
        actSelforgContent.setCreateDate(new Date());
        actSelforgContent.setId(IdGen.uuid());
        appActSelfContentService.saveSelfContent(actSelforgContent);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "报名成功！", actSelforgContent.getId());
    }
    /**
     * 我参与的值年返校列表
     *
     * @param pageNo
     * @param pageSize
     * @param actSelforg
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getMyActSelfList")
    public PageInfo<ActSelfVo> getMyActSelfList(int pageNo, int pageSize, ActSelforgContent actSelforg,HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        actSelforg.setUserId(userId);
        return appActSelfContentService.getActSelfList(pageNo, pageSize, actSelforg);
    }
    /**
     * 我参与的值年返校条数
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getMyActSelfNum")
    public RestResponse<Integer> getMyActSelfNum(ActSelforgContent actSelforg, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        actSelforg.setUserId(userId);
        return new RestResponse<Integer>(ExceptionResult.REQUEST_SUCCESS, "获取成功", appActSelfContentService.getMyActSelfNum(actSelforg));
    }
}