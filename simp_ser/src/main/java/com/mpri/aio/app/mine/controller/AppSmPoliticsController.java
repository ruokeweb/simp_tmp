package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmPoliticsService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmPolitics;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心政治面貌
 */
@RestController
@RequestMapping("app/politics")
public class AppSmPoliticsController extends BaseController {
    @Autowired
    private AppSmPoliticsService appSmPoliticsService;
    /**
     * 获取个人的某个政治面貌
     * @param smPolitics
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmPolitics> getOne(SmPolitics smPolitics ) {
        return new RestResponse<SmPolitics>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmPoliticsService.getOne(smPolitics));
    }
    /**
     * 获取个人的所有政治面貌
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getPolitics")
    public RestResponse<List<SmPolitics>> getPolitics(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmPolitics smPolitics = new SmPolitics();
        smPolitics.setUserId(userId);
        return new RestResponse<List<SmPolitics>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmPoliticsService.getPolitics(smPolitics));
    }

    /**
     * 删除个人的政治面貌
     * @param smPolitics
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deletePolitic")
    public RestResponse<String> deletePolitic(SmPolitics smPolitics){
        appSmPoliticsService.deletePolitic(smPolitics);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }

    /**
     * 新增或修改个人政治面貌
     * @param request
     * @param smPolitics
     * @return
     */
    @CrossOrigin
    @RequestMapping("/savePolitic")
    public RestResponse<String> savePolitic(HttpServletRequest request,SmPolitics smPolitics){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smPolitics.setUserId(userId);
        appSmPoliticsService.savePolitic(smPolitics);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }
}
