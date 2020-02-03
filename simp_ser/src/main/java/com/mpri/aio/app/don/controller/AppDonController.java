package com.mpri.aio.app.don.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.app.don.service.AppDonService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.system.common.GlobalStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
*
* @Description:  小程序捐赠项目——Controller
* @Author:       LZQ
* @project 	  AIO
* @CreateDate:   Wed Aug 29 15:22:22 CST 2018
* @Version:      v_1.0
*
*/
@RestController
@RequestMapping("/app/don")
public class AppDonController {

    @Autowired
    private AppDonService donService;

    /**
     * 小程序分页查询捐赠项目列表--捐赠
     * @param pageNo
     * @param pageSize
     * @param donProject
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/donProList")
    public PageInfo<DonProject> donProList(int pageNo,int pageSize, DonProject donProject){
        PageIo<DonProject> pageInfo =  donService.donProList(pageNo,pageSize,donProject);
        return pageInfo;
    }

    /**
     * 获取所有的捐赠项目
     * @param donProject
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getAllDonPro")
    public RestResponse<List<DonProjectTogether>> getAllDonPro(DonProject donProject){
        donProject.setStatus(GlobalStr.DON_BEDOING);
        List<DonProjectTogether> lsit =  donService.getAllDonPro(donProject);
        return new RestResponse<List<DonProjectTogether>>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", lsit);
    }

    /**
     * 小程序捐赠首页轮播获取--捐赠
     * @param donProject
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/bannerDonPros")
    public PageInfo<DonProject> bannerDonPros(int pageNo,int pageSize,DonProject donProject){
        PageIo<DonProject> pageInfo =  donService.donProList(pageNo,pageSize,donProject);
        return pageInfo;
    }

    /**
     * 小程序捐赠项目内容
     * @param donProject
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getDonProDetail")
    public RestResponse<DonProject> getDonProDetail(DonProject donProject){
        donProject = donService.getById(donProject);
        return new RestResponse<DonProject>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目信息获取成功", donProject);
    }


}