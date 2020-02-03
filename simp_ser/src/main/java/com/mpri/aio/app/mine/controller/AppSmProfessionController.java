package com.mpri.aio.app.mine.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.mine.service.AppSmProfessionService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping("app/profession")
public class AppSmProfessionController {

    @Autowired
    AppSmProfessionService appSmProfessionService;

    /**、
     * 分页获取职业经历信息
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @PostMapping(value = "getProList")
    @CrossOrigin
    public RestResponse<List<SmProfession>> getProList(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        SmProfession smProfession= new SmProfession();
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smProfession.setUserId(userid);
        }
       // smProfession.setType(GlobalStr.NOT_DEFAULT);
        List<SmProfession>  list = appSmProfessionService.loadAllListBy(smProfession);
        return new RestResponse<List<SmProfession>>(ExceptionResult.REQUEST_SUCCESS, "获取获取校友教育经历列表成功", list);
    }


    
    
    
    /**
     * 获取职业经历详细信息
     * @return
     */
    @PostMapping(value = "getProInfo")
    @CrossOrigin
    public RestResponse<SmProfession> getInfo(SmProfession smProfession,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smProfession.setUserId(userid);
        }
        SmProfession  info = appSmProfessionService.getById(smProfession);
        return new RestResponse<SmProfession>(ExceptionResult.REQUEST_SUCCESS, "获取获取校友教育信息成功", info);
    }

    /**
     * 保存职业经历详细信息
     * @return
     */
    @PostMapping(value = "saveProInfo")
    @CrossOrigin
    public RestResponse<SmProfession> saveProInfo(SmProfession smProfession,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smProfession.setUserId(userid);
        }
        smProfession.setType(GlobalStr.NOT_DEFAULT);
        SmProfession  info = appSmProfessionService.save(smProfession);
        return new RestResponse<SmProfession>(ExceptionResult.REQUEST_SUCCESS, "保存校友教育经历信息成功", info);
    }

    /**
     * 删除职业经历详细信息
     * @return
     */
    @PostMapping(value = "delete")
    @CrossOrigin
    public RestResponse<SmProfession> deleteProInfo(SmProfession smProfession,HttpServletRequest request) {
        smProfession.setFlag("DELETE");
        SmProfession  info = appSmProfessionService.save(smProfession);
        return new RestResponse<SmProfession>(ExceptionResult.REQUEST_SUCCESS, "保存校友教育经历信息成功", info);
    }
}
