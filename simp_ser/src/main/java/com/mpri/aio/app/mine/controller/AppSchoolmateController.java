package com.mpri.aio.app.mine.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.mine.service.AppSchoolmateService;
import com.mpri.aio.app.reg.service.RegistService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping("/app/schoolmate")
@CrossOrigin
public class AppSchoolmateController {
    @Autowired
    AppSchoolmateService schoolmateService;

    @Autowired
    private RegistService registService;

    /**
     * 保存校友基本信息
     * @param smSchoolmate
     * @param request
     * @return
     */
    @PostMapping(value = "saveInfo")
    @CrossOrigin
    public RestResponse<SmSchoolmate> saveinfo(SmSchoolmate smSchoolmate, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userid = getUserid(token);
        if (StringUtil.isNotEmpty(userid)) {
            smSchoolmate.setUserId(userid);
        }
        smSchoolmate = schoolmateService.saveinfo(smSchoolmate);
    	/**
    	 * 获取校友基本数据
    	 */
        SmSchoolmate schoolmate = new SmSchoolmate();
        schoolmate.setUserId(userid);
        schoolmate= registService.getSchoolmateBaseInfo(schoolmate);
        return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "保存校友信息成功", schoolmate);

    }

    /**
     * 获取校友信息
     * @param request
     * @return
     */
    @PostMapping(value = "getInfo")
    @CrossOrigin
    public RestResponse<SmSchoolmate> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        SmSchoolmate smSchoolmate = new SmSchoolmate();
        String userid = getUserid(token);
        if (StringUtil.isNotEmpty(userid)) {
            smSchoolmate.setUserId(userid);
        }

        smSchoolmate = schoolmateService.getByUserId(smSchoolmate);
        return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", smSchoolmate);
    }


    private String getUserid(String token) {
        if (StringUtil.isNotEmpty(token)) {
            String userid = JWTUtil.getUserId(token);
            return userid;
        }
        return "";
    }
}
