package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmSocialService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmSocial;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心社会兼职
 */
@RestController
@RequestMapping("/app/social")
public class AppSmSocialController extends BaseController {

    @Autowired
    private AppSmSocialService appSmSocialService;
    /**
     * 获取个人社会兼职信息
     * @param smSocial
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmSocial> getOne(SmSocial smSocial) {
        return new RestResponse<SmSocial>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmSocialService.getOne(smSocial));
    }
    /**
     * 获取个人所有社会兼职
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getSocials")
    public RestResponse<List<SmSocial>> getSocials(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmSocial smSocial = new SmSocial();
        smSocial.setUserId(userId);
        return new RestResponse<List<SmSocial>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmSocialService.getSocials(smSocial));
    }
    /**
     * 保存或修改个人的社会兼职
     * @param request
     * @param smSocial
     * @return
     */
    @CrossOrigin
    @RequestMapping("/saveSocial")
    public RestResponse<String> saveSocial(HttpServletRequest request,SmSocial smSocial){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smSocial.setUserId(userId);
        appSmSocialService.saveSocial(smSocial);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }
    /**
     * 删除个人的社会兼职
     * @param smSocial
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deleteSocial")
    public RestResponse<String> deleteSocial(SmSocial smSocial){
        appSmSocialService.deleteSocial(smSocial);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }

}