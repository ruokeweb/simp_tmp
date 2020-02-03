package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmHonorService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmHonor;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心荣誉成果
 */
@RestController
@RequestMapping("app/honor")
public class AppSmHonorController extends BaseController {
    @Autowired
    private AppSmHonorService appSmHonorService;

    /**
     * 获取个人荣誉成果信息
     * @param smHonor
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmHonor> getOne(SmHonor smHonor) {
        return new RestResponse<SmHonor>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmHonorService.getOne(smHonor));
    }
    /**
     * 获取个人所有荣誉成果
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getHonors")
    public RestResponse<List<SmHonor>> getExperiences(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmHonor smHonor = new SmHonor();
        smHonor.setUserId(userId);
        return new RestResponse<List<SmHonor>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmHonorService.getHonors(smHonor));
    }
    /**
     * 保存或修改个人的荣誉成果
     * @param request
     * @param smHonor
     * @return
     */
    @CrossOrigin
    @RequestMapping("/saveHonor")
    public RestResponse<String> saveHonor(HttpServletRequest request,SmHonor smHonor){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smHonor.setUserId(userId);
        appSmHonorService.saveHonor(smHonor);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }
    /**
     * 删除个人的荣誉成果
     * @param smHonor
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deleteHonor")
    public RestResponse<String> deleteHonor(SmHonor smHonor){
        appSmHonorService.deleteHonor(smHonor);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }
}
