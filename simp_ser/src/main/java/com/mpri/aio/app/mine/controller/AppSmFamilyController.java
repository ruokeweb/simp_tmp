package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmFamilyService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmFamily;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心家庭成员
 */
@RestController
@RequestMapping("app/family")
public class AppSmFamilyController extends BaseController {
    @Autowired
    private AppSmFamilyService appSmFamilyService;

    /**
     * 获取个人家庭成员信息
     * @param smFamily
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmFamily> getOne(SmFamily smFamily) {
        return new RestResponse<SmFamily>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmFamilyService.getOne(smFamily));
    }

    /**
     * 获取个人所有家庭成员
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getFamilys")
    public RestResponse<List<SmFamily>> getFamilys(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmFamily smFamily = new SmFamily();
        smFamily.setUserId(userId);
        return new RestResponse<List<SmFamily>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmFamilyService.getFamilys(smFamily));
    }

    /**
     * 保存或修改个人的家庭成员
     * @param request
     * @param smFamily
     * @return
     */
    @CrossOrigin
    @RequestMapping("/saveFamily")
    public RestResponse<String> saveFamily(HttpServletRequest request,SmFamily smFamily){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smFamily.setUserId(userId);
        appSmFamilyService.saveFamily(smFamily);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }

    /**
     * 删除个人的家庭成员
     * @param smFamily
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deleteFamily")
    public RestResponse<String> deleteFamily(SmFamily smFamily){
        appSmFamilyService.deleteFamily(smFamily);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }
}
