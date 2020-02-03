package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppEducationService;
import com.mpri.aio.app.mine.vo.AppSmEduVo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心的联系方式
 */
@RestController
@RequestMapping("app/edu")
public class AppSmEduController extends BaseController {

    @Autowired
    AppEducationService appEducationService;

    /**、
     * 获取教育经历信息
     * @param request
     * @return
     */
    @PostMapping(value = "getEduList")
    @CrossOrigin
    public RestResponse<List<AppSmEduVo>> getEduList( HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        SmEducation smEducation= new SmEducation();
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smEducation.setUserId(userid);
        }
       // smEducation.setIsDefault(GlobalStr.NOT_DEFAULT);
        List<AppSmEduVo> list = appEducationService.getEduList(smEducation);
       // PageIo<SmEducation>  info = appEducationService.loadByPage(pageNo, pageSize,smEducation);
        return new RestResponse<List<AppSmEduVo>>(ExceptionResult.REQUEST_SUCCESS, "获取获取校友教育经历列表成功", list);
    }


    /**
     * 获取教育经历详细信息
     * @return
     */
    @PostMapping(value = "getEduInfo")
    @CrossOrigin
    public RestResponse<SmEducation> getInfo(SmEducation smEducation,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smEducation.setUserId(userid);
        }

        SmEducation  info = appEducationService.getById(smEducation);
        return new RestResponse<SmEducation>(ExceptionResult.REQUEST_SUCCESS, "获取获取校友教育信息成功", info);
    }

    /**
     * 保存教育经历详细信息
     * @return
     */
    @PostMapping(value = "saveEduInfo")
    @CrossOrigin
    public RestResponse<SmEducation> saveEduInfo(SmEducation smEducation,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smEducation.setUserId(userid);
        }

        SmEducation  info = appEducationService.save(smEducation);
        return new RestResponse<SmEducation>(ExceptionResult.REQUEST_SUCCESS, "保存校友教育经历信息成功", info);
    }

    /**
     * 保存教育经历详细信息
     * @return
     */
    @PostMapping(value = "delEduInfo")
    @CrossOrigin
    public RestResponse<SmEducation> delEduInfo(SmEducation smEducation) {
        smEducation.setFlag("DELETE");
        SmEducation  info = appEducationService.save(smEducation);
        return new RestResponse<SmEducation>(ExceptionResult.REQUEST_SUCCESS, "删除教育经历信息成功", info);
    }

    /**
     * 设置和取消教育经历是否为默认
     * @return
     */
    @PostMapping(value = "setDefault")
    @CrossOrigin
    public RestResponse<List<AppSmEduVo>> setDefault(SmEducation smEducation,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(token);
        smEducation.setUserId(userId);
        //清除默认
        appEducationService.setDefalutEdu(smEducation);
        SmEducation edu = new SmEducation();
        edu.setUserId(userId);
        List<AppSmEduVo> list = appEducationService.getEduList(edu);
       // PageIo<SmEducation>  info = appEducationService.loadByPage(pageNo, pageSize,edu);
        return new RestResponse<List<AppSmEduVo>>(ExceptionResult.REQUEST_SUCCESS, "设置默认成功", list);
    }



}
