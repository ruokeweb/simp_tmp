package com.mpri.aio.app.mine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.mine.service.AppHisInfomationService;
import com.mpri.aio.app.mine.vo.HisInfomationVo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.model.SysUser;

@RestController
@RequestMapping("app/his")
public class AppHisInfomationController extends BaseController {

    @Autowired
    private AppHisInfomationService appHisInfomationService;

    @CrossOrigin
    @PostMapping(value = "/getHisInfomation")
    public RestResponse<HisInfomationVo> getHisInfomation(SysUser sysUser) {
        return new RestResponse<HisInfomationVo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                appHisInfomationService.getHisInfomation(sysUser));
    }
}
