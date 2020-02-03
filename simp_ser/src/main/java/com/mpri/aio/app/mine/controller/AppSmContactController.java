package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppSmContactService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心的联系方式
 */
@RestController
@RequestMapping("app/contact")
public class AppSmContactController extends BaseController {
    @Autowired
    private AppSmContactService appSmContactService;
    /**
     * 获取个人的某个联系方式
     * @param smContact
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getOne")
    public RestResponse<SmContact> getOne( SmContact smContact ) {
        return new RestResponse<SmContact>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmContactService.getOne(smContact));
    }
    /**
     * 获取个人所有联系方式
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getContacats")
    public RestResponse<List<SmContact>> getContacats(HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        SmContact smContact = new SmContact();
        smContact.setUserId(userId);
        return new RestResponse<List<SmContact>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appSmContactService.getContacats(smContact));
    }

    /**
     * 保存或修改个人的联系方式
     * @param request
     * @param smContact
     * @return
     */
    @CrossOrigin
    @RequestMapping("/saveContacat")
    public RestResponse<String> saveContacat(HttpServletRequest request,SmContact smContact){
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        smContact.setUserId(userId);
        appSmContactService.saveContacat(smContact);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }

    /**
     * 删除个人的联系方式
     * @param smContact
     * @return
     */
    @CrossOrigin
    @RequestMapping("/deleteContact")
    public RestResponse<String> deleteContact(SmContact smContact){
        SmContact smContact1 = appSmContactService.get(smContact);
        if("IS_DEFAULT".equals(smContact1.getIsDefault())){
            return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "该联系方式为默认，不能直接删除！", "");
        }
        appSmContactService.deleteContact(smContact);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }
}
