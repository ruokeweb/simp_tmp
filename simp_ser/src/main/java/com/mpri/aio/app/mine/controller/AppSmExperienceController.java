package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmExperienceService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmExperience;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心校园经历
 */
@RestController
@RequestMapping("app/experience")
public class AppSmExperienceController extends BaseController {
    @Autowired
    private AppSmExperienceService appSmExperienceService;

    /**
     * 获取个人校园经历信息
     * @param smExperience
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmExperience> getOne(SmExperience smExperience) {
        return new RestResponse<SmExperience>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmExperienceService.getOne(smExperience));
    }
    /**
     * 获取个人所有校园经历
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getExperiences")
    public RestResponse<List<SmExperience>> getExperiences(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmExperience smExperience = new SmExperience();
        smExperience.setUserId(userId);
        return new RestResponse<List<SmExperience>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmExperienceService.getExperiences(smExperience));
    }
    /**
     * 保存或修改个人的校园经历
     * @param request
     * @param smExperience
     * @return
     */
    @CrossOrigin
    @RequestMapping("/saveExperience")
    public RestResponse<String> saveExperience(HttpServletRequest request,SmExperience smExperience){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smExperience.setUserId(userId);
        appSmExperienceService.saveExperience(smExperience);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }
    /**
     * 删除个人的校园经历
     * @param smExperience
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deleteExperience")
    public RestResponse<String> deleteExperience(SmExperience smExperience){
        appSmExperienceService.deleteExperience(smExperience);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }
}
