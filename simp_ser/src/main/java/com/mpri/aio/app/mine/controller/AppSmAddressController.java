package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppAddressService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.service.SmAddressService;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人中心家庭成员
 */
@RestController
@RequestMapping("app/address")
public class AppSmAddressController extends BaseController {
    @Autowired
    AppAddressService appAddressService;

    @PostMapping(value = "getByid")
    @CrossOrigin
    public RestResponse<SmAddress> get(SmAddress smAddress){
        smAddress= appAddressService.get(smAddress);
        return new RestResponse<SmAddress>(ExceptionResult.REQUEST_SUCCESS, "获取校友地址详细信息成功", smAddress);
    }
    @PostMapping(value = "loadByPage")
    @CrossOrigin
    public RestResponse<PageIo<SmAddress>> loadByPage (int pageNo, int pageSize, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        SmAddress smAddress= new SmAddress();
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smAddress.setUserId(userid);
        }
        PageIo<SmAddress> pageList = appAddressService.loadByPage(pageNo, pageSize,smAddress);
        return new RestResponse<PageIo<SmAddress>>(ExceptionResult.REQUEST_SUCCESS, "获取校友地址详细信息成功", pageList);
    }
    @PostMapping(value = "delete")
    @CrossOrigin
    public RestResponse<String> delete(SmAddress smAddress){
        appAddressService.delete(smAddress);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取校友地址详细信息成功", "");
    }
    @PostMapping(value = "save")
    @CrossOrigin
    public RestResponse<SmAddress> save(SmAddress smAddress, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (StringUtil.isNotEmpty(token)) {
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            smAddress.setUserId(userid);
        }
        if(null == smAddress.getId() || "".equals(smAddress.getId()) || "0".equals(smAddress.getId())){
            smAddress.setIsDefault("NOT_DEFAULT");
            smAddress.setType("NO_NATION_PLACE");
            smAddress =   appAddressService.insert(smAddress);
        }else{
            smAddress = appAddressService.update(smAddress);
        }

        return new RestResponse<SmAddress>(ExceptionResult.REQUEST_SUCCESS, "获取校友地址详细信息成功", smAddress);
    }

    @PostMapping(value = "setDefault")
    @CrossOrigin
    public RestResponse<PageIo<SmAddress>> setDefault(SmAddress smAddress,int pageNo, int pageSize, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(token);
        smAddress.setUserId(userId);
        appAddressService.setDefalutAddr(smAddress);
        SmAddress addr = new SmAddress();
        addr.setUserId(userId);
        PageIo<SmAddress> pageList = appAddressService.loadByPage(pageNo, pageSize,addr);
        return new RestResponse<PageIo<SmAddress>>(ExceptionResult.REQUEST_SUCCESS, "设置成功", pageList);
    }
}
